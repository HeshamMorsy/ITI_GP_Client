package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.Retrofit
import retrofit2.http.POST
import retrofit2.http.QueryMap


/**
 * Created by Hazem on 5/30/2018.
 */
interface SignUpService {
    @POST("signup")
    fun getTokin(@QueryMap options:Map<String, String> ) : Observable<SignUpData>
}

fun createSignUpRequest():SignUpService{
    return RetrofitCreation.createRetrofit().create(SignUpService::class.java)
}


