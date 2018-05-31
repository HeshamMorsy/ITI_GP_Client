package app.iti.client.iti_gp_client.screens.forgot_password

import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*
/**
 * Created by Hesham on 5/29/2018.
 * Responsible for handling actions from forgot password view and updating UI if required
 */
class ForgotPasswordPresenter : Presenter {
    // references to view and model
    var mView:View? = null
    var mModel:Model? = null

    // initialize mView and mModel
    override fun initPresenter(view: View) {
        // initialize mView as ForgotPasswordActivity and mModel as ForgotPasswordModel
        mView = view
        mModel = ForgotPasswordModel()
    }
}