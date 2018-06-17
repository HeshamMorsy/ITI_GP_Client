package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.ChangePasswordResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.*

interface ChangePasswordService {
    @PATCH("authentication/changepassword")
    fun postChangePassword(@QueryMap options: Map<String, String> , @Header("Authorization") auth: String): Observable<ChangePasswordResponse>
}

fun createChangePasswordRequest():ChangePasswordService{
    return RetrofitCreation.createRetrofit().create(ChangePasswordService::class.java)
}
