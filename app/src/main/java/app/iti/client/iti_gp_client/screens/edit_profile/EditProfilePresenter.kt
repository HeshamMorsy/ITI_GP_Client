package app.iti.client.iti_gp_client.screens.edit_profile

import app.iti.client.iti_gp_client.contracts.EditProfileContract.*
import app.iti.client.iti_gp_client.entities.EditProfileResponse

/**
 * Responsible for getting data from [EditProfileActivity] and send it to the model [EditProfileModel] to send it to backend
 * , handling actions in the screen and update UI if needed
 */

class EditProfilePresenter : Presenter {
    // reference to the model and the view
    private lateinit var mView:View
    private lateinit var mModel: Model

    override fun initPresenter(view: View) {
        mView = view
        mModel = EditProfileModel()
        mModel.initModel(this)
    }

    override fun receiveResponse(response: EditProfileResponse) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun errorResponse(error: Throwable) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}