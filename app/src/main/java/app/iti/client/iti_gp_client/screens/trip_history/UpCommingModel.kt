package app.iti.client.iti_gp_client.screens.trip_history

import android.util.Log
import app.iti.client.iti_gp_client.contracts.UpcommingOrders
import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.entities.OrderupcomingResponse
import app.iti.client.iti_gp_client.services.createCancelServiceRequest
import app.iti.client.iti_gp_client.services.createOrderUpcommingRequest
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hazem on 6/16/2018.
 */
class UpCommingModel(var presenter:UpcommingOrders.Presenter):UpcommingOrders.Model {
    override fun getOrders(auth: String,pageNum:Int) {
        val upcomingOrderRequest = createOrderUpcommingRequest()
        upcomingOrderRequest.getUpcommingOrders(pageNum,auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleProvidersResponse, this::handleProvidersError)
    }
    fun handleProvidersResponse(response: OrderupcomingResponse){
        Log.i("response",response.toString())
        presenter.handleOrders(response)
    }
    fun handleProvidersError(error: Throwable){
        Log.i("response",error.toString())
    }

    override fun cancellTrip(auth: String?,position:Int) {
        val cancelOrderRequest = createCancelServiceRequest()
        cancelOrderRequest.cancelOrder(auth!!,position)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleCancelResponse, this::handlecancelError)
    }


    fun handleCancelResponse(response: CancelOrderResponse){
        Log.i("response",response.toString())
        presenter.handlecancelOrders(response)
    }
    fun handlecancelError(error: Throwable){
        Log.i("response",error.toString())
    }
}