package app.iti.client.iti_gp_client.contracts

/**
 * Created by Hesham on 5/29/2018.
 * define the contract between the view ForgotPasswordActivity, the model ForgotPasswordModel and the presenter ForgotPasswordPresenter
 */
interface ForgotPasswordContract {
    interface Model {
        fun sendPinCode()
   }

    interface View {
        fun setEmailError(error:String)
    }

    interface Presenter {
        fun initPresenter(view: View)
        fun isEmailValid(email:String)
        fun sendResetBtnEvent()
    }
}