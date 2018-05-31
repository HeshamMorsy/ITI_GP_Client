package app.iti.client.iti_gp_client.screens.forgot_password

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*


/**
 * Display forgot password screen
 */

class ForgotPasswordActivity : AppCompatActivity(), View {
    // reference to presenter
    var mPresenter:Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        // initialize the presenter as ForgotPasswordPresenter
        mPresenter = ForgotPasswordPresenter()
        (mPresenter as ForgotPasswordPresenter).initPresenter(this)
    }
}
