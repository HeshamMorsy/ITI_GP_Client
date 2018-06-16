package app.iti.client.iti_gp_client.screens.change_password

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ChangePasswordContract.*
import app.iti.client.iti_gp_client.entities.ChangePasswordResponse

/**
 * Displays the change password screen
 */

class ChangePasswordActivity : AppCompatActivity(), View {
    //reference to presenter
    private lateinit var mPresneter:Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        // initialize about presenter
        mPresneter = ChangePasswordPresenter()
        mPresneter.initPresenter(this)
    }

    override fun onRequestSuccess(response: ChangePasswordResponse) {

    }

    override fun onRequestError(error: Throwable) {

    }
}
