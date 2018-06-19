package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.http.*

interface EditProfileService {
    @Multipart
    @PUT("authentication/update")
    fun postEditProfile(@Part("email") email: RequestBody, @Part("phone") phone: RequestBody, @Part("name") name: RequestBody, @Part avatar: MultipartBody.Part, @Header("Authorization") auth: String ) : Observable<EditProfileResponse>

    @PUT("authentication/update")
    fun postWithoutImage(@QueryMap options:Map<String, String> ,@Header("Authorization") auth: String ) : Observable<EditProfileResponse>
}

fun createEditProfileRequest():EditProfileService{
    return RetrofitCreation.createRetrofit().create(EditProfileService::class.java)
}
