package app.iti.client.iti_gp_client.screens.trip_history


import android.content.Intent
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.OrderHistory
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.screens.home.RecyclerItemClickListener
import app.iti.client.iti_gp_client.screens.tracking.TrackingActivity
import app.iti.client.iti_gp_client.utilities.Constants.Companion.TOKEN_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import kotlinx.android.synthetic.main.fragment_history.*
import java.io.Serializable


/**
 * Displays the history fragment in trip activity
 *
 */
class HistoryFragment : Fragment(),OrderHistory.View {
    private lateinit var pastLinearLayoutManager: LinearLayoutManager
    private lateinit var activeLinearLayoutManager: LinearLayoutManager
    private lateinit var pastAdapter: OrdersAdapter
    private lateinit var activeAdapter: OrdersAdapter
    private lateinit var presenter:OrderHistory.Presenter
    private lateinit var activeOrders:ArrayList<OrderDetails>
    private lateinit var pastOrders:ArrayList<OrderDetails>

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

        //initiate the presenter
        presenter = HistoryPresenter(this)


        activeOrders = ArrayList()
        pastOrders = ArrayList()

        //set the layout manager for the resyclerviews
        pastLinearLayoutManager = LinearLayoutManager(context)
        activeLinearLayoutManager = LinearLayoutManager(context)
        pastrecyclerView.layoutManager = pastLinearLayoutManager
        histActiverecyclerView.layoutManager = activeLinearLayoutManager

        //get the data from the server
        presenter.getOrders()

        pastrecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                var isBottomReached:Boolean = !recyclerView!!.canScrollVertically(1)
                if (isBottomReached){
                    presenter.getOrders()
                }
            }
        })

        var listner = createItemClickListner()
        histActiverecyclerView.addOnItemTouchListener(listner)
    }

    override fun getAuth(): String? {
        var defaultPref = PreferenceHelper.defaultPrefs(context!!)
        return defaultPref.get(TOKEN_SHARED_PREFERENCE,"0")
    }

    override fun updateData(active:ArrayList<OrderDetails>,past:ArrayList<OrderDetails>){
        activeOrders = active
        for (order in past){
            pastOrders.add(order)
        }
        Log.i("orders","orders in hist fragment: "+ activeOrders.toString() + "past: "+pastOrders.toString())
        pastAdapter = OrdersAdapter(pastOrders)
        activeAdapter = OrdersAdapter(activeOrders)
        pastrecyclerView.adapter = pastAdapter
        histActiverecyclerView.adapter = activeAdapter
    }

    private fun createItemClickListner(): RecyclerView.OnItemTouchListener? {
        return RecyclerItemClickListener(context, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.i("click","clicked: " + position)
                val trackingIntent = Intent(context,TrackingActivity::class.java)
                trackingIntent.putExtra("order",activeOrders.get(position) as Serializable)
                startActivity(trackingIntent)
            }
        })
    }
}
