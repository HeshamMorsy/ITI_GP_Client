package app.iti.client.iti_gp_client.screens.order_description

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AlertDialog
import android.util.Log
import app.iti.client.iti_gp_client.contracts.OrderContract.*
import app.iti.client.iti_gp_client.utilities.getAlertDialog
import java.io.File

/**
 * Responsible for handling actions in OrderActivity and updating UI if required
 */
class OrderPresenter : Presenter {
    // references to order model and order view
    private var mModel: Model? = null
    private var mView: View? = null
    private var counter = 0


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
        var alert: AlertDialog.Builder = getAlertDialog(mView as OrderActivity,"Get Image","Choose the way to get the image")
            // button to access gallery
        alert.setPositiveButton("Gallery",{ dialog,which ->
            checkReadGalleryPermission()
        })
            // button to access camera
        alert.setNegativeButton("Camera",{dialog, which ->
            mView?.getImageFromCamera()
        })
        alert.show()
    }

    // this method is to create file
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
    }

    private fun checkReadGalleryPermission(){
        val checkPermission = ContextCompat.checkSelfPermission((mView as OrderActivity),
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions((mView as OrderActivity),
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE),(mView as OrderActivity).PICK_GALLERY_REQUEST)
        }else{
            mView?.getImageFromGallery()
        }
    }

}