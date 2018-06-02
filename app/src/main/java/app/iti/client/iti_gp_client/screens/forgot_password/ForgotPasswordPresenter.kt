package app.iti.client.iti_gp_client.screens.forgot_password

import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*
import java.util.regex.Pattern

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for handling actions from forgot password view and updating UI if required
 */
class ForgotPasswordPresenter : Presenter {
    // references to view and model
    var mView:View? = null
    var mModel:Model? = null
    // boolean to check if the email is valid or not
    var checkEmail:Boolean = false

    // initialize mView and mModel
    override fun initPresenter(view: View) {
        // initialize mView as ForgotPasswordActivity and mModel as ForgotPasswordModel
        mView = view
        mModel = ForgotPasswordModel()
    }

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
            mView?.setEmailError("Invalid Email")
            checkEmail = false
        }
    }

    override fun sendResetBtnEvent() {
        if(checkEmail){
            mModel?.sendPinCode()
        }else{
            mView?.setEmailError("Invalid Email")

        }
    }

}