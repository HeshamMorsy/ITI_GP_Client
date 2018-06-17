package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.ChangePasswordResponse

interface ChangePasswordContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi(auth: String ,old_password: String, new_password: String, confirm_password: String)
    }
    interface View {
        fun onRequestSuccess(response: ChangePasswordResponse)
        fun onRequestError(error: Throwable)
        fun showMessage(message: String)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun sendPasswordToModel(old_password: String, new_password: String, confirm_password: String)
        fun receiveResponse(response: ChangePasswordResponse)
        fun errorResponse(error: Throwable)
    }
}