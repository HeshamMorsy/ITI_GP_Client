package app.iti.client.iti_gp_client.screens.login

import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.LoginContract.*
import app.iti.client.iti_gp_client.entities.LoginResponse
import app.iti.client.iti_gp_client.utilities.Constants
import app.iti.client.iti_gp_client.utilities.Constants.Companion.AVATAR_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.EMAIL_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.NAME_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.PHONE_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.Constants.Companion.TOKEN_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.setValue
import app.iti.client.iti_gp_client.utilities.getAlertDialog
import java.util.regex.Pattern

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for handling actions from login view and updating UI if required
 */
class LoginPresenter : Presenter {
    // references to model and view
    var mView:View?=null
    var mModel:Model?=null

    // booleans to check if email and password are valid
    var checkEmail:Boolean = false
    var checkPassword:Boolean = false

    // initialize mView and mModel
    override fun initPresenter(view: View) {
        // initializing mView as LoginActivity and mModel as LoginModel
        mView = view

        mModel= LoginModel(this)
    }

    // send email and password to model to check if the user email and password exists and matches in the login api
    override fun login(email: String, password: String) {
        isEmailValid(email)
        isPasswordValid(password)
        if(checkEmail && checkPassword) {
            mView?.startLoading((mView as Activity).resources.getString(R.string.wait))
            mModel?.requestToApi(email, password)
        }
        else {
            Log.i("LoginPresenter", "unvalid email or password!!")
        }
    }

    // check regular expression for email
    override fun isEmailValid(email: String){
        val check:Boolean= Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
        if(check){
            checkEmail = true
        }else{
            mView?.setEmailError((mView as Activity).resources.getString(R.string.invalidEmail))
            checkEmail = false
        }
    }

    override fun isPasswordValid(pass:String){
        val passwordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        val check:Boolean= pass.matches(passwordRegex.toRegex())
        if(check){
            checkPassword = true
        }else{
            mView?.setPasswordError((mView as Activity).resources.getString(R.string.invalidEmail))
            checkPassword = false
        }
    }

    // response from login api model
    override fun receiveResponse(response: LoginResponse) {
        mView?.endLoading()
        if(response.message == "success") {
            // save authentication token in shared preferences
            val defaultPref = PreferenceHelper.defaultPrefs(mView as Context)
            defaultPref.setValue(Constants.LOGIN_STATUS_SHARED_PREFERENCE,true)
            defaultPref.setValue(TOKEN_SHARED_PREFERENCE,response.auth_token)
            defaultPref.setValue(EMAIL_SHARED_PREFERENCE,response.user.email)
            defaultPref.setValue(PHONE_SHARED_PREFERENCE,response.user.phone)
            defaultPref.setValue(NAME_SHARED_PREFERENCE,response.user.name)
            defaultPref.setValue(AVATAR_SHARED_PREFERENCE,response.user.avatar.url)
            Toast.makeText(mView as Context, (mView as Activity).resources.getString(R.string.loginSuccessful), Toast.LENGTH_SHORT).show()

            mView?.goToHomeScreen()
        }else if(response.message == "sorry this account is not yet verified"){
            val alert = getAlertDialog(mView as Activity, (mView as Activity).resources.getString(R.string.error) ,
                    response.message)
            alert.setCancelable(false)
            alert.setPositiveButton((mView as Activity).resources.getString(R.string.ok) ,DialogInterface.OnClickListener{dialog, which ->
                alert.setCancelable(true)
                mView?.goToSignUpScreen()
            })
            alert.show()

        }else{
            val alert = getAlertDialog(mView as Activity, (mView as Activity).resources.getString(R.string.error) ,
                    response.message)
            alert.setPositiveButton((mView as Activity).resources.getString(R.string.ok) ,DialogInterface.OnClickListener{dialog, which ->
                alert.setCancelable(true)
            })
            alert.show()
        }
        Log.i("LoginPresenter Response","my token : "+response.auth_token)
        Log.i("LoginPresenter Response","message : "+response.message)
    }

    override fun errorResponse(error: String) {
        mView?.endLoading()
        val alert = getAlertDialog(mView!! as Activity, (mView as Activity).resources.getString(R.string.error)
                ,error)
                alert.setPositiveButton((mView as Activity).resources.getString(R.string.ok)
                        , DialogInterface.OnClickListener{dialog, which ->
                    alert.setCancelable(true)
                 })
        alert.show()
    }

}