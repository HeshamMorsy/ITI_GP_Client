package app.iti.client.iti_gp_client.screens.order_description

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.OrderContract.*
import app.iti.client.iti_gp_client.utilities.Constants.Companion.CAMERA_REQUEST
import app.iti.client.iti_gp_client.utilities.Constants.Companion.READ_GALARY_REQUEST
import app.iti.client.iti_gp_client.utilities.Constants.Companion.WRITE_GALARY_REQUEST
import app.iti.client.iti_gp_client.utilities.Permissions.checkAccessCameraPermission
import app.iti.client.iti_gp_client.utilities.Permissions.checkReadGalleryPermission
import app.iti.client.iti_gp_client.utilities.Permissions.checkWriteStoragePermission
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File



/**
 * Responsible for handling actions in OrderActivity and updating UI if required
 */
class OrderPresenter : Presenter {
    // references to order model and order view
    private var mModel: Model? = null
    private var mView: View? = null
    private var counter = 0
    override var imageCounter: Int
        get() = counter
        set(value) {}
    // booleans to check if permissions granted or not
    var writeExternalStorage: Boolean? = false
    var readExternalStorage: Boolean? = false
    var accessCamera: Boolean? = false
    // array of paths to check if the image is chosen before or not
    var arrayOfImageNames: ArrayList<String>? = null


    // initialize presenter to start using it
    override fun initPresenter(view: View) {
        // initialize mModel as OrderModel and mView as the context sent from OrderActivity
        mModel = OrderModel()
        mView = view
        // array of paths to prevent adding one image more than one time
        arrayOfImageNames = ArrayList()

    }

    // method to show dialog for user to choose image from gallery or camera
    override fun getImageAction() {
        Log.i("OrderPresenter","getImageAction()")
        // getAlertDialog method returns reference to alertDialog.Builder to use it easily
        readExternalStorage = checkReadGalleryPermission(mView as Activity)
        writeExternalStorage = checkWriteStoragePermission(mView as Activity)
        accessCamera =checkAccessCameraPermission(mView as Activity)
        if(readExternalStorage!! && writeExternalStorage!! && accessCamera!!)
        EasyImage.openChooserWithGallery(mView as Activity, "Select Image", 10)
    }

    // convert arrayList file to a Bitmap
    override fun convertFilesToBitmap(imageFiles: List<File>){
        Log.i("image list size is", imageFiles.count().toString())
        /*if(counter == 0){
            arrayOfImageNames!!.add(imageFiles[0].name)
        }else{
            for(name in arrayOfImageNames!!){
                Log.i("path array",name)
                Log.i("path image",imageFiles[0].name)
                if(imageFiles[0].name == name){
                    Toast.makeText(mView as Activity , "This image added before, can't add image twice"
                            , Toast.LENGTH_SHORT).show()
                    Log.i("path repeated",imageFiles[0].path)
                }else{
                    Log.i("path","from else")
                    arrayOfImageNames!!.add(name)
                }
            }
        }*/


        val bitmap:Bitmap = BitmapFactory.decodeFile(imageFiles[0].path)
//         bitmap = compressBitmap(bitmap) // this is to test compressing the bitmap
        if(counter<=4)
        counter++
        mView?.updateImageView(bitmap,imageFiles[0].path ,counter)
    }

    // decrement the counter when removing an image
    override fun decrementCounter() {
        if(counter>0)
        --counter
    }

    // this method is to compress bitmap images
    /*private fun compressBitmap(bitmap: Bitmap):Bitmap{

        // Initialize a new ByteArrayStream
        val stream = ByteArrayOutputStream()
        // Compress the bitmap with JPEG format and quality 50%
        bitmap.compress(Bitmap.CompressFormat.JPEG, 10, stream)

        val byteArray = stream.toByteArray()
        return BitmapFactory.decodeByteArray(byteArray, 0, byteArray.size)
    }*/

}