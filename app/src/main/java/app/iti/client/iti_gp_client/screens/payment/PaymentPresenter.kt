package app.iti.client.iti_gp_client.screens.payment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.entities.OrderToBeSent
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.RequestCreation
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import java.util.*
import kotlin.collections.ArrayList
import android.graphics.Bitmap
import java.io.ByteArrayOutputStream
import kotlin.collections.HashMap


/**
 * Created by Hesham on 6/7/2018.
 * Responsible for handling actions in [PaymentActivity] and updating UI if required
 */
class PaymentPresenter : Presenter {
    // references to model and view of payment
    private var mModel: Model? = null
    private var mView: View? = null

    override fun initPresenter(view: View) {
        mView = view
        mModel = PaymentModel(this)
    }



    override fun receiveResponse(response: OrderResponse) {
        Log.i("Response status","data sent")
        Log.i("Response status",response.message)
        Toast.makeText(mView as PaymentActivity, response.message ,Toast.LENGTH_SHORT).show()
    }

    override fun errorResponse(error: Throwable) {
        Log.i("Response status","error")
        Toast.makeText(mView as PaymentActivity, "error localized : ${error.localizedMessage}",Toast.LENGTH_LONG).show()
    }

    override fun prepareOrderAndSend(order: Order) {
        RequestCreation.images = createMultiPartBody(order.paths)
        RequestCreation.payment_method = "cash"
        RequestCreation.weight = order.weight.toDouble()
        /*// be5 ay 7aga
        RequestCreation.title = "be5"
        RequestCreation.time = "2018-12-5 12:30:35"
        RequestCreation.src_latitude = 31.2
        RequestCreation.src_longitude = 31.2
        RequestCreation.dest_latitude = 32.2
        RequestCreation.dest_longitude = 32.2
        RequestCreation.provider_id = 1
        RequestCreation.description = "fdmsfds"*/
        Log.i("Order before upload",RequestCreation.toString())

        // get auth_token from sharedPreferences
        val defaultPref = PreferenceHelper.defaultPrefs(mView as Activity)
        val auth = defaultPref.get("auth_token","0")
        Toast.makeText((mView as Activity),auth,Toast.LENGTH_SHORT).show()
        mModel?.uploadOrderData( auth!!)


    }

//    // prepare image paths as MultiPartBody.Part to send it to the backend
//    private fun createMultiPartBody(paths: ArrayList<String>): ArrayList<MultipartBody.Part> {
//        val multipartBodyArr:ArrayList<MultipartBody.Part> = ArrayList()
////        val map = HashMap<String, RequestBody>()
//        for (path in paths){
//            // create file from path
//            val file = File(path)
//            // create RequestBody to parse the file into mutlipart/form-data
//            val requestBody = RequestBody.create(MediaType.parse("*/*"),file)
//
////            map.put("file\"; filename=\""+file.name+"\"", requestBody)
//
//            // create the MultiPartBody.Part as this type will be sent to the backEnd
//            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
//            multipartBodyArr.add(multipartBody)
//            Toast.makeText(mView as PaymentActivity, "file name:${file.name}",Toast.LENGTH_LONG).show()
//            Toast.makeText(mView as PaymentActivity, "array index:${multipartBodyArr[0]}",Toast.LENGTH_LONG).show()
//        }
//
//        return multipartBodyArr
////        return map
//    }



    // prepare image paths as MultiPartBody.Part to send it to the backend
    private fun createMultiPartBody(paths: ArrayList<String>): HashMap<String,ArrayList<MultipartBody.Part>> {
        // create
        val multipartBodyArr:ArrayList<MultipartBody.Part> = ArrayList()
        for (path in paths){

            // create file from path
            val file = File(path)

            // create RequestBody to parse the file into mutlipart/form-data
            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
            // create the MultiPartBody.Part as this type will be sent to the backEnd
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
            multipartBodyArr.add(multipartBody)

            Toast.makeText(mView as PaymentActivity, "file name:${file.name}",Toast.LENGTH_LONG).show()
            Toast.makeText(mView as PaymentActivity, "array index:${multipartBodyArr[0]}",Toast.LENGTH_LONG).show()
        }
        val map: HashMap<String,ArrayList<MultipartBody.Part>> =
                hashMapOf("images" to multipartBodyArr)

        return map
    }

    // convert paths to bitmaps
    /*private fun convertPathsToBitmap(pathList: ArrayList<String>): ArrayList<Bitmap> {
        val bitArrayList = ArrayList<Bitmap>()
        var bitmap:Bitmap? = null
        for (path in pathList){
            bitmap = BitmapFactory.decodeFile(path)
            bitArrayList.add(bitmap)
        }
        mView?.updateImage(bitmap!!)
        return bitArrayList
    }*/

    // convert bitmaps into base64
   /* private fun convertImageToBase64(paths: ArrayList<String>): ArrayList<String> {
        val convertedArray = ArrayList<String>()
        for(path in paths){
            val bytes = File(path).readBytes()
            val base64 = Base64.getEncoder().encodeToString(bytes)
            convertedArray.add(base64)
        }

        return convertedArray
    }*/

}