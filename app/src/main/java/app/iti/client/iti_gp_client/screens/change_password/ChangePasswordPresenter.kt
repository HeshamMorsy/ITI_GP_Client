package app.iti.client.iti_gp_client.screens.change_password

import android.app.Activity
import android.content.Context
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ChangePasswordContract.*
import app.iti.client.iti_gp_client.entities.ChangePasswordResponse
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get

/**
 * created by Hesham 14/6/2018
 * Responsible for getting data from [ChangePasswordActivity] and send it to the model [ChangePasswordModel] to send it to backend
 * , handling actions in the screen and update UI if needed
 */

class ChangePasswordPresenter : Presenter {
    // reference to the model and the view
    private lateinit var mView:View
    private lateinit var mModel: Model

    override fun initPresenter(view: View) {
        mView = view
        mModel = ChangePasswordModel()
        mModel.initModel(this)
    }

    override fun receiveResponse(response: ChangePasswordResponse) {
        mView.showMessage(response.message)
        mView.onRequestSuccess(response)
    }

    override fun errorResponse(error: Throwable) {
        mView.showMessage(error.localizedMessage)
    }

    override fun sendPasswordToModel(old_password: String ,new_password: String, confirm_password: String) {
        // show message for user of which password field is not valid
        if(!isPasswordValid(old_password)) mView.showMessage((mView as Activity).resources.getString(R.string.oldPasswordNotValid))
        if(!isPasswordValid(new_password)) mView.showMessage((mView as Activity).resources.getString(R.string.newPasswordNotValid))
        if(!isPasswordValid(new_password)) mView.showMessage((mView as Activity).resources.getString(R.string.confirmPasswordNotValid))

        if(isPasswordValid(old_password) && isPasswordValid(new_password) && isPasswordValid(confirm_password)) {
            val defaultPref = PreferenceHelper.defaultPrefs(mView as Context)
            val auth = defaultPref.get("auth_token", "0")
            mModel.requestToApi(auth!!, old_password, new_password, confirm_password)
        }
    }

    private fun isPasswordValid(pass:String) : Boolean{
        val passwordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        val check:Boolean= pass.matches(passwordRegex.toRegex())
        return check
    }

}