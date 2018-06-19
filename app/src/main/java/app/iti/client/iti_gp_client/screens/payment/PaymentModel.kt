package app.iti.client.iti_gp_client.screens.payment

import android.util.Log
import app.iti.client.iti_gp_client.contracts.PaymentContract.Model
import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.services.createOrderRequest
import app.iti.client.iti_gp_client.utilities.RequestCreation
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Hesham on 6/7/2018.
 * Responsible for connecting to backend in Payment Business
 */
class PaymentModel(val presenter: PaymentPresenter) : Model {
    override fun uploadOrderData(auth: String) {
        val orderRequest = createOrderRequest()

        // print all order data to check if it's empty or not
        Log.i("RequestCreation data" , RequestCreation.toString())

        orderRequest.uploadData(auth ,RequestCreation.title!!, RequestCreation.description!!,
                RequestCreation.time!!,RequestCreation.provider_id!!,RequestCreation.images,RequestCreation.weight!!,
                RequestCreation.payment_method!!,RequestCreation.src_latitude!!,RequestCreation.src_longitude!!,
                RequestCreation.dest_latitude!!,RequestCreation.dest_longitude!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: OrderResponse) {
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "stackTrace : "+error.localizedMessage)
        presenter.errorResponse(error)
    }
}