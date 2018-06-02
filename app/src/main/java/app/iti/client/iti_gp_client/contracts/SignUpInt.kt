package app.iti.client.iti_gp_client.contracts

import android.widget.EditText
import app.iti.client.iti_gp_client.entities.SignUpData

/**
 * Created by Hazem on 5/30/2018.
 */
interface SignUpInt {
    interface Model{
        fun signUp(phone:String,email:String,pass:String)


    }
    interface View{
        fun emailError(error:String)
        fun phoneError(error:String)
        fun passwordError(error:String)
        fun repasswordError(error:String)
    }
    interface Presenter{
        fun signUp(phone:String,email:String,pass:String,repass:String)
        fun receiveResponse(response: SignUpData)
        fun validateEmail(toString: String)
        fun validatePassword(pass:String)
        fun validatePhone(phone:String)
        fun validateRePassword(pass:String,repass:String)
    }
}