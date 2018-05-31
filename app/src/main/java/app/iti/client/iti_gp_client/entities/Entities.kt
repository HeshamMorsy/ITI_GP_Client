package app.iti.client.iti_gp_client.entities

/**
 * Created by Hesham on 5/31/2018.
 * this file is made for data classes that hesham worked with..
 */
//login data classes
data class LoginUserData(val email:String,var password:String)
data class LoginResponse(val auth_token:String)
