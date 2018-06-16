package app.iti.client.iti_gp_client.entities

import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.Serializable
import java.sql.Driver
import java.util.*
import kotlin.collections.ArrayList
import kotlin.collections.HashMap


/**
 * Created by Hesham on 5/31/2018.
 * this file is made for data classes that hesham worked with..
 */
//login data classes
data class LoginUserData(val email:String,var password:String)
data class LoginResponse(val message: String, val auth_token:String)
//data class ForgotPasswordResponse(val message: String, val reset_token: String) // old api
data class ForgotPasswordResponse(val message: String)
data class Order(val title: String, val description: String, val paths: ArrayList<String>) : Serializable
// for payment list view
data class Payment(val paymentMethod:String, val status: String, val Rid: Int)
// all order request data entity
data class FinalOrderRequest(val images: ArrayList<MultipartBody.Part>, val orderData: Order)
data class ProfileOption(val optionTitle: String, val arrowImage: Int, val language: String)
data class OrderToBeSent(val title: String, val time: String, val provider_id: Int, val weight: Int, val payment_method: String,
                         val images: HashMap<String,ArrayList<MultipartBody.Part>>, val src_latitude: Double, val src_longitude: Double,
                         val dest_latitude: Double, val dest_longitude: Double)
// submitting order data response model
data class Driver(val id: Int, val latitude: Double, val longitude: Double , val name: String, val phone: String
                  , val email: String , val  created_at: String, val updated_at: String, val password_digest:String,
                  val status: String, val vehicle_kind: String)
data class OrderResponse(val message: String
//                         , val driver: Driver
                        )


// responses of edit profile api
data class EditProfileResponse(val result: String)
// response of change password api
data class ChangePasswordResponse(val result: String)
// response of about api
data class AboutResponse(val result: String)


/*
{
    "message": "success",
    "driver": {
    "id": 1,
    "latitude": 48.1515,
    "longitude": 48.4848,
    "name": "mina",
    "phone": "01236856496",
    "email": "hoho@yahoo.com",
    "created_at": "2018-06-10T11:35:58.000Z",
    "updated_at": "2018-06-10T11:35:58.000Z",
    "password_digest": "123456",
    "status": "online",
    "vehicle_kind": "pickup truck"
}
}*/