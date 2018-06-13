package app.iti.client.iti_gp_client.entities

/**
 * Created by Hazem on 5/30/2018.
 */

//signup data classes
data class SignUpUser(var id:Int,var name:String?,var email:String,var verified:Boolean,var user_pin:String,var phone:String)
data class SignUpData(var message:String,var auth_token:String,var user:SignUpUser)

//select carriers spinner class
data class SelectCarriers(var img:String,var name:String)

//request order class
data class RequestOrder(var id:String,var date:String,var time:String,var address:String,var status:String)

//verification Data
data class VerifyData(var message:String)

//providers data
data class ProvidersRequest(var message:String,var providers:Array<Provider>)
data class Provider(var id:Int,var name: String,var image: Image)
data class Image(var url:String)
