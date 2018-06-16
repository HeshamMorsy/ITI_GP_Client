package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import okhttp3.MultipartBody
import okhttp3.RequestBody

import retrofit2.http.*
import java.time.LocalDateTime
import java.util.*
import kotlin.collections.HashMap

interface OrderService {
    // @FormUrlEncoded
    // to send arrayList of multipart we use @Query("someKey[]")
    /*@Multipart
    @POST("orders")
    fun uploadImageAndOrder(@QueryMap optionStrings: Map<String,String>, @QueryMap optionsDoubles: Map<String,Double>
                            , @Query("payment_method") payment_method:Int
                            , @Part img: ArrayList<MultipartBody.Part>) : Observable<OrderResponse>*/
    @Multipart
    @POST("orders")
    fun uploadData(@Header("Authorization") auth: String, @Query("title") title: String, @Query("time") time: String,
                   @Query("provider_id") provider_id: Int, @PartMap images: HashMap<String,ArrayList<MultipartBody.Part>>,
                   @Query("weight") weight: Int, @Query("payment_method") payment_method: String,
                   @Query("src_latitude") src_latitude: Double, @Query("src_longitude") src_longitude: Double,
                   @Query("dest_latitude") dest_latitude: Double, @Query("dest_longitude") dest_longitude: Double)
            : Observable<OrderResponse>


    /*
{
  "title": "some order title",
  "time": "in datetime format",
  "provider_id": 1,
  "images": [
    "image1",
    "image2",
    "image3"
  ],
  "weight": 90,
  "payment_method": "cash",
  "src_latitude": 30.123,
  "src_longitude": 30.3,
  "dest_latitude": 31.123,
  "dest_longitude": 31.123
}
    */
}

fun createOrderRequest(): OrderService {
    return RetrofitCreation.createRetrofit().create(OrderService::class.java)
}
