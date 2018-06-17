package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.OrderHistoryResponse
import app.iti.client.iti_gp_client.entities.OrderupcomingResponse

/**
 * Created by Hazem on 6/16/2018.
 */
interface UpcommingOrders {
    interface Model{
        fun getOrders(auth: String, i: Int) {}

    }
    interface View{
        fun getAuth(): String?
        fun updateData(future:ArrayList<OrderDetails>)
    }
    interface Presenter{
        fun getOrders()
        fun handleOrders(response: OrderupcomingResponse)

    }
}