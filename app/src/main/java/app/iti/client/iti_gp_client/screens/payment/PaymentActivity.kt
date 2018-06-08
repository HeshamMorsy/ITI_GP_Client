package app.iti.client.iti_gp_client.screens.payment

import android.graphics.Bitmap
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import app.iti.client.iti_gp_client.entities.Order
import kotlinx.android.synthetic.main.activity_payment.*

/**
 * Created by Hesham on 6/7/2018.
 * Display Payment Screen
 */
class PaymentActivity : AppCompatActivity(), View {
    // reference to presenter
    var mPresenter:Presenter? = null
    // arrayList of order image paths

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_payment)
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
        var bitmapArrayList = mPresenter?.convertPathsToBitmap(order.paths)!!
        Log.i("not order", "be5")
    }

    // for testing
    override fun updateImage(photo: Bitmap) {
//        testImg.setImageBitmap(photo)
    }

    fun submitBtnAction(view: android.view.View){

    }

}
