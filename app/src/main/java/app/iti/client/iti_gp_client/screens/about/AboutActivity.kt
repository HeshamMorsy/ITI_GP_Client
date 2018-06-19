package app.iti.client.iti_gp_client.screens.about

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.AboutContract.*
import app.iti.client.iti_gp_client.entities.AboutResponse
import app.iti.client.iti_gp_client.screens.home.HomeActivity
import kotlinx.android.synthetic.main.activity_about.*

/**
 * created by Hesham 14/6/2018
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
        mPresneter.getDataFromModel()
    }

    override fun onRequestSuccess(response: AboutResponse) {
        if(response.message == "success")
            about_msg.text = response.about_us
    }

    override fun onRequestError(error: Throwable) {
        about_msg.text = resources.getString(R.string.wrongMsg)
    }


    override fun onBackPressed() {
//        super.onBackPressed()
        val myIntent = Intent(this, HomeActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}
