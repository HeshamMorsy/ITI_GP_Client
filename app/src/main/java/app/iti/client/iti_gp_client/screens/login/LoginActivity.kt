package app.iti.client.iti_gp_client.screens.login

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.forgot_password.ForgotPasswordActivity
import app.iti.client.iti_gp_client.screens.signup.SignUpActivity
import kotlinx.android.synthetic.main.activity_login.*
import app.iti.client.iti_gp_client.contracts.LoginContract.*
import app.iti.client.iti_gp_client.screens.home.HomeActivity
import app.iti.client.iti_gp_client.screens.order_description.OrderActivity

/**
 * Displays the login screen
 */

class LoginActivity : AppCompatActivity(), View, android.view.View.OnFocusChangeListener {
    // reference to presenter
    var mPresenter:Presenter?=null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        // initialize presenter as LoginPresenter
        mPresenter = LoginPresenter()
        mPresenter?.initPresenter(this)
        // set focus on email and password edit text
        login_emailEditText.onFocusChangeListener = this
        login_passwordEditText.onFocusChangeListener= this

        // temporarily go to order activity from the logo in login screen
        login_logo.setOnClickListener {
            var myIntent = Intent(this, OrderActivity::class.java)
            startActivity(myIntent)
        }

    }

    // method to handle on click on register now textView and go to SignUpActivity
    fun toRegisterScreen(view:android.view.View){
        val intent = Intent(this, SignUpActivity::class.java)
        // start your register activity
        startActivity(intent)
        finish()
    }

    // method to handle on click on forgot password textView and go to ForgotPasswordActivity
    fun toForgotPassword(view:android.view.View){
        val intent = Intent(this, ForgotPasswordActivity::class.java)
        // start your forgot password activity
        startActivity(intent)
    }

    // login button action when clicked
    fun loginBtnEvent(view:android.view.View){
        val email:String = login_emailEditText.text.toString()
        val password:String = login_passwordEditText.text.toString()
        mPresenter?.login(email,password)

    }

    override fun setEmailError(error:String) {
            login_emailEditText.error = error
    }

    override fun setPasswordError(error:String) {
            login_passwordEditText.error = error
    }

    // on focus on edit texts fields
    override fun onFocusChange(v: android.view.View?, hasFocus: Boolean) {
        if (v == login_emailEditText && hasFocus == false) {
            mPresenter?.isEmailValid(login_emailEditText.text.toString())
        }
        if(v == login_passwordEditText && hasFocus == false){
            mPresenter?.isPasswordValid(login_passwordEditText.text.toString())
        }
    }

    override fun goToHomeScreen() {
        val intent = Intent(this, HomeActivity::class.java)
        // start home activity
        startActivity(intent)
        finish()
    }

}
