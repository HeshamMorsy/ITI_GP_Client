package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.PATCH
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Created by Hazem on 6/19/2018.
 */
interface CancelService {
    @PATCH("orders/cancel/{id}")
    fun cancelOrder(@Header("Authorization") auth: String, @Path("id") order_id: Int):Observable<CancelOrderResponse>
}
fun createCancelServiceRequest():CancelService{
    return RetrofitCreation.createRetrofit().create(CancelService::class.java)
}