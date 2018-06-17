package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.NewPasswordResponse

interface NewPasswordContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi(hash: String , new_password: String, confirm_password: String)
    }
    interface View {
        fun onRequestSuccess(response: NewPasswordResponse)
        fun onRequestError(error: Throwable)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun sendPasswordToModel(hash: String, new_password: String, confirm_password: String)
        fun receiveResponse(response: NewPasswordResponse)
        fun errorResponse(error: Throwable)
        fun isPasswordValid(password: String) : Boolean
    }
}