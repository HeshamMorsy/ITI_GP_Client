package app.iti.client.iti_gp_client.screens.payment

import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.PaymentContract.Presenter
import app.iti.client.iti_gp_client.contracts.PaymentContract.View
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.Payment
import app.iti.client.iti_gp_client.screens.home.HomeActivity
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
    // order data sent from order description activity
    var order:Order? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)

        // add the three payment methods by init method to use this array in list adapter
        initAdapterArray()

        //initialize list view and adapter
        val payAdapter = PayListAdapter(this, paymentMethodArray!!)
        paymentList.adapter = payAdapter

        // handle on  item click listener to the list view
        paymentList.setOnItemClickListener { parent, view, position, id ->
            if(position != 0){
        Toast.makeText(this, "${paymentMethodArray!![position].paymentMethod} "+resources.getString(R.string.paymentSoon)
                        , Toast.LENGTH_SHORT).show()
            }
        }

        // initialize presenter
        mPresenter = PaymentPresenter()
        mPresenter?.initPresenter(this)

        // receive order from intent
        val intent = intent
         order = intent.getSerializableExtra("Order") as Order
        // print data to check if it sent successfully
        Log.i("order title",order!!.title)
        Log.i("order description",order!!.description)
        for (path in order!!.paths) {
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
        mPresenter?.prepareOrderAndSend(order!!)
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


    private fun initAdapterArray(){
        val cashPayment = Payment(resources.getString(R.string.cash), "", R.mipmap.ic_payment_cash)
        val masterPayment = Payment(resources.getString(R.string.masterCard), resources.getString(R.string.soon), R.mipmap.ic_payment_mastercard)
        val visaPayment = Payment(resources.getString(R.string.visa),  resources.getString(R.string.soon), R.mipmap.ic_payment_visa)

        paymentMethodArray = ArrayList()
        paymentMethodArray?.add(cashPayment)
        paymentMethodArray?.add(masterPayment)
        paymentMethodArray?.add(visaPayment)
    }

    override fun goToHomeScreen() {
        val myIntent = Intent(this,HomeActivity::class.java)
        startActivity(myIntent)
        finish()
    }





}
