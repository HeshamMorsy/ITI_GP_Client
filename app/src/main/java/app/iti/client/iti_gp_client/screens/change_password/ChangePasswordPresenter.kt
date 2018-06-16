package app.iti.client.iti_gp_client.screens.change_password

import app.iti.client.iti_gp_client.contracts.ChangePasswordContract.*
import app.iti.client.iti_gp_client.entities.ChangePasswordResponse

/**
 * Responsible for getting data from [ChangePasswordActivity] and send it to the model [ChangePasswordModel] to send it to backend
 * , handling actions in the screen and update UI if needed
 */

class ChangePasswordPresenter : Presenter {
    // reference to the model and the view
    private lateinit var mView:View
    private lateinit var mModel: Model

    override fun initPresenter(view: View) {
        mView = view
        mModel = ChangePasswordModel()
        mModel.initModel(this)
    }

    override fun receiveResponse(response: ChangePasswordResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun errorResponse(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}