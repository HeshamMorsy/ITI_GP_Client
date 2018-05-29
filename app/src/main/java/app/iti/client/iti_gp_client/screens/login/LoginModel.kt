package app.iti.client.iti_gp_client.screens.login

import android.util.Log
import contract.interfaces.LoginContract.*

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for checking if the email and password exists and matches together in the login api, if requested from LoginPresenter
 */
class LoginModel : Model {

    // check if the user email and password exists and matches in the login api
    override fun checkEmailAndPassword(email: String, password: String) {
        Log.i("LoginModel","login succeeded")
    }
}