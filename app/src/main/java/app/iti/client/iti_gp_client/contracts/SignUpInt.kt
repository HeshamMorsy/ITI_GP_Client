package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.SignUpData

/**
 * Created by Hazem on 5/30/2018.
 */
interface SignUpInt {
    interface Model{
        fun signUp(phone:String,email:String,pass:String)

    }
    interface View{

    }
    interface Presenter{
        fun signUp(phone:String,email:String,pass:String,repass:String)
        fun receiveResponse(response: SignUpData)
    }
}