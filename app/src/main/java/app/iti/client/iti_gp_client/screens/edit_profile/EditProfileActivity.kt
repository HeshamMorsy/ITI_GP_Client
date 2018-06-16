package app.iti.client.iti_gp_client.screens.edit_profile

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.EditProfileContract.*
import app.iti.client.iti_gp_client.entities.EditProfileResponse

/**
 * Displays the edit profile screen
 */

class EditProfileActivity : AppCompatActivity(), View {
    //reference to presenter
    private lateinit var mPresneter:Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        // initialize about presenter
        mPresneter = EditProfilePresenter()
        mPresneter.initPresenter(this)
    }

    override fun onRequestSuccess(response: EditProfileResponse) {

    }

    override fun onRequestError(error: Throwable) {

    }
}
