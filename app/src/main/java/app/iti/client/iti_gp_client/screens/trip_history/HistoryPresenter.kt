package app.iti.client.iti_gp_client.screens.trip_history

import android.util.Log
import app.iti.client.iti_gp_client.contracts.OrderHistory
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.OrderHistoryResponse

/**
 * Created by Hazem on 6/16/2018.
 */
class HistoryPresenter(var view:OrderHistory.View):OrderHistory.Presenter {
    private lateinit var model:OrderHistory.Model
    private lateinit var activeOrders:ArrayList<OrderDetails>
    private lateinit var pastOrders:ArrayList<OrderDetails>
    private var pageNum = 0
    private var totalPages = 0
    init {
        model = HistoryModel(this)
    }

    override fun getOrders() {
        if (pageNum == 0 || pageNum<totalPages){
            val auth = view.getAuth()
            model.getOrders(auth!!,++pageNum)
        }else{
            Log.i("orders","there is no more orders in history orders")
        }
    }

    override fun handleOrders(response: OrderHistoryResponse) {
        Log.i("response","history response" + response.toString())
        activeOrders = ArrayList()
        pastOrders = ArrayList()
        totalPages = response.total_pages
        if(response.message == "success"){
            for ( order in response.data.active){
                activeOrders.add(order)
            }
            for ( order in response.data.history){
                pastOrders.add(order)
            }
        }
        Log.i("orders","orders in hist presenter: "+ activeOrders.toString() + "past: "+pastOrders.toString())
        view.updateData(activeOrders,pastOrders)


    }
}