package app.iti.client.iti_gp_client.screens.order_description

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log
import app.iti.client.iti_gp_client.contracts.OrderContract.*
import app.iti.client.iti_gp_client.utilities.Constants.Companion.CAMERA_REQUEST
import app.iti.client.iti_gp_client.utilities.Constants.Companion.READ_GALARY_REQUEST
import app.iti.client.iti_gp_client.utilities.Constants.Companion.WRITE_GALARY_REQUEST
import pl.aprilapps.easyphotopicker.EasyImage
import java.io.File


/**
 * Responsible for handling actions in OrderActivity and updating UI if required
 */
class OrderPresenter : Presenter {
    // references to order model and order view
    private var mModel: Model? = null
    private var mView: View? = null
    var counter = 0
    override var imageCounter: Int
        get() = counter
        set(value) {value}


    // initialize presenter to start using it
    override fun initPresenter(view: View) {
        // initialize mModel as OrderModel and mView as the context sent from OrderActivity
        mModel = OrderModel()
        mView = view

    }

    // method to show dialog for user to choose image from gallery or camera
    override fun getImageAction() {
        Log.i("OrderPresenter","getImageAction()")
        // getAlertDialog method returns reference to alertDialog.Builder to use it easily
        checkReadGalleryPermission()
        checkWriteStoragePermission()
        checkAccessCameraPermission()
        EasyImage.openChooserWithGallery(mView as Activity, "Select Image", 10)

    }

    /*// this method is to create file
    override fun getFile() : File? {
        val fileDir = File(Environment.getExternalStorageDirectory().toString()
                +"/Android/data/"+ mView?.getApplicationPackageName() + "/Files")
        if(!fileDir.exists()){
            if(!fileDir.mkdirs()) {
                return null
            }
        }

        val mediaFile = File(fileDir.getPath() + File.separator + "temp$counter.jpg")
        counter++
        return mediaFile
    }*/

    private fun checkReadGalleryPermission(): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission((mView as Activity),
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((mView as Activity),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), READ_GALARY_REQUEST!!)
        }else{
            Log.i("Permission","Read External Storage Granted")
            check = true
        }
        return check
    }

    private fun checkWriteStoragePermission(): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission((mView as Activity),
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((mView as Activity),
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), WRITE_GALARY_REQUEST)
        }else{
            Log.i("Permission","Write External Storage Granted")
            check = true
        }
        return check
    }

    private fun checkAccessCameraPermission(): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission((mView as Activity),
                Manifest.permission.CAMERA)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((mView as Activity),
                    arrayOf(Manifest.permission.CAMERA), CAMERA_REQUEST)
        }else{
            Log.i("Permission","Camera Granted")
            check =  true
        }
        return check
    }

//    override fun getBitmapFromData(data: Intent?): ArrayList<Bitmap> {
//        /*val imageUri = data?.data
//        val inputStream = (mView as Activity).contentResolver.openInputStream(imageUri)
//        val selectedImage = BitmapFactory.decodeStream(inputStream)
//        mView?.updateImageView(selectedImage)*/
//        val images = data!!.getParcelableArrayListExtra<Image>(Constants.INTENT_EXTRA_IMAGES)
//        val arrayOfBitmap = convertPathesToBitmap(images)
//        return arrayOfBitmap
//    }

    override fun convertFilesToBitmap(imageFiles: List<File>){
        print("image list size is "+imageFiles.size)
        var bitmap:Bitmap = BitmapFactory.decodeFile(imageFiles[0].path)
        if(counter<=4)
        counter++
        mView?.updateImageView(bitmap,counter)
    }

    override fun decrementCounter() {
        if(counter>0)
        --counter
    }

}