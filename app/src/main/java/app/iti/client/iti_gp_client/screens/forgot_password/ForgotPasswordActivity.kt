package app.iti.client.iti_gp_client.screens.forgot_password

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*
import kotlinx.android.synthetic.main.activity_forgot_password.*


/**
 * Display forgot password screen
 */

class ForgotPasswordActivity : AppCompatActivity(), View{
    // reference to presenter
    var mPresenter:Presenter? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        // initialize the presenter as ForgotPasswordPresenter
        mPresenter = ForgotPasswordPresenter()
        mPresenter?.initPresenter(this)

        // set on click on send reset link button
        forgot_sendResetBtn.setOnClickListener {
            mPresenter?.isEmailValid(forgot_emailEditText.text.toString())
            mPresenter?.sendResetBtnEvent()
        }
    }

    override fun setEmailError(error: String) {
        forgot_emailEditText.error = error
    }

}
