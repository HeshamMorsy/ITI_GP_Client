package app.iti.client.iti_gp_client.screens.signup

import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.services.createSignUpRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import junit.framework.Assert.assertTrue
import java.net.CacheResponse

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpPresenter(var view:SignUpInt.View):SignUpInt.Presenter {
    val model = SignUpModel(this)
    override fun signUp(phone:String,email:String,pass:String,repass:String){
        val phoneValidation = validatePhone(phone)
        val emailValidation = validateEmail(email)
        val passwordValidation = validatePassword(pass)
        val repassValidation = pass == repass
        //"^(([A-Za-z 0-9])(?!.\"  \")){8,23}$"
        Log.i("signupData",phone + phoneValidation + email +emailValidation+ pass+passwordValidation + repass +repassValidation)
        model.signUp(phone, email, pass)
    }



    fun validatePhone(phone:String):Boolean{
        val phoneRegex = "^01[0-5]\\d{7,8}$"

        return phone.matches(phoneRegex.toRegex())
    }
    fun validateEmail(email:String):Boolean{
        val emailRegex = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,64}"
        return email.matches(emailRegex.toRegex())
    }
    fun validatePassword(pass:String):Boolean{
        val passWordRegex = "^((?!.*\\s)(?=.*[a-zA-Z])(?=.*\\d)).{6,12}$"
        return pass.matches(passWordRegex.toRegex())
    }

    override fun receiveResponse(response: SignUpData){
        Log.i("error", response.toString())
    }
}