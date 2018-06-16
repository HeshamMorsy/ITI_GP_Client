package app.iti.client.iti_gp_client.screens.trip_history


import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.RequestOrder
import kotlinx.android.synthetic.main.fragment_history.*


/**
 * Displays the history fragment in trip activity
 *
 */
class HistoryFragment : Fragment() {
    private lateinit var pastLinearLayoutManager: LinearLayoutManager
    private lateinit var activeLinearLayoutManager: LinearLayoutManager
    private lateinit var pastAdapter: OrdersAdapter
    private lateinit var activeAdapter: OrdersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_history, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        pastLinearLayoutManager = LinearLayoutManager(context)
        activeLinearLayoutManager = LinearLayoutManager(context)
        pastrecyclerView.layoutManager = pastLinearLayoutManager
        histActiverecyclerView.layoutManager = activeLinearLayoutManager
        var activeOrders = arrayListOf<RequestOrder>(RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Pending"))

        var pastOrders = arrayListOf<RequestOrder>(RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Delivered"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Cancelled"),
                RequestOrder("2454","19 Apr 2018","12:20","945 apagiali prairi","Delivered"))
        pastAdapter = OrdersAdapter(pastOrders)
        activeAdapter = OrdersAdapter(activeOrders)
        pastrecyclerView.adapter = pastAdapter
        histActiverecyclerView.adapter = activeAdapter
    }
}
