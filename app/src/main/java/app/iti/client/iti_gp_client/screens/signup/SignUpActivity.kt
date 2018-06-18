package app.iti.client.iti_gp_client.screens.signup

import android.app.AlertDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.TextView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.screens.home.HomeActivity
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import kotlinx.android.synthetic.main.activity_sign_up.*
import android.support.design.widget.BottomSheetDialog
import android.widget.Button
import android.widget.EditText
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.defaultPrefs
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.setValue
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.alertWithOneButton
import kotlinx.android.synthetic.main.verification_button_sheet.*


class SignUpActivity : AppCompatActivity(),SignUpInt.View,View.OnFocusChangeListener {

    //creating the presenter for the activity
    var presenter:SignUpInt.Presenter? = null

    lateinit var dialouge:AlertDialog

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sign_up)

        //check if user is not verified
        val bundle=intent.extras
        if(bundle!=null)
        {
            showVerificationButtonSheet()
        }

//        showVerificationButtonSheet()
        //try navigation drawer
        imageView2.setOnClickListener {
            val intent = Intent(this, HomeActivity::class.java)
            // start your next activity
            startActivity(intent)
        }


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

    private fun confirmCode() {
        Log.i("response","show confirm code clicked")
        var defaultPref = PreferenceHelper.defaultPrefs(this)
        val auth = defaultPref.get("auth_token","0")
        Log.i("auth_token",auth)
        presenter!!.verifyCode(verificationCode.text.toString(),auth!!)

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
        presenter?.validateRePassword(pass,repass)
        presenter?.signUp(phone,email,pass, repass)?: Log.i("error","presenter is null")
    }

    //handling change focus
    override fun onFocusChange(p0: View?, p1: Boolean) {
        if(p0==userEmail && p1==false){
            presenter?.validateEmail(userEmail.text.toString())
        }else if(p0==userPhone && p1==false){
            presenter?.validatePhone(userPhone.text.toString())
        }else if(p0==userPassword && p1==false){
            presenter?.validatePassword(userPassword.text.toString())
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

    override fun startLoading(mes:String){
        Log.i("response", "start loading function")
        val builder = AlertDialog.Builder(this)
        val dialougeView = layoutInflater.inflate(R.layout.progress_dialouge,null)
        val message = dialougeView.findViewById<TextView>(R.id.loadingmessage)
        message.text = mes
        builder.setView(dialougeView)
        builder.setCancelable(false)
        dialouge = builder.create()
        dialouge.show()
    }

    override fun endLoading(){
        Log.i("response", "end loading function")
        dialouge.dismiss()
    }

    override fun showVerificationButtonSheet(){
        Log.i("response","show verification buttonsheet")
        val mBottomSheetDialog = BottomSheetDialog(this)
        val sheetView = this.layoutInflater.inflate(R.layout.verification_button_sheet, null)
        //prevent dismiss


        var conver = sheetView.findViewById<Button>(R.id.confirmVerification)
        conver.setOnClickListener{
            Log.i("response","show confirm code clicked")
            Log.i("auth_token",getAuth())
            presenter!!.verifyCode(sheetView.findViewById<EditText>(R.id.verificationCode).text.toString(),getAuth()!!)
        }

        val resendVerification = sheetView.findViewById<Button>(R.id.sendVerification)
        resendVerification.setOnClickListener {
            presenter!!.resendCode(getAuth()!!)
        }
        mBottomSheetDialog.setContentView(sheetView)
        mBottomSheetDialog.show()


//        var touchOutside:View =mBottomSheetDialog.window.findViewById(android.support.design.R.id.touch_outside)
//        touchOutside.setOnClickListener(null)
        mBottomSheetDialog.setCancelable(false)
        mBottomSheetDialog.setCanceledOnTouchOutside(false)
        //register the confirm code button

    }

    private fun getAuth(): String? {
        var defaultPref = PreferenceHelper.defaultPrefs(this)
        return defaultPref.get("auth_token","0")
    }

    override fun errorResponse(msg:String){
        alertWithOneButton(this,"Error Message",msg,"Ok")
//        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
    override fun saveAuth_token(auth_token:String){
        var defaultPref = defaultPrefs(this)
        defaultPref.setValue("auth_token",auth_token)
    }
    override fun navigateToLogin() {
        val intent = Intent(this, LoginActivity::class.java)
        // start your next activity
        startActivity(intent)
        finish()
    }
}
