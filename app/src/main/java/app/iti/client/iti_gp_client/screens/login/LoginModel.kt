package app.iti.client.iti_gp_client.screens.login

import android.util.Log
import app.iti.client.iti_gp_client.contracts.LoginContract.Model
import app.iti.client.iti_gp_client.entities.LoginResponse
import app.iti.client.iti_gp_client.services.createLoginRequest
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for checking if the email and password exists and matches together in the login api, if requested from LoginPresenter
 */
class LoginModel(val presenter: LoginPresenter, val user_auth: String ) : Model {

    // check if the user email and password exists and matches in the login api
    override fun requestToApi(email: String, password: String) {
        val loginRequest = createLoginRequest()

        // set user data in a hash map to send it in a map query
        val options:Map<String, String> = hashMapOf("email" to email, "password" to password)

            loginRequest.getTokin(options)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: LoginResponse) {
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data"+error.message)
        presenter.errorResponse()
//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}