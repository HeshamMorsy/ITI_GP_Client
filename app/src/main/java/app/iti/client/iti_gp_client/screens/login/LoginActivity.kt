package app.iti.client.iti_gp_client.screens.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.forgot_password.ForgotPasswordActivity
import app.iti.client.iti_gp_client.screens.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import contract.interfaces.LoginContract.*

/**
 * Displays the login screen
 */

class LoginActivity : AppCompatActivity(),View {
    // reference to presenter
    var mPresenter:Presenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // initialize presenter as LoginPresenter
        mPresenter = LoginPresenter()
        (mPresenter as LoginPresenter).initPresenter(this)

    }

    // method to handle on click on register now textView and go to SignUpActivity
    fun toRegisterScreen(view:android.view.View){
        val intent = Intent(this, SignUpActivity::class.java)
        // start your next activity
        startActivity(intent)
        finish()
    }

    // method to handle on click on forgot password textView and go to ForgotPasswordActivity
    fun toForgotPassword(view:android.view.View){
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        // start your next activity
        startActivity(intent)
    }

    // login button action when clicked
    fun loginBtnEvent(view:android.view.View){
        val email:String = login_emailEditText.text.toString()
        val password:String = login_passwordEditText.text.toString()
        (mPresenter as LoginPresenter).login(email,password)
    }


}
