package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import okhttp3.MultipartBody
import retrofit2.http.*

interface OrderService {

    @Multipart
    @POST("orders")
    fun uploadData(@Header("Authorization") auth: String, @Query("title") title: String,
                   @Query("description") description: String ,@Query("time") time: String,
                   @Query("provider_id") provider_id: Int, @Part images: MultipartBody.Part,
                   @Query("weight") weight: Double, @Query("payment_method") payment_method: String,
                   @Query("src_latitude") src_latitude: Double, @Query("src_longitude") src_longitude: Double,
                   @Query("dest_latitude") dest_latitude: Double, @Query("dest_longitude") dest_longitude: Double,
                   @Query("pickup_location")pickUpAddress: String, @Query("dropoff_location") dropOffAddress: String)
            : Observable<OrderResponse>
}

fun createOrderRequest(): OrderService {
    return RetrofitCreation.createRetrofit().create(OrderService::class.java)
}
