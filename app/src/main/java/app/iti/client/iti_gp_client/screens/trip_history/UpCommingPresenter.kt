package app.iti.client.iti_gp_client.screens.trip_history

import android.content.Context
import android.util.Log
import app.iti.client.iti_gp_client.contracts.OrderHistory
import app.iti.client.iti_gp_client.contracts.UpcommingOrders
import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.OrderHistoryResponse
import app.iti.client.iti_gp_client.entities.OrderupcomingResponse
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get

/**
 * Created by Hazem on 6/16/2018.
 */
class UpCommingPresenter(var view:UpcommingOrders.View):UpcommingOrders.Presenter {
    private lateinit var model:UpcommingOrders.Model
    private lateinit var futureOrders:ArrayList<OrderDetails>
    private var pageNum = 0
    private var totalPages = 0
    init {
        model = UpCommingModel(this)
    }
    override fun getOrders() {
        Log.i("orders","pageNum: "+ pageNum + "  totallPages: " + totalPages)
        if (pageNum == 0 || pageNum<totalPages){
            val auth = view.getAuth()
            model.getOrders(auth!!,++pageNum)
        }else{
            Log.i("orders","there is no more orders in upcomming orders")
        }
    }

    override fun handleOrders(response: OrderupcomingResponse) {
        Log.i("response","upcomming orders " + response)
        totalPages = response.total_pages
        futureOrders = ArrayList()
        if (response.message == "success"){
            for (order in response.data.upcoming){
                futureOrders.add(order)
            }
        }
        Log.i("orders","orders in upcomming presenter: "+ futureOrders.toString())
        view.updateData(futureOrders)
    }

    override fun cancelTrip(auth: String?, id: Int) {
        model.cancellTrip(auth, id)
    }

    override fun handlecancelOrders(response: CancelOrderResponse) {
        view.updateView()
    }
}