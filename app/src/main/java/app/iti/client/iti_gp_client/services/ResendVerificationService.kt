package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ResendDetails
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.POST

/**
 * Created by Hazem on 6/18/2018.
 */
interface ResendVerificationService {
    @POST("authentication/resendverification")
    fun resendVerificationCode(@Header("Authorization") auth:String): Observable<ResendDetails>
}
fun createResendRequest():ResendVerificationService{
    return RetrofitCreation.createRetrofit().create(ResendVerificationService::class.java)
}
