package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.LoginResponse
import app.iti.client.iti_gp_client.entities.SignUpData
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.Header
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.QueryMap

/**
 * Created by Hesham on 5/31/2018.
 */
interface LoginService {

    @POST("authentication/signin")
    fun getTokin(@QueryMap options:Map<String, String> ) : Observable<LoginResponse>
}

fun createLoginRequest():LoginService{
    return RetrofitCreation.createRetrofit().create(LoginService::class.java)
}
