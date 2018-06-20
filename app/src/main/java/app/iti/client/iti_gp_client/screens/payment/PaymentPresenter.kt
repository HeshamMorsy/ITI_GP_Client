package app.iti.client.iti_gp_client.screens.payment

import android.app.Activity
import android.util.Log
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.utilities.Constants.Companion.TOKEN_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.RequestCreation
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File


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
        mView?.endLoading()
        Log.i("Response status","data sent")
        Log.i("Response status",response.message)
        Toast.makeText(mView as PaymentActivity, response.message ,Toast.LENGTH_SHORT).show()
        mView!!.goToHomeScreen()
    }

    override fun errorResponse(error: Throwable) {
        mView?.endLoading()
        Log.i("Response status","error")
        Toast.makeText(mView as PaymentActivity, error.localizedMessage,Toast.LENGTH_LONG).show()
    }

    override fun prepareOrderAndSend(order: Order) {
        mView?.startLoading((mView!! as Activity).resources.getString(R.string.sending))
        RequestCreation.images = createMultiPartBody(order.paths)
        RequestCreation.payment_method = "cash"
        RequestCreation.weight = order.weight.toDouble()
        Log.i("Order before upload",RequestCreation.toString())

        // get auth_token from sharedPreferences
        val defaultPref = PreferenceHelper.defaultPrefs(mView as Activity)
        val auth = defaultPref.get(TOKEN_SHARED_PREFERENCE,"")
        mModel?.uploadOrderData( auth!!)


    }



    // prepare image paths as MultiPartBody.Part to send it to the backend
    private fun createMultiPartBody(paths: ArrayList<String>): MultipartBody.Part {
        // create
        val multipartBodyArr:ArrayList<MultipartBody.Part> = ArrayList()
        // for future plan to send more than one image
//        for (path in paths){
//
//            // create file from path
//            val file = File(path)
//
//            // create RequestBody to parse the file into mutlipart/form-data
//            val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
//            // create the MultiPartBody.Part as this type will be sent to the backEnd
//            val multipartBody = MultipartBody.Part.createFormData("image", file.name, requestBody)
//            multipartBodyArr.add(multipartBody)
//
//            Toast.makeText(mView as PaymentActivity, "file name:${file.name}",Toast.LENGTH_LONG).show()
//            Toast.makeText(mView as PaymentActivity, "array index:${multipartBodyArr[0]}",Toast.LENGTH_LONG).show()
//        }
//        val map: HashMap<String,ArrayList<MultipartBody.Part>> =
//                hashMapOf("images" to multipartBodyArr)

        // this is to send one image to the backend
        val file = File(paths[0])
        val requestBody = RequestBody.create(MediaType.parse("image/*"),file)
        // create the MultiPartBody.Part as this type will be sent to the backEnd
        val multipartBody = MultipartBody.Part.createFormData("images", file.name, requestBody)

        return multipartBody
    }

}