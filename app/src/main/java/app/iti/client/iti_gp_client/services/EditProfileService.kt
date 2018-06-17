package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.entities.Image
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface EditProfileService {
    @Multipart
    @PUT("authentication/update")
    fun postEditProfile(@QueryMap options:Map<String, String> ,@Part image: MultipartBody.Part  ,@Header("Authorization") auth: String ) : Observable<EditProfileResponse>

    @PUT("authentication/update")
    fun postWithoutImage(@QueryMap options:Map<String, String> ,@Header("Authorization") auth: String ) : Observable<EditProfileResponse>
}

fun createEditProfileRequest():EditProfileService{
    return RetrofitCreation.createRetrofit().create(EditProfileService::class.java)
}
