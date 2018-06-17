package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.OrderHistoryResponse

/**
 * Created by Hazem on 6/16/2018.
 */
interface OrderHistory {
    interface Model{
        fun getOrders(auth:String,pageNum:Int)

    }
    interface View{
        fun getAuth(): String?
        fun updateData(active:ArrayList<OrderDetails>,past:ArrayList<OrderDetails>)
    }
    interface Presenter{
        fun getOrders()
        fun handleOrders(response: OrderHistoryResponse)

    }
}