package app.iti.client.iti_gp_client.screens.login

import android.util.Log
import app.iti.client.iti_gp_client.contracts.LoginContract.*
import java.util.regex.Pattern

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for handling actions from login view and updating UI if required
 */
class LoginPresenter : Presenter {
    // references to model and view
    var mView:View?=null
    var mModel:Model?=null

    // initialize mView and mModel
    override fun initPresenter(view: View) {
        // initializing mView as LoginActivity and mModel as LoginModel
        mView = view
        mModel= LoginModel()
    }

    // send email and password to model to check if the user email and password exists and matches in the login api
    override fun login(email: String, password: String) {
        if(isEmailValid(email) && password.length>0)
            (mModel as LoginModel).checkEmailAndPassword(email,password)
        else
            Log.i("LoginPresenter","unvalid email or password!!")
    }

    // check regular expression for email
    fun isEmailValid(email: String): Boolean {
        return Pattern.compile(
                "^(([\\w-]+\\.)+[\\w-]+|([a-zA-Z]|[\\w-]{2,}))@"
                        + "((([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\."
                        + "([0-1]?[0-9]{1,2}|25[0-5]|2[0-4][0-9])\\.([0-1]?"
                        + "[0-9]{1,2}|25[0-5]|2[0-4][0-9]))|"
                        + "([a-zA-Z]+[\\w-]+\\.)+[a-zA-Z]{2,4})$"
        ).matcher(email).matches()
    }

}