package app.iti.client.iti_gp_client.screens.signup

import android.app.Activity
import android.content.Context
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.ResendDetails
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.utilities.isNetworkAvailable

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpPresenter(var view:SignUpInt.View):SignUpInt.Presenter {

    var model:SignUpInt.Model
    init {
         model = SignUpModel(this)
    }

    private var emailValidation = false
    private var phoneValidation = false
    private var passwordValidation = false
    private var repasswordValidation = false


    override fun signUp(phone:String,email:String,pass:String,repass:String){

        if (isNetworkAvailable(view as Context)){
            Log.i("response","signup: "+ emailValidation+ phoneValidation+ passwordValidation + repasswordValidation )
            if (emailValidation && phoneValidation && passwordValidation && repasswordValidation){
                view.startLoading((view as Activity).resources.getString(R.string.sendingData))
                model.signUp(phone, email, pass)
            }else if (!emailValidation){
                validateEmail(email)
            }else if (!phoneValidation){
                validatePhone(phone)
            }else if(!passwordValidation){
                validatePassword(pass)
            }else if(!repasswordValidation){
                validateRePassword(pass, repass)
            }
        }else{
            Toast.makeText(view as Context,(view as Activity).resources.getString(R.string.wrongMsg), Toast.LENGTH_SHORT).show()
        }


    }


    //check for phone validation
    override fun validatePhone(phone:String){
        val phoneRegex = "^01[0-5]\\d{7,8}$"

        if (!phone.matches(phoneRegex.toRegex())){
            phoneValidation = false
            view.phoneError((view as Activity).resources.getString(R.string.phoneValidError))
        }else{
            phoneValidation = true
        }
    }

    //check for email validation
    override fun validateEmail(email:String){
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        if (!email.matches(emailRegex.toRegex())){
            emailValidation = false
            view.emailError((view as Activity).resources.getString(R.string.emailValidError))
        }else{
            emailValidation = true
        }
    }

    //check for password validation
    override fun validatePassword(pass:String){
        val passWordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        if (!pass.matches(passWordRegex.toRegex())){
            passwordValidation = false
            view.passwordError((view as Activity).resources.getString(R.string.passwordNotStrongError))
        }else{
            passwordValidation = true
        }
    }

    //check if repassword matched password
    override fun validateRePassword(pass:String,repass:String){

        if (pass==repass ){
            repasswordValidation = true
        }else{
            repasswordValidation = false
            view.repasswordError((view as Activity).resources.getString(R.string.passwordNotMatch))
        }
    }

    override fun receiveResponse(response: SignUpData){
        view.endLoading()
        Log.i("response in presenter", response.toString())
        if (response.message.equals("success")){
            val auth_token = response.auth_token
            view.saveAuth_token(auth_token)
            view.showVerificationButtonSheet()

        }else{
            view.errorResponse(response.message)
        }

    }


    override fun receiveErrorResponse(){
        view.endLoading()
        view.errorResponse((view as Activity).resources.getString(R.string.serverError))
        Log.i("response", "presenter error response")
    }


    override fun verifyCode(pinCode: String,auth:String) {
        view.startLoading((view as Activity).resources.getString(R.string.verifying))
        model.verify(pinCode,auth)
    }


    override fun handleVerificationResponse(response: String) {
        view.endLoading()
        if (response.equals("success")){
            view.navigateToLogin()
        }else{
            view.errorResponse(response)
        }
    }


    override fun handleVerificationError(){
        view.endLoading()
        view.errorResponse((view as Activity).resources.getString(R.string.serverError))
    }
    override fun resendCode(auth: String) {
        view.startLoading((view as Activity).resources.getString(R.string.resendingCode))
        model.resendCode(auth)
    }

    override fun handleResendResponse(message: ResendDetails) {
        view.endLoading()
        if (message.message == "success"){

        }
    }
}