package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Hazem on 6/20/2018.
 */
interface RegisterTokinService {
    @POST("drivers/registertoken")
    fun registerTokin(@Header("Authorization") auth:String):Observable<CancelOrderResponse>
}

fun createRegisterTokinRequest():RegisterTokinService{
    return RetrofitCreation.createRetrofit().create(RegisterTokinService::class.java)
}