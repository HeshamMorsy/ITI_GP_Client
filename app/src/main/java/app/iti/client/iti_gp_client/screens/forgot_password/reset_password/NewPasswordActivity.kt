package app.iti.client.iti_gp_client.screens.forgot_password.reset_password

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.NewPasswordContract.*
import app.iti.client.iti_gp_client.entities.NewPasswordResponse
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_new_password.*

/**
 * created by Hesham
 */

class NewPasswordActivity : AppCompatActivity(), View {
    // reference to presenter
    lateinit var mPresenter: Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_new_password)
        mPresenter = NewPasswordPresenter()
        mPresenter.initPresenter(this)
    }

    override fun onRequestSuccess(response: NewPasswordResponse) {
        Toast.makeText(this, response.message, Toast.LENGTH_SHORT).show()
        // go to sign in activity
        val myIntent = Intent(this , LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    override fun onRequestError(error: Throwable) {
        Toast.makeText(this, error.localizedMessage, Toast.LENGTH_SHORT).show()
    }

    // method to handle action on save changes button
    fun saveNewPassowrdEvent(view: android.view.View){
        // get hash from the reset password url
        val myIntent = intent.data
        var hash = ""
        if(intent != null) {
             hash = myIntent.getQueryParameter("hash")
        }else{
            Toast.makeText(this, resources.getString(R.string.wrong), Toast.LENGTH_SHORT).show()
        }
        Log.i("hash","hash = $hash")

        // get password and confirm password from the layout
        val password = newPass_pass.text.toString()
        val confirm_password = newPass_confirmPass.text.toString()

        // check passwords validations
        val checkPassword = mPresenter.isPasswordValid(password)
        val checkConfirmPass = mPresenter.isPasswordValid(confirm_password)

        if(!checkPassword) Toast.makeText(this, resources.getString(R.string.passwordNotValid), Toast.LENGTH_SHORT).show()
        if(!checkConfirmPass) Toast.makeText(this, resources.getString(R.string.confirmPasswordNotValid), Toast.LENGTH_SHORT).show()


        if((password == confirm_password) && (hash != "")){
            if(mPresenter.isPasswordValid(password) && mPresenter.isPasswordValid(confirm_password)){
                mPresenter.sendPasswordToModel(hash , password , confirm_password )
            }
        }else{
            Toast.makeText(this, resources.getString(R.string.passwordNotMatch), Toast.LENGTH_SHORT).show()
        }
    }

}
