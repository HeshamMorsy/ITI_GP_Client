package app.iti.client.iti_gp_client.screens.edit_profile

import android.util.Log
import app.iti.client.iti_gp_client.contracts.EditProfileContract.*
import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.entities.LoginedUser
import app.iti.client.iti_gp_client.services.createEditProfileRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import okhttp3.MultipartBody
import okhttp3.RequestBody

/**
 * created by Hesham
 * Responsible for sending edited data of the user from [EditProfilePresenter] to the backend
 */
class EditProfileModel : Model {
    // reference to about presenter
    private lateinit var mPresenter:Presenter


    override fun initModel(presenter: Presenter) {
        mPresenter = presenter
    }

    override fun requestToApi(auth: String ,name: RequestBody, email: RequestBody, phone: RequestBody, avatar: MultipartBody.Part?, user: LoginedUser) {
        val editProfileRequest = createEditProfileRequest()
        // set user data in a map query
        val stringOptions: Map<String, String> = hashMapOf("email" to user.email, "phone" to user.phone, "name" to user.name)

        if(avatar == null){
            // put data without image
            editProfileRequest.postWithoutImage(
                    stringOptions, auth)
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeOn(Schedulers.io())
                    .subscribe(this::handleResponse, this::handleError)
        }else {
            // then image is not equal null
            editProfileRequest.postEditProfile(
                    email,phone,name, avatar, auth)
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
