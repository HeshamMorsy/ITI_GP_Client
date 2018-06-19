package app.iti.client.iti_gp_client.contracts

import android.graphics.Bitmap
import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.entities.LoginedUser
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File

interface EditProfileContract {
    interface Model {
        fun initModel(presenter: Presenter)
        fun requestToApi(auth: String, name: RequestBody, email: RequestBody, phone: RequestBody, avatar: MultipartBody.Part?, user: LoginedUser)
    }
    interface View {
        fun onRequestSuccess(response: EditProfileResponse)
        fun onRequestError(error: Throwable)
        fun showMessage(msg: String)
//        fun updateImageView(bitmap: Bitmap)
    }
    interface Presenter {
        fun initPresenter(view: View)
        fun receiveResponse(response: EditProfileResponse)
        fun errorResponse(error: Throwable)
        fun sendChangesToModel(email: String, phone: String, name: String)
        fun getNewImage()
        fun convertImageToBitmap(imageFiles: MutableList<File>) : Bitmap
    }
}