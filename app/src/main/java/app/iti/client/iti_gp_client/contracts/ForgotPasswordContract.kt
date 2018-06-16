package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.ForgotPasswordResponse
import app.iti.client.iti_gp_client.screens.forgot_password.*


/**
 * Created by Hesham on 5/29/2018.
 * define the contract between the view [ForgotPasswordActivity], the model [ForgotPasswordModel] and the presenter [ForgotPasswordPresenter]
 */
interface ForgotPasswordContract {
    interface Model {
        fun resetPassword(email: String)
   }

    interface View {
        fun setEmailError(error:String)
        fun startLoading(mes:String)
        fun endLoading(result: String)
    }

    interface Presenter {
        fun initPresenter(view: View)
        fun isEmailValid(email:String)
        fun sendResetBtnEvent(email: String)
        fun receiveResponse(response: ForgotPasswordResponse)
        fun errorResponse(error: String)
    }
}