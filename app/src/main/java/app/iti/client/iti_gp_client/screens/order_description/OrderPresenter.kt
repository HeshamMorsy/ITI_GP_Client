package app.iti.client.iti_gp_client.screens.order_description

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import app.iti.client.iti_gp_client.contracts.OrderContract.*
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

        val bitmap:Bitmap = BitmapFactory.decodeFile(imageFiles[0].path)
        if(counter<=4)
        counter++
        mView?.updateImageView(bitmap,imageFiles[0].path ,counter)
    }

    // decrement the counter when removing an image
    override fun decrementCounter() {
        if(counter>0)
        --counter
    }
}