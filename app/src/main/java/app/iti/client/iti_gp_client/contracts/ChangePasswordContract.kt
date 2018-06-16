package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.ChangePasswordResponse

interface ChangePasswordContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi(email: String)
    }
    interface View {
        fun onRequestSuccess(response: ChangePasswordResponse)
        fun onRequestError(error: Throwable)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun receiveResponse(response: ChangePasswordResponse)
        fun errorResponse(error: Throwable)
    }
}