package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.entities.VerifyData
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST
import retrofit2.http.Query

/**
 * Created by Hazem on 6/12/2018.
 */
interface VerifyService {
    @POST("authentication/verify")
    fun confirmCode(@Query("verification_pin") auth_token:String,@Header("Authorization") auth:String): Observable<VerifyData>
}

fun createVerifyRequest():VerifyService{
    return RetrofitCreation.createRetrofit().create(VerifyService::class.java)
}