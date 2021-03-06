package app.iti.client.iti_gp_client.screens.edit_profile

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.EditProfileContract.*
import app.iti.client.iti_gp_client.entities.Avatar
import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.entities.LoginedUser
import app.iti.client.iti_gp_client.utilities.Permissions.checkAccessCameraPermission
import app.iti.client.iti_gp_client.utilities.Permissions.checkReadGalleryPermission
import app.iti.client.iti_gp_client.utilities.Permissions.checkWriteStoragePermission
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File

/**
 * created by Hesham
 * Responsible for getting data from [EditProfileActivity] and send it to the model [EditProfileModel] to send it to backend
 * , handling actions in the screen and update UI if needed
 */

class EditProfilePresenter : Presenter {
    // reference to the model and the view
    private lateinit var mView:View
    private lateinit var mModel: Model
    // request body of user image
    private  var multipartBody: MultipartBody.Part? = null

    override fun initPresenter(view: View) {
        mView = view
        mModel = EditProfileModel()
        mModel.initModel(this)
    }

    override fun receiveResponse(response: EditProfileResponse) {
        mView.showMessage(response.message)
        mView.onRequestSuccess(response)
    }

    override fun errorResponse(error: Throwable) {
        if(error.message != null)
        mView.showMessage(error.message!!)
    }

    override fun sendChangesToModel(email: String, phone: String ,name: String) {

        // get token from shared preferences
        val defaultPref = PreferenceHelper.defaultPrefs(mView as Context)
        val auth = defaultPref.get("auth_token", "0")

        // add another part within the multipart request
        val reqName = RequestBody.create(MediaType.parse("text/plain"), name)
        val reqEmail = RequestBody.create(MediaType.parse("text/plain"), email)
        val reqPhone = RequestBody.create(MediaType.parse("text/plain"), phone)

        // create user object with basic data to send them as String if the user didn't change the image
        val userBasicData = LoginedUser(name,email,phone, Avatar(""))

        // send data to model to connect to update api
        mModel.requestToApi(auth!! ,reqName , reqEmail, reqPhone , multipartBody, userBasicData)

    }

    // method called when change profile image button clicked to get image from gallery or camera
    override fun getNewImage() {
        Log.i("OrderPresenter","getImageAction()")
        // getAlertDialog method returns reference to alertDialog.Builder to use it easily
        val readExternalStorage = checkReadGalleryPermission(mView as Activity)
        val writeExternalStorage = checkWriteStoragePermission(mView as Activity)
        val accessCamera =checkAccessCameraPermission(mView as Activity)
        if(readExternalStorage && writeExternalStorage && accessCamera)
            EasyImage.openChooserWithGallery(mView as Activity, "Select Image", 10)
    }

    // this method  is responsible for converting image file to bitmap
    override fun convertImageToBitmap(imageFiles: MutableList<File>) : Bitmap {
        val bitmap: Bitmap = BitmapFactory.decodeFile(imageFiles[0].path)
        multipartBody = createMultiPartBody(imageFiles[0].path)
//        mView.updateImageView(bitmap)
        return bitmap
    }

    // prepare image paths as MultiPartBody.Part to send it to the backend
    private fun createMultiPartBody(path: String): MultipartBody.Part {
            val file = File(path)
            // create RequestBody to parse the file into mutlipart/form-data
            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
            // create the MultiPartBody.Part as this type will be sent to the backEnd
            val multipartBody = MultipartBody.Part.createFormData("avatar", file.name, requestBody)
            return multipartBody
    }


}