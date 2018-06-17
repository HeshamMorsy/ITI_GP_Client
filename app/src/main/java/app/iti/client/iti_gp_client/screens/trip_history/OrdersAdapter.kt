package app.iti.client.iti_gp_client.screens.trip_history

import android.icu.lang.UCharacter.GraphemeClusterBreak.V
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.View
import android.view.ViewGroup
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.entities.RequestOrder
import app.iti.client.iti_gp_client.utilities.confirtDateFormat
import app.iti.client.iti_gp_client.utilities.inflate
import kotlinx.android.synthetic.main.order_layout.view.*

/**
 * Created by Hazem on 6/11/2018.
 */
class OrdersAdapter(private val orders: ArrayList<OrderDetails>): RecyclerView.Adapter<OrdersAdapter.OrderHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): OrderHolder {
        val inflatedView = parent.inflate(R.layout.order_layout, false)
        return OrderHolder(inflatedView)
    }

    override fun onBindViewHolder(holder: OrderHolder, position: Int) {
        val itemPhoto = orders[position]
        holder.bindOrder(itemPhoto)
    }

    override fun getItemCount(): Int {
        return orders.size
    }


    class OrderHolder(v: View):RecyclerView.ViewHolder(v), View.OnClickListener  {


        private var view: View = v
        private var order: OrderDetails? = null

        init {
            v.setOnClickListener(this)
        }


        override fun onClick(v: View?) {
            Log.d("RecyclerView", "CLICK!")
        }
        fun bindOrder(order: OrderDetails) {
            this.order = order
            view.orderId.text = "id#"+order.id
            view.orderDate.text = confirtDateFormat(order.created_at)
//            view.orderTime.text = order.time
            view.orderAddress.text = "  "+order.pickup_location
            view.setPadding(0,0,0,0)
//            view.orderPrice.text = order.
            view.orderStatus.text = "  "+order.status
            view.orderPrice.text = (order.cost?:34.00).toString() + " LE"
        }

    }
}