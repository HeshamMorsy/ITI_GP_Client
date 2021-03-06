package app.iti.client.iti_gp_client.screens.forgot_password

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_forgot_password.*


/**
 * created by Hesham
 * Display forgot password screen
 */

class ForgotPasswordActivity : AppCompatActivity(), View{
    // reference to presenter
    private lateinit var mPresenter:Presenter
    private lateinit var dialog:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        // initialize the presenter as ForgotPasswordPresenter
        mPresenter = ForgotPasswordPresenter()
        mPresenter.initPresenter(this)

        // set on click on send reset link button
        forgot_sendResetBtn.setOnClickListener {
            val email = forgot_emailEditText.text.toString()
            mPresenter.sendResetBtnEvent(email)
//            mPresenter?.isEmailValid(forgot_emailEditText.text.toString())
        }
    }

    override fun setEmailError(error: String) {
        forgot_emailEditText.error = error
    }

    override fun startLoading(mes:String){
        Log.i("response", "start loading function")
        val builder = AlertDialog.Builder(this)
        val dialougeView = layoutInflater.inflate(R.layout.progress_dialouge,null)
        val message = dialougeView.findViewById<TextView>(R.id.loadingmessage)
        message.text = mes
        builder.setView(dialougeView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog.show()
    }

    override fun endLoading(result: String){
        Toast.makeText(this, result,Toast.LENGTH_SHORT).show()
        Log.i("response", "end loading function")
        dialog.dismiss()
    }

    override fun onBackPressed() {
//        super.onBackPressed()
        val myIntent = Intent(this, LoginActivity::class.java)
        startActivity(myIntent)
        finish()
    }

}
