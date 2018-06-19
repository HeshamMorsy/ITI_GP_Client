package app.iti.client.iti_gp_client.screens.forgot_password.reset_password

import android.util.Log
import app.iti.client.iti_gp_client.contracts.NewPasswordContract.*
import app.iti.client.iti_gp_client.entities.NewPasswordResponse
import app.iti.client.iti_gp_client.services.createNewPasswordRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * created by Hesham
 */

class NewPasswordModel : Model {
    // reference to presenter
    lateinit var mPresenter: Presenter

    override fun initModel(presenter: Presenter) {
        mPresenter = presenter
    }

    override fun requestToApi(hash: String, new_password: String, confirm_password: String) {
        val newPasswordRequest = createNewPasswordRequest()
        val options:Map<String, String> = hashMapOf("password" to new_password , "confirm_password" to confirm_password)

        newPasswordRequest.sendNewPassword(options, hash)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: NewPasswordResponse) {
        mPresenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data "+error.message)
        mPresenter.errorResponse(error)
    }
}