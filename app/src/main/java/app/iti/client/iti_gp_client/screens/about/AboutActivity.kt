package app.iti.client.iti_gp_client.screens.about

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.AboutContract.*
import app.iti.client.iti_gp_client.entities.AboutResponse

/**
 * Displays the about screen
 */

class AboutActivity : AppCompatActivity(), View {
    //reference to presenter
    private lateinit var mPresneter:Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_about)
        // initialize about presenter
        mPresneter = AboutPresenter()
        mPresneter.initPresenter(this)
    }

    override fun onRequestSuccess(response: AboutResponse) {

    }

    override fun onRequestError(error: Throwable) {

    }
}
