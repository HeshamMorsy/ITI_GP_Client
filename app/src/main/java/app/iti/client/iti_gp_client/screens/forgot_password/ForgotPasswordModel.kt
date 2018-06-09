package app.iti.client.iti_gp_client.screens.forgot_password

import android.util.Log
import app.iti.client.iti_gp_client.contracts.ForgotPasswordContract.Model
import app.iti.client.iti_gp_client.entities.ForgotPasswordResponse
import app.iti.client.iti_gp_client.services.createForgotPasswordRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hesham on 5/29/2018.
 * Responsible for checking if the email or phone number exists and then send reset link or code to them
 */
class ForgotPasswordModel(val presenter: ForgotPasswordPresenter) : Model {
    override fun resetPassword(email: String) {
        val forgotRequest = createForgotPasswordRequest()
        val options:Map<String, String> = hashMapOf("email" to email)

        forgotRequest.getPassword(options)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: ForgotPasswordResponse) {
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data"+error.message)
        presenter.errorResponse()
//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }


}