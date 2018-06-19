package app.iti.client.iti_gp_client.screens.trip_history


import android.content.DialogInterface
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.app.AlertDialog
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.UpcommingOrders
import app.iti.client.iti_gp_client.entities.OrderDetails
import app.iti.client.iti_gp_client.screens.home.RecyclerItemClickListener
import app.iti.client.iti_gp_client.utilities.Constants.Companion.TOKEN_SHARED_PREFERENCE
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import kotlinx.android.synthetic.main.fragment_upcoming.*


// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
private const val ARG_PARAM1 = "param1"
private const val ARG_PARAM2 = "param2"

/**
 * A simple [Fragment] subclass.
 *
 */
class UpcomingFragment : Fragment(),UpcommingOrders.View {
    private lateinit var futureOrderLayoutManager: LinearLayoutManager
    private lateinit var futureOrderAdapter: OrdersAdapter
    private lateinit var presenter:UpcommingOrders.Presenter
    private lateinit var futureOrders:ArrayList<OrderDetails>
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_upcoming, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        presenter = UpCommingPresenter(this)
        //set the layout manager for the resycler view
        futureOrderLayoutManager = LinearLayoutManager(context)
        futureOrdersrecyclerView.layoutManager = futureOrderLayoutManager

        futureOrders = ArrayList()



        //get the data
        presenter.getOrders()

        futureOrdersrecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener(){
            override fun onScrolled(recyclerView: RecyclerView?, dx: Int, dy: Int) {
                var isBottomReached:Boolean = !recyclerView!!.canScrollVertically(1)
                Log.i("orders","isBottomReached: " + isBottomReached)
                if (isBottomReached){
                    presenter.getOrders()
                }
            }
        })
        // onitemclicklistner for the recyler view
        var listner = createItemClickListner()
        // register the listner for recyclerview
        futureOrdersrecyclerView.addOnItemTouchListener(listner)
    }



    override fun getAuth(): String? {
        Log.i("orders","get auth from upcomming fragment")
        val defaultPref = PreferenceHelper.defaultPrefs(context!!)
        return defaultPref.get(TOKEN_SHARED_PREFERENCE,"0")
    }
    override fun updateData(future:ArrayList<OrderDetails>){
        Log.i("orders","in upcomming fragment")
        for (order in future){
            futureOrders.add(order)
        }
        Log.i("orders","orders in upcomming fragment: "+ futureOrders.toString())
        futureOrderAdapter = OrdersAdapter(futureOrders)
        futureOrdersrecyclerView.adapter = futureOrderAdapter
    }

    fun alertWithOneButton(title:String, message:String, btnTitle:String,canTitle:String ,pos:Int){
        val alert: AlertDialog.Builder = AlertDialog.Builder(context!!)
        alert.setMessage(message)
        alert.setTitle(title)
        alert.setPositiveButton(btnTitle, DialogInterface.OnClickListener { dialog, which ->
            Log.i("alert","ok clicked")
            Log.i("alert","orderId"+ futureOrders.get(pos).id)
            val defaultPref = PreferenceHelper.defaultPrefs(context!!)
            val auth = defaultPref.get(TOKEN_SHARED_PREFERENCE,"0")
            presenter.cancelTrip(auth,futureOrders.get(pos).id)
            futureOrders.remove(futureOrders[pos])

        })
        alert.setNegativeButton(canTitle, DialogInterface.OnClickListener { dialog, which ->
            Log.i("alert","cancelled clicked")
        })
        alert.show()
    }

    override fun updateView() {
        futureOrderAdapter.notifyDataSetChanged()
    }

    private fun createItemClickListner(): RecyclerView.OnItemTouchListener? {
        return RecyclerItemClickListener(context, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                Log.i("click","clicked: " + position)
                alertWithOneButton("Cancel Order", "Are you sure you want to cancel order", "OK","Cancel",position)
            }
        })
    }






}
