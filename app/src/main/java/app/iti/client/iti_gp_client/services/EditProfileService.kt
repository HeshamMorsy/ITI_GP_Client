package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.POST
import retrofit2.http.QueryMap

interface EditProfileService {
    @POST("")
    fun postEditProfile(@QueryMap options:Map<String, String> ) : Observable<EditProfileResponse>
}

fun createEditProfileRequest():EditProfileService{
    return RetrofitCreation.createRetrofit().create(EditProfileService::class.java)
}
