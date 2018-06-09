package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.FinalOrderRequest
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface OrderService {
    // @FormUrlEncoded
    // to send arrayList of multipart we use @Query("someKey[]")
    @Multipart
    @POST("")
    fun uploadImageAndOrder(@QueryMap options: Map<String,String>, @Part img: ArrayList<MultipartBody.Part>) : Observable<FinalOrderRequest>
}

fun createOrderRequest(): OrderService {
    return RetrofitCreation.createRetrofit().create(OrderService::class.java)
}
