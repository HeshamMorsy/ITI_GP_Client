package app.iti.client.iti_gp_client.screens.payment

import android.graphics.Bitmap
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import android.graphics.BitmapFactory
import android.os.Build
import android.support.annotation.RequiresApi
import android.util.Base64.encodeToString
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.entities.FinalOrderRequest
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.OrderToBeSent
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.ByteArrayOutputStream
import java.io.File
import java.util.*
import kotlin.collections.ArrayList


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



    override fun receiveResponse(response: FinalOrderRequest) {
        Log.i("Response status","data sent")
        Toast.makeText(mView as PaymentActivity, "data sent",Toast.LENGTH_SHORT).show()
    }

    override fun errorResponse(error: Throwable) {
        Log.i("Response status","error")
        Toast.makeText(mView as PaymentActivity, "error",Toast.LENGTH_SHORT).show()
    }

    override fun prepareOrderAndSend(order: Order) {
        val imgArrayToSend = createMultiPartBody(order.paths)
//        val finalOrderRequest = FinalOrderRequest(arrayToSend,order)
        // the object of order entity to be sent to backend
        val orderToBeSent = OrderToBeSent(order.title,23.3 ,1,50.5,"cash",imgArrayToSend
                ,0.0,0.0,0.0 ,0.0)
        mModel?.uploadOrderData(orderToBeSent)

    }

    // prepare image paths as MultiPartBody.Part to send it to the backend
    private fun createMultiPartBody(paths: ArrayList<String>): ArrayList<MultipartBody.Part>{
        val multipartBodyArr:ArrayList<MultipartBody.Part> = ArrayList()

        for (path in paths){
            // create file from path
            val file = File("path")
            // create RequestBody to parse the file into mutlipart/form-data
            val requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),file)
            // create the MultiPartBody.Part as this type will be sent to the backEnd
            val multipartBody = MultipartBody.Part.createFormData("file", file.name, requestBody)
            multipartBodyArr.add(multipartBody)
        }

        return multipartBodyArr
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