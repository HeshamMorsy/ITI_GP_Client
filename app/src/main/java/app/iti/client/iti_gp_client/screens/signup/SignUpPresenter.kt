package app.iti.client.iti_gp_client.screens.signup

import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import app.iti.client.iti_gp_client.services.createSignUpRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import kotlinx.android.synthetic.main.activity_sign_up.*
import java.net.CacheResponse

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpPresenter(var view:SignUpInt.View):SignUpInt.Presenter {
    val model = SignUpModel(this)
    private var emailValidation = false
    private var phoneValidation = false
    private var passwordValidation = false
    private var RepasswordValidation = false
    override fun signUp(phone:String,email:String,pass:String,repass:String){
        val phoneValidation = validatePhone(phone)

        val passwordValidation = validatePassword(pass)
        val repassValidation = pass == repass
        //"^(([A-Za-z 0-9])(?!.\"  \")){8,23}$"
        Log.i("signupData",phone + phoneValidation + email +emailValidation+ pass+passwordValidation + repass +repassValidation)
        model.signUp(phone, email, pass)
    }


    //check for phone validation
    override fun validatePhone(phone:String){
        val phoneRegex = "^01[0-5]\\d{7,8}$"

        if (!phone.matches(phoneRegex.toRegex())){
            phoneValidation = false
            view.phoneError("please enter a valid phone")
        }
    }

    //check for email validation
    override fun validateEmail(email:String){
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        if (!email.matches(emailRegex.toRegex())){
            emailValidation = false
            view.emailError("please enter a correct email")
        }else{
            emailValidation = true
        }
    }

    //check for password validation
    override fun validatePassword(pass:String){
        val passWordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        if (!pass.matches(passWordRegex.toRegex())){
            passwordValidation = false
            view.passwordError("please enter stronger password")
        }
    }

    //check if repassword matched password
    override fun validateRePassword(pass:String,repass:String){

        if (pass != repass){
            RepasswordValidation = false
            view.repasswordError("repassword doesnot match password")
        }
    }

    override fun receiveResponse(response: SignUpData){
        Log.i("error", response.toString())
    }
}