package app.iti.client.iti_gp_client.screens.signup

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*

class SignUpActivity : AppCompatActivity(),SignUpInt.View,View.OnFocusChangeListener {



    //creating the presenter for the activity
    var presenter:SignUpInt.Presenter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)



        //initializing the presenter
        presenter = SignUpPresenter(this)


        //register the signup button click listner
        signUpButton.setOnClickListener {
            signUp()
        }

        //register the login label click listner
        loginLabel.setOnClickListener {
            gotoLoginPage()
        }

        //register the edittexts lose focus validation
        userEmail.setOnFocusChangeListener(this)
        userPhone.setOnFocusChangeListener(this)
        userPassword.setOnFocusChangeListener(this)
        userRepassword.setOnFocusChangeListener(this)
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
    fun validateEmail(){

    }

    //handling change focus
    override fun onFocusChange(p0: View?, p1: Boolean) {
        if (p0==userEmail && p1==true){
            Log.i("focus","email has focus")
        }else if(p0==userEmail && p1==false){
            presenter?.validateEmail(userEmail.text.toString())
        }else if(p0==userPhone && p1==true){
            Log.i("focus","phone has focus")
        }else if(p0==userPhone && p1==false){
            presenter?.validatePhone(userPhone.text.toString())
        }else if(p0==userPassword && p1==true){
            Log.i("focus","password has focus")
        }else if(p0==userPassword && p1==false){
            presenter?.validatePassword(userPassword.text.toString())
        }else if(p0==userRepassword && p1==true){
            Log.i("focus","repassword has focus")
        }else if(p0==userRepassword && p1==false){
            presenter?.validateRePassword(userPassword.text.toString(),userRepassword.text.toString())
        }
    }

    override fun emailError(error:String){
        userEmail.error = error
    }
    override fun phoneError(error:String){
        userPhone.error = error
    }
    override fun passwordError(error:String){
        userPassword.error = error
    }
    override fun repasswordError(error:String){
        userRepassword.error = error
    }


}
