package app.iti.client.iti_gp_client.screens.order_description

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.provider.MediaStore
import android.util.Log
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.OrderContract.*
import kotlinx.android.synthetic.main.activity_order.*
import kotlinx.android.synthetic.main.fragment_request.*
import java.io.File

/**
 * Displays Order Screen
 */

class OrderActivity : AppCompatActivity(), View {
    // reference to order presenter
    var mPresenter:Presenter?=null
    // constant result code for choosing image from gallery
    val PICK_GALLERY_REQUEST = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)
        // initialize presenter
        mPresenter = OrderPresenter()
        mPresenter?.initPresenter(this)
    }

    // action when upload image clicked
    fun getImageToUpload(view:android.view.View){
        Log.i("OrderActivity","get button clicked!")
        // call method from presenter to show dialog for user to choose image from gallery or camera
        mPresenter?.getImageAction()
    }


    // method called from presenter to get image from gallery
    override fun getImageFromGallery() {
                val photoPickerIntent = Intent(Intent.ACTION_PICK)
                photoPickerIntent.type="image/*"
                startActivityForResult(photoPickerIntent,PICK_GALLERY_REQUEST)
//        var galleryIntent = Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI)
//        startActivityForResult(galleryIntent, PICK_GALLERY_REQUEST)
    }

    // method called from presenter to get image from camera
    override fun getImageFromCamera() {

    }

    // update imageView after getting image from gallery or from camera
    override fun updateImageView(data: Intent?) {
        val imageUri = data?.data
        val inputStream = contentResolver.openInputStream(imageUri)
        val selectedImage = BitmapFactory.decodeStream(inputStream)
        order_Image.setImageBitmap(selectedImage)
    }

    // method to get application package name
    override fun getApplicationPackageName(): String {
        return applicationContext.packageName
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        Log.i("request",requestCode.toString())
        Log.i("result",resultCode.toString())
        if(resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                PICK_GALLERY_REQUEST -> {
                    Log.i("result", "result = $requestCode")
                    updateImageView(data)
                }
                2 -> {
                    Log.i("result", "result = $requestCode")

                }

            }
        }
    }


}
