package app.iti.client.iti_gp_client.utilities

import android.Manifest
import android.app.Activity
import android.content.pm.PackageManager
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.util.Log

object Permissions {
    // check READ_EXTERNAL_STORAGE permission
    fun checkReadGalleryPermission(activity: Activity): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.READ_EXTERNAL_STORAGE)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.READ_EXTERNAL_STORAGE), Constants.READ_GALARY_REQUEST)
        }else{
            Log.i("Permission","Read External Storage Granted")
            check = true
        }
        return check
    }

    // check WRITE_EXTERNAL_STORAGE permission
     fun checkWriteStoragePermission(activity: Activity): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.WRITE_EXTERNAL_STORAGE)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE), Constants.WRITE_GALARY_REQUEST)
        }else{
            Log.i("Permission","Write External Storage Granted")
            check = true
        }
        return check
    }

    // check CAMERA permission
     fun checkAccessCameraPermission(activity: Activity): Boolean{
        var check = false
        val checkPermission = ContextCompat.checkSelfPermission(activity,
                Manifest.permission.CAMERA)
        if(checkPermission != PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(activity,
                    arrayOf(Manifest.permission.CAMERA), Constants.CAMERA_REQUEST)
        }else{
            Log.i("Permission","Camera Granted")
            check =  true
        }
        return check
    }
}