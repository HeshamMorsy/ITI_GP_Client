package app.iti.client.iti_gp_client.screens.payment

import android.util.Log
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.FinalOrderRequest
import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.entities.OrderToBeSent
import app.iti.client.iti_gp_client.services.createOrderRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import retrofit2.HttpException


/**
 * Created by Hesham on 6/7/2018.
 * Responsible for connecting to backend in Payment Business
 */
class PaymentModel(val presenter: PaymentPresenter) : Model {
    override fun uploadOrderData(finalOrder: OrderToBeSent) {
        val orderRequest = createOrderRequest()
        /*val optionStrings:Map<String, String> =
                hashMapOf(
                "title" to finalOrder.title,
                "payment_method" to finalOrder.payment_method
                )

        val optionDoubles: Map<String,Double> =
                hashMapOf(
                        "src_latitude" to finalOrder.src_latitude,
                        "src_longitude" to finalOrder.src_longitude,
                        "dest_latitude" to finalOrder.dest_latitude,
                        "dest_longitude" to finalOrder.dest_longitude,
                        "weight" to finalOrder.weight,
                        "time" to finalOrder.time
        )

        orderRequest.uploadImageAndOrder(optionStrings, optionDoubles , finalOrder.provider_id , finalOrder.images)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
                */

        orderRequest.uploadData(finalOrder.title, finalOrder.time, finalOrder.provider_id, finalOrder.images, finalOrder.weight,
                finalOrder.payment_method, finalOrder.src_latitude, finalOrder.src_longitude, finalOrder.dest_latitude,
                finalOrder.dest_longitude)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)
/*
{
  "title": "some order title",
  "time": "in datetime format",
  "provider_id": 1,
  "images": [
    "image1",
    "image2",
    "image3"
  ],
  "weight": 90,
  "payment_method": "cash",
  "src_latitude": 30.123,
  "src_longitude": 30.3,
  "dest_latitude": 31.123,
  "dest_longitude": 31.123
}
    */
    }

    private fun handleResponse(response: OrderResponse) {
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "stackTrace : "+error.localizedMessage)
        var er = error as HttpException
        Log.i("error Response", "cause : "+er.response().errorBody().toString())
        Log.i("error Response", "error receiving data"+error.printStackTrace())
        presenter.errorResponse(error)
//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}