package app.iti.client.iti_gp_client.screens.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),SignUpInt.View {


    //creating the presenter for the activity
    var presenter:SignUpPresenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        //initializing the presenter
        presenter = SignUpPresenter(this)

        signUpButton.setOnClickListener {
            signUp()
        }
        loginLabel.setOnClickListener {
            gotoLoginPage()
        }

    }
    fun gotoLoginPage(){
        val intent = Intent(this, LoginActivity::class.java)
        // start your next activity
        startActivity(intent)
        finish()
    }
    fun signUp(){
        val phone = userPhone.text.toString()
        val email = userEmail.text.toString()
        val pass  = userPassword.text.toString()
        val repass = userRepassword.text.toString()
        presenter?.signUp(phone,email,pass, repass)?: Log.i("error","presenter is null")
    }

}
