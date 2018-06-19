package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.OrderHistoryResponse
import app.iti.client.iti_gp_client.entities.OrderupcomingResponse

/**
 * Created by Hazem on 6/16/2018.
 */
interface UpcommingOrders {
    interface Model{
        fun getOrders(auth: String, i: Int) {}
        fun cancellTrip(auth: String?, id: Int)

    }
    interface View{
        fun getAuth(): String?
        fun updateData(future:ArrayList<OrderDetails>)
        fun updateView()
    }
    interface Presenter{
        fun getOrders()
        fun handleOrders(response: OrderupcomingResponse)
        fun cancelTrip(auth: String?, id: Int)
        fun handlecancelOrders(response: CancelOrderResponse)
    }
}