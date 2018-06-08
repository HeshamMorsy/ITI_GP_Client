package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ForgotPasswordResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by Hesham on 6/3/2018.
 * interface to represent the service that get pin code from api
 */
interface ForgotPasswordService {
    @POST("forgot_password")
    fun getPassword(@QueryMap options:Map<String, String> ) : Observable<ForgotPasswordResponse>
}

fun createForgotPasswordRequest():ForgotPasswordService{
    return RetrofitCreation.createRetrofit().create(ForgotPasswordService::class.java)
}
