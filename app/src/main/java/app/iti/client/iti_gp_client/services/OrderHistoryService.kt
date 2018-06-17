package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.OrderHistoryResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Hazem on 6/16/2018.
 */
interface OrderHistoryService{
    @GET("orders/showhistory/{id}")
    fun getHistoryOrders(@Path("id") pageNumber:Int,@Header("Authorization")auth:String ):Observable<OrderHistoryResponse>
}

fun createOrderHistoryRequest():OrderHistoryService{
    return RetrofitCreation.createRetrofit().create(OrderHistoryService::class.java)
}