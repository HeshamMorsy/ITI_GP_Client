package app.iti.client.iti_gp_client.screens.payment

import android.util.Log
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.FinalOrderRequest
import app.iti.client.iti_gp_client.services.createOrderRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


/**
 * Created by Hesham on 6/7/2018.
 * Responsible for connecting to backend in Payment Business
 */
class PaymentModel(val presenter: PaymentPresenter) : Model {
    override fun uploadOrderData(finalOrder: FinalOrderRequest) {
        val orderRequest = createOrderRequest()
        val options:Map<String, String> = hashMapOf("orderTitle" to finalOrder.orderData.title
        , "description" to finalOrder.orderData.description)

        orderRequest.uploadImageAndOrder(options, finalOrder.images)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleResponse, this::handleError)

    }

    private fun handleResponse(response: FinalOrderRequest) {
        presenter.receiveResponse(response)
    }

    private fun handleError(error: Throwable) {
        Log.i("error Response", "error receiving data"+error.message)
        presenter.errorResponse(error)
//        Toast.makeText(this, "Error ${error.localizedMessage}", Toast.LENGTH_SHORT).show()
    }
}