package app.iti.client.iti_gp_client.entities

/**
 * Created by Hazem on 5/30/2018.
 */

//signup data classes
data class SignUpUser(var id:Int,var name:String,var email:String,var verified:Boolean)
data class SignUpData(var message:String,var auth_token:String,var user:SignUpUser)

//select carriers spinner class
data class SelectCarriers(var img:String,var name:String)

