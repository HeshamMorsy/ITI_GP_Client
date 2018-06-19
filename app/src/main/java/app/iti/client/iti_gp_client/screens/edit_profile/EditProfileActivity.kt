package app.iti.client.iti_gp_client.screens.edit_profile

import android.content.Context
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.EditProfileContract.Presenter
import app.iti.client.iti_gp_client.contracts.EditProfileContract.View
import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.screens.profile.ProfileActivity
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.setValue
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_edit_profile.*
import pl.aprilapps.easyphotopicker.DefaultCallback
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Displays the edit profile screen
 */

class EditProfileActivity : AppCompatActivity(), View {
    //reference to presenter
    private lateinit var mPresneter:Presenter
    private var bitmap: Bitmap? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_profile)
        // initialize about presenter
        mPresneter = EditProfilePresenter()
        mPresneter.initPresenter(this)
        // set user values in the edit texts
        val defaultPref = PreferenceHelper.defaultPrefs(this)
        val email = defaultPref.get("email","0")
        val name = defaultPref.get("name","No name")
        val phone = defaultPref.get("phone","0")
        val imageUrl = defaultPref.get("avatar","")
        if(imageUrl != "")
        Glide.with(this).load(imageUrl).into(editProfile_image)
        editProfile_newEmail.setText(email)
        editProfile_newName.setText(name)
        editProfile_newPhone.setText(phone)

    }

    override fun onRequestSuccess(response: EditProfileResponse) {
        // save authentication token in shared preferences
        val defaultPref = PreferenceHelper.defaultPrefs(this)
        defaultPref.setValue("email",response.user.email)
        defaultPref.setValue("phone",response.user.phone)
        defaultPref.setValue("name",response.user.name)
        defaultPref.setValue("avatar",response.user.avatar.url)
        val myIntent = Intent(this,ProfileActivity::class.java)
        startActivity(myIntent)
        finish()
    }

    override fun onRequestError(error: Throwable) {

    }


    // handle on change profile image button click
    fun changeProfileImage(view: android.view.View){
        mPresneter.getNewImage()
    }

    // handle on click on save button
    fun saveChangesEvent(view: android.view.View){
        val email = editProfile_newEmail.text.toString()
        val phone = editProfile_newPhone.text.toString()
        val name  = editProfile_newName.text.toString()

        mPresneter.sendChangesToModel(email, phone, name)
    }

    override fun showMessage(msg: String) {
        Toast.makeText(this, msg , Toast.LENGTH_SHORT).show()
    }


    // method to get images after coming back to the activity
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("request",requestCode.toString())
        Log.i("result",resultCode.toString())
        EasyImage.handleActivityResult(requestCode, resultCode, data, this, object : DefaultCallback() {
            override fun onImagesPicked(imageFiles: MutableList<File>, source: EasyImage.ImageSource?, type: Int) {
                mPresneter.convertImageToBitmap(imageFiles)
            }

            override fun onImagePickerError(e: Exception?, source: EasyImage.ImageSource?, type: Int) {
                //Some error handling

            }
        })
    }

    // set user profile image after converting it to bitmap
    override fun updateImageView(bitmap: Bitmap) {
        this.bitmap = bitmap
        editProfile_image.setImageBitmap(bitmap)
    }
}
