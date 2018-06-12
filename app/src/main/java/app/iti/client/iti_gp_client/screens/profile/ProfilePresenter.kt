package app.iti.client.iti_gp_client.screens.profile

import app.iti.client.iti_gp_client.contracts.ProfileContract.*

class ProfilePresenter : Presenter {
    // references to model and view
    var mView: View? = null
    var mModel: Model? = null

    override fun initPresenter(view: View) {
        // the view refers to the ProfileActivity
        mView = view
        mModel = ProfileModel(this)
    }
}