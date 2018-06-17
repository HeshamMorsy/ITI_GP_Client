package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.NewPasswordResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.*

interface NewPasswordService{

@PATCH("authentication/resetpassword")
fun sendNewPassword(@QueryMap options: Map<String, String>, @Query("hash") auth: String): Observable<NewPasswordResponse>
}

fun createNewPasswordRequest():NewPasswordService{
    return RetrofitCreation.createRetrofit().create(NewPasswordService::class.java)
}