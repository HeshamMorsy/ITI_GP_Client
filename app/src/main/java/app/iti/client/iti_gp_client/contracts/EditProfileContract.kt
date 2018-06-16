package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.EditProfileResponse

interface EditProfileContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi(email: String, phone: String)
    }
    interface View {
        fun onRequestSuccess(response: EditProfileResponse)
        fun onRequestError(error: Throwable)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun receiveResponse(response: EditProfileResponse)
        fun errorResponse(error: Throwable)
    }
}