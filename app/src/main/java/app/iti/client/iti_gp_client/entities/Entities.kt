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

//data class ForgotPasswordResponse(val message: String, val reset_token: String) // old api
data class ForgotPasswordResponse(val message: String)
data class Order(val title: String, val description: String, val weight: String , val paths: ArrayList<String>) : Serializable
// for payment list view
data class Payment(val paymentMethod:String, val status: String, val Rid: Int)
// all order request data entity
data class FinalOrderRequest(val images: ArrayList<MultipartBody.Part>, val orderData: Order)
data class ProfileOption(val optionTitle: String, val arrowImage: Int, val language: String)
data class OrderToBeSent(val title: String, val time: String, val provider_id: Int, val weight: Double, val payment_method: String,
                         val images: HashMap<String,ArrayList<MultipartBody.Part>>, val src_latitude: Double, val src_longitude: Double,
                         val dest_latitude: Double, val dest_longitude: Double)
// submitting order data response model
data class Driver(val id: Int, val latitude: Double, val longitude: Double , val name: String, val phone: String
                  , val email: String , val  created_at: String, val updated_at: String, val password_digest:String,
                  val status: String, val vehicle_kind: String)
// order response data class
data class OrderResponse(val message: String , val data: OrderResponseData)
data class OrderResponseData(val message: String, val driver: OrderDriver , val cost: Double)
data class OrderDriver(val name: String, val phone: String, val vehicle_kind: String)


// response of change password api
data class ChangePasswordResponse(val message: String)
// response of about api
data class AboutResponse(val message: String, val about_us: String)
// response of edit profile
data class EditProfileResponse(val message: String,val user: User)
data class User(val id: Int, val email: String,
                val password_digest: String, val phone: String ,
                val avatar: Avatar, val name: String, val created_at: String,
                val updated_at: String, val user_pin: String, val verified: Boolean)
data class Avatar(val url: String)
// response of setting new password after forgotten password
data class NewPasswordResponse(val message: String)
// response of login api
data class LoginResponse(val message: String, val auth_token:String, val user: LoginedUser)
// user login entity
data class LoginedUser(val name: String,val email: String, val phone: String, val avatar: Avatar)




/*
{
    "message": "success",
    "user": {
        "id": 8,
        "email": "hesham@morsyy.com",
        "password_digest": "$2a$10$jhFUWPR1vBXNnw6oXlhEGu3yidtp3zJOODzLJMj3opIUDmxjn5ZH6",
        "phone": "01113455607",
        "avatar": {
            "url": "https://driveo.herokuapp.com/uploads/user/avatar/8/"
        },
        "name": null,
        "created_at": "2018-06-17T04:53:15.778Z",
        "updated_at": "2018-06-17T04:55:13.441Z",
        "user_pin": 525,
        "verified": true
    }
}
*/


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