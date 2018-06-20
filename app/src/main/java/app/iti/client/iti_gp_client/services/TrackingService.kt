package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.TrackingRequest
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Path

/**
 * Created by Hazem on 6/20/2018.
 */

interface TrackingService {
    @GET("orders/track/{id}")
    fun trackLocation(@Header("Authorization") auth:String,@Path("id") id:Int):Observable<TrackingRequest>
}
fun createTrackingRequest():TrackingService{
    return RetrofitCreation.createRetrofit().create(TrackingService::class.java)
}