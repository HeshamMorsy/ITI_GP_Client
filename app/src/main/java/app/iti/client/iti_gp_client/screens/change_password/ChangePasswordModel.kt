package app.iti.client.iti_gp_client.screens.change_password

import android.util.Log
import app.iti.client.iti_gp_client.contracts.ChangePasswordContract.*
import app.iti.client.iti_gp_client.entities.ChangePasswordResponse
import app.iti.client.iti_gp_client.services.createChangePasswordRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Responsible for sending new user password from [ChangePasswordPresenter] to the backend server
 */

class ChangePasswordModel : Model {
    // reference to about presenter
    private lateinit var mPresenter:Presenter


    override fun initModel(presenter: Presenter) {
        mPresenter = presenter
    }

    override fun requestToApi(auth: String  ,old_password: String, new_password: String, confirm_password: String) {
        val changePassRequest = createChangePasswordRequest()
        // set user data in a map query
        val options:Map<String, String> = hashMapOf("password" to old_password,
                                                    "new_password" to new_password,
                                                    "confirm_password" to confirm_password)

        changePassRequest.postChangePassword(options, auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: ChangePasswordResponse) {
        mPresenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data "+error.message)
        mPresenter.errorResponse(error)
    }
}