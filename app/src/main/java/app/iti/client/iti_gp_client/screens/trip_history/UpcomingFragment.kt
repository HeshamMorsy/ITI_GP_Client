package app.iti.client.iti_gp_client.screens.trip_history


import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.RequestOrder
import kotlinx.android.synthetic.main.fragment_upcoming.*

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UpcomingFragment : Fragment() {
    private lateinit var futureOrderLayoutManager: LinearLayoutManager
    private lateinit var futureOrderAdapter: OrdersAdapter

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        futureOrderLayoutManager = LinearLayoutManager(context)
        futureOrdersrecyclerView.layoutManager = futureOrderLayoutManager

        var futureOrders = arrayListOf<RequestOrder>(RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"),
                RequestOrder("1","19 Apr 2018","12:20","945 apagiali prairi","Pending"))
        futureOrderAdapter = OrdersAdapter(futureOrders)
        futureOrdersrecyclerView.adapter = futureOrderAdapter
    }
}
