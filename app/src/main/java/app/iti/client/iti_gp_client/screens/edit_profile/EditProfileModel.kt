package app.iti.client.iti_gp_client.screens.edit_profile

import android.util.Log
import app.iti.client.iti_gp_client.contracts.EditProfileContract.*
import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.services.createEditProfileRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody

/**
 * Responsible for sending edited data of the user from [EditProfilePresenter] to the backend
 */
class EditProfileModel : Model {
    // reference to about presenter
    private lateinit var mPresenter:Presenter


    override fun initModel(presenter: Presenter) {
        mPresenter = presenter
    }

    override fun requestToApi(auth: String ,email: String, phone: String, name: String, multipartImage: MultipartBody.Part?) {
        val editProfileRequest = createEditProfileRequest()
        // set user data in a map query
        val options:Map<String, String> = hashMapOf("email" to email, "phone" to phone, "name" to name)

        if(multipartImage == null){
            // put data without image
            editProfileRequest.postWithoutImage(
                    options, auth)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
        }else {
            // then image is not equal null
            editProfileRequest.postEditProfile(
                    options, multipartImage, auth)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
        }

    }

    private fun handleResponse(response: EditProfileResponse) {
        mPresenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data "+error.message)
        mPresenter.errorResponse(error)
    }
}
