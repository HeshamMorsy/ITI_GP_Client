package app.iti.client.iti_gp_client.screens.payment

import android.app.AlertDialog
import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.AdapterView
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.Payment
import kotlinx.android.synthetic.main.activity_payment.*

/**
 * Created by Hesham on 6/7/2018.
 * Display Payment Screen
 */
class PaymentActivity : AppCompatActivity(), View {
    // reference to presenter
    var mPresenter:Presenter? = null
    // arrayList of order image paths
    var paymentMethodArray: ArrayList<Payment>? = null
    // progress dialog
    var dialog: AlertDialog? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
        // add the three payment methods
        var cashPayment = Payment(resources.getString(R.string.cash), "", R.mipmap.ic_payment_cash)
        var masterPayment = Payment(resources.getString(R.string.masterCard), resources.getString(R.string.soon), R.mipmap.ic_payment_mastercard)
        var visaPayment = Payment(resources.getString(R.string.visa),  resources.getString(R.string.soon), R.mipmap.ic_payment_visa)

        paymentMethodArray = ArrayList()
        paymentMethodArray?.add(cashPayment)
        paymentMethodArray?.add(masterPayment)
        paymentMethodArray?.add(visaPayment)

        //initialize list view and adapter
        var payAdapter = PayListAdapter(this, paymentMethodArray!!)
        paymentList.adapter = payAdapter
        // handle on  item click listener to the list view
        paymentList.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
        Toast.makeText(this, "${paymentMethodArray!![position]} payment will be enabled soon..", Toast.LENGTH_SHORT).show()

        }
        }

        // initialize presenter
        mPresenter = PaymentPresenter()
        mPresenter?.initPresenter(this)

        // receive order from intent
        val intent = intent
        var order = intent.getSerializableExtra("Order") as Order
        // print data to check if it sent successfully
        Log.i("order title",order.title)
        Log.i("order description",order.description)
        for (path in order.paths) {
            Log.i("order image path", path)
        }
        // convert array of paths to array of bitmaps and receive them in arrayList<Bitmap>
//        var bitmapArrayList = mPresenter?.convertPathsToBitmap(order.paths)!!
        Log.i("not order", "test")
    }

    // for testing
    override fun updateImage(photo: Bitmap) {
//        testImg.setImageBitmap(photo)
    }

    fun submitBtnAction(view: android.view.View) {

    }

    override fun startLoading(mes:String){
        Log.i("response", "start loading function")
        val builder = AlertDialog.Builder(this)
        val dialougeView = layoutInflater.inflate(R.layout.progress_dialouge,null)
        val message = dialougeView.findViewById<TextView>(R.id.loadingmessage)
        message.text = mes
        builder.setView(dialougeView)
        builder.setCancelable(false)
        dialog = builder.create()
        dialog?.show()
    }

    override fun endLoading(){
        Log.i("response", "end loading function")
        dialog?.dismiss()
    }

}
