package app.iti.client.iti_gp_client.screens.forgot_password

import android.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.*
import kotlinx.android.synthetic.main.activity_forgot_password.*


/**
 * Display forgot password screen
 */

class ForgotPasswordActivity : AppCompatActivity(), View{
    // reference to presenter
    var mPresenter:Presenter? = null
    var dialoge:AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_password)
        // initialize the presenter as ForgotPasswordPresenter
        mPresenter = ForgotPasswordPresenter()
        mPresenter?.initPresenter(this)

        // set on click on send reset link button
        forgot_sendResetBtn.setOnClickListener {
            val email = forgot_emailEditText.text.toString()
            mPresenter?.sendResetBtnEvent(email)
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
        dialoge = builder.create()
        dialoge?.show()
    }

    override fun endLoading(){
        Log.i("response", "end loading function")
        dialoge?.dismiss()
    }

}
