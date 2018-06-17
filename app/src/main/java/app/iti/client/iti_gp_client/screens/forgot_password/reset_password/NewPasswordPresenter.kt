package app.iti.client.iti_gp_client.screens.forgot_password.reset_password

import app.iti.client.iti_gp_client.contracts.NewPasswordContract.*
import app.iti.client.iti_gp_client.entities.NewPasswordResponse

class NewPasswordPresenter : Presenter {
    //references to view and model
    lateinit var mModel: Model
    lateinit var mView: View


    override fun initPresenter(view: View) {
        mView =  view
        mModel = NewPasswordModel()
        mModel.initModel(this)
    }

    override fun sendPasswordToModel(hash: String, new_password: String, confirm_password: String) {
        mModel.requestToApi(hash , new_password  , confirm_password)
    }

    override fun receiveResponse(response: NewPasswordResponse) {
        mView.onRequestSuccess(response)
    }

    override fun errorResponse(error: Throwable) {
        mView.onRequestError(error)
    }

    override fun isPasswordValid(password :String) : Boolean{
        val passwordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        val check:Boolean= password.matches(passwordRegex.toRegex())
        return check
    }
}