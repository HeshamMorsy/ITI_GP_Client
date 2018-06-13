package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ProvidersRequest
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.GET
import retrofit2.http.Header

/**
 * Created by Hazem on 6/13/2018.
 */
interface ProvidersService {
    @GET("providers")
    fun getProviders(@Header("Authorization") auth:String):Observable<ProvidersRequest>
}
fun createProvidersRequest():ProvidersService{
    return RetrofitCreation.createRetrofit().create(ProvidersService::class.java)
}