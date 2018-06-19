package app.iti.client.iti_gp_client.screens.change_password

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ChangePasswordContract.*
import app.iti.client.iti_gp_client.entities.ChangePasswordResponse
import app.iti.client.iti_gp_client.screens.profile.ProfileActivity
import kotlinx.android.synthetic.main.activity_change_password.*

/**
 * created by Hesham 14/6/2018
 * Displays the change password screen
 */

class ChangePasswordActivity : AppCompatActivity(), View {
    //reference to presenter
    private lateinit var mPresneter:Presenter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_change_password)
        // initialize about presenter
        mPresneter = ChangePasswordPresenter()
        mPresneter.initPresenter(this)

    }

    override fun onRequestSuccess(response: ChangePasswordResponse) {

    }

    override fun onRequestError(error: Throwable) {

    }

    // handle on click on save button
    fun saveChangeEvent(view: android.view.View){
        val old_password = changePass_oldPass.text.toString()
        val new_password = changePass_newPass.text.toString()
        val confirm_password = changePass_confirmPass.text.toString()
        if(new_password == confirm_password)
        mPresneter.sendPasswordToModel(old_password , new_password , confirm_password)
        else
            Toast.makeText(this, resources.getString(R.string.wrongPasswordConfirmation),Toast.LENGTH_SHORT).show()
    }

    override fun showMessage(message: String) {
        Toast.makeText(this , message , Toast.LENGTH_SHORT).show()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val myIntent = Intent(this, ProfileActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}
