package app.iti.client.iti_gp_client.entities

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import okhttp3.MultipartBody
import java.io.Serializable


/**
 * Created by Hesham on 5/31/2018.
 * this file is made for data classes that hesham worked with..
 */
//login data classes
data class LoginUserData(val email:String,var password:String)
data class LoginResponse(val auth_token:String)
data class ForgotPasswordResponse(val message: String, val reset_token: String)
data class Order(val title: String, val description: String, val paths: ArrayList<String>) : Serializable
// for payment list view
data class Payment(val paymentMethod:String, val status: String, val Rid: Int)
// all order request data entity
data class FinalOrderRequest(val images: ArrayList<MultipartBody.Part>, val orderData: Order)
data class ProfileOption(val optionTitle: String, val arrowImage: Int)
data class OrderToBeSent(val title: String, val time: Double, val provider_id: Int, val weight: Double, val payment_method: String,
                         val images: ArrayList<MultipartBody.Part>, val src_latitude: Double, val src_longitude: Double,
                         val dest_latitude: Double , val dest_longitude: Double)