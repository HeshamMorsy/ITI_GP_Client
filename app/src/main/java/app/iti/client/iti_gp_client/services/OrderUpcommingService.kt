package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.OrderHistoryResponse
import app.iti.client.iti_gp_client.entities.OrderupcomingResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Path

/**
 * Created by Hazem on 6/16/2018.
 */
interface OrderUpcommingService {
    @GET("orders/showupcoming/{id}")
    fun getUpcommingOrders(@Path("id") pageNumber:Int,@Header("Authorization") auth:String ): Observable<OrderupcomingResponse>
}

fun createOrderUpcommingRequest():OrderUpcommingService{
    return RetrofitCreation.createRetrofit().create(OrderUpcommingService::class.java)
}