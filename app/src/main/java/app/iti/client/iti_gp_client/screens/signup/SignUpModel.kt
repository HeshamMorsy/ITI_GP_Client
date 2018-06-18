package app.iti.client.iti_gp_client.screens.signup

import android.util.Log
import app.iti.client.iti_gp_client.contracts.SignUpInt
import app.iti.client.iti_gp_client.entities.ResendDetails
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.entities.VerifyData
import app.iti.client.iti_gp_client.services.createResendRequest
import app.iti.client.iti_gp_client.services.createSignUpRequest
import app.iti.client.iti_gp_client.services.createVerifyRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hazem on 5/30/2018.
 */
class SignUpModel(var presenter: SignUpPresenter):SignUpInt.Model {

    override fun signUp(phone:String,email:String,pass:String) {
        var signUpRequest = createSignUpRequest()
        var options:Map<String, String> = hashMapOf("phone" to phone,"password" to pass,"email" to email)

        signUpRequest.getTokin(options)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
    }

    private fun handleResponse(response: SignUpData) {
        Log.i("response", "response data: "+response)

        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error", "error receiving data"+error.localizedMessage)
        presenter.receiveErrorResponse()

//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }

    override fun verify(pin:String,auth:String){
        Log.i("response","model veriy fn")
        var verificationRequest = createVerifyRequest()
        verificationRequest.confirmCode(pin,auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleVerificationResponse, this::handleVerificationError)

    }

    private fun handleVerificationResponse(response: VerifyData) {
        Log.i("response", "response verification data"+response)
        presenter.handleVerificationResponse(response.message)
    }
    private fun handleVerificationError(error: Throwable) {
        Log.i("error", "error receiving data"+error.localizedMessage)
        presenter.handleVerificationError()

    }

    override fun resendCode(auth: String) {
        var resendVRequest = createResendRequest()
        resendVRequest.resendVerificationCode(auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResendResponse, this::handleResendError)
    }

    private fun handleResendResponse(response: ResendDetails) {
        Log.i("response", "response resend data"+response)
        presenter.handleResendResponse(response)
    }
    private fun handleResendError(error: Throwable) {
        Log.i("error", "error receiving resend data"+error.localizedMessage)
        presenter.handleVerificationError()

    }
}