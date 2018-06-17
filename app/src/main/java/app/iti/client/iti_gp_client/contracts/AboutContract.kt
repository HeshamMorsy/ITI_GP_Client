package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.AboutResponse
import app.iti.client.iti_gp_client.entities.LoginResponse

interface AboutContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi()
    }
    interface View {
        fun onRequestSuccess(response: AboutResponse)
        fun onRequestError(error: Throwable)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun receiveResponse(response: AboutResponse)
        fun errorResponse(error: Throwable)
        fun getDataFromModel()
    }
}