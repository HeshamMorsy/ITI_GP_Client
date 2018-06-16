package app.iti.client.iti_gp_client.screens.about

import app.iti.client.iti_gp_client.contracts.AboutContract.*
import app.iti.client.iti_gp_client.entities.AboutResponse

/**
 * Responsible for getting data from the view [AboutActivity] and send it to the model [AboutModel] to send it to backend
 * , handling actions in the screen and update UI if needed
 */


class AboutPresenter : Presenter {
    // reference to the model and the view
    private lateinit var mView:View
    private lateinit var mModel: Model

    override fun initPresenter(view: View) {
        mView = view
        mModel = AboutModel()
        mModel.initModel(this)
    }

    override fun receiveResponse(response: AboutResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun errorResponse(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}