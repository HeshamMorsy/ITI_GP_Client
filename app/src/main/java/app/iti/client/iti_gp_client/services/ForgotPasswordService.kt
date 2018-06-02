package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ForgotPasswordResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.POST

/**
 * Created by Hesham on 6/3/2018.
 * interface to represent the service that get pin code from api
 */
interface ForgotPasswordService {
    @POST("")
    fun getPinCode() : Observable<ForgotPasswordResponse>
}

fun createForgotPasswordRequest():ForgotPasswordService{
    return RetrofitCreation.createRetrofit().create(ForgotPasswordService::class.java)
}
