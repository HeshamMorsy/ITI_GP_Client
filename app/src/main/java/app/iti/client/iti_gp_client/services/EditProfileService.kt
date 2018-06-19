package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.EditProfileResponse
import app.iti.client.iti_gp_client.entities.Image
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

/*
@Multipart
@POST("user/updateprofile")
Observable<ResponseBody> updateProfile(@Part("user_id") RequestBody id,
                                       @Part("full_name") RequestBody fullName,
                                       @Part MultipartBody.Part image,
                                       @Part("other") RequestBody other);

//pass it like this
File file = new File("/storage/emulated/0/Download/Corrections 6.jpg");
RequestBody requestFile =
        RequestBody.create(MediaType.parse("multipart/form-data"), file);

// MultipartBody.Part is used to send also the actual file name
MultipartBody.Part body =
        MultipartBody.Part.createFormData("image", file.getName(), requestFile);

// add another part within the multipart request
RequestBody fullName =
        RequestBody.create(MediaType.parse("multipart/form-data"), "Your Name");

service.updateProfile(id, fullName, body, other);
 */
