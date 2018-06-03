package app.iti.client.iti_gp_client.screens.signup

import android.util.Log
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.services.createSignUpRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpModel(var presenter: SignUpPresenter):SignUpInt.Model {
    override fun signUp(phone:String,email:String,pass:String) {
        var signUpRequest = createSignUpRequest()
        var options:Map<String, String> = hashMapOf("name" to "hazem","phone" to phone,"password" to pass,"email" to email)

        signUpRequest.getTokin(options)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(response: SignUpData) {
        Log.i("response", "response data"+response)
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        presenter.view.endLoading()

        Log.i("error", "error receiving data"+error.localizedMessage)

//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}