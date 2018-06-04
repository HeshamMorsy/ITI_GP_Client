package app.iti.client.iti_gp_client.screens.signup

import android.content.Context
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.utilities.isNetworkAvailable

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpPresenter(var view:SignUpInt.View):SignUpInt.Presenter {
    val model = SignUpModel(this)
    private var emailValidation = false
    private var phoneValidation = false
    private var passwordValidation = false
    private var repasswordValidation = false

    override fun signUp(phone:String,email:String,pass:String,repass:String){

        if (isNetworkAvailable(view as Context)){
            Log.i("response","signup: "+ emailValidation+ phoneValidation+ passwordValidation + repasswordValidation )
            if (emailValidation && phoneValidation && passwordValidation && repasswordValidation){
                view.startLoading("sending data!!")
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
            Toast.makeText(view as Context,"there is no internet connection", Toast.LENGTH_SHORT).show()
        }


    }


    //check for phone validation
    override fun validatePhone(phone:String){
        val phoneRegex = "^01[0-5]\\d{7,8}$"

        if (!phone.matches(phoneRegex.toRegex())){
            phoneValidation = false
            view.phoneError("please enter a valid phone")
        }else{
            phoneValidation = true
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
            view.repasswordError("repassword doesnot match password")
        }
    }

    override fun receiveResponse(response: SignUpData){
        view.endLoading()
        Log.i("response", response.toString())
    }
}