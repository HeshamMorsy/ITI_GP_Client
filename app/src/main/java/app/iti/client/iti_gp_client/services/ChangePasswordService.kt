package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ChangePasswordResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface ChangePasswordService {
    @POST("")
    fun postChangePassword(@QueryMap options: Map<String, String>): Observable<ChangePasswordResponse>
}

fun createChangePasswordRequest():ChangePasswordService{
    return RetrofitCreation.createRetrofit().create(ChangePasswordService::class.java)
}
