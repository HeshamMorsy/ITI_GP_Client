package app.iti.client.iti_gp_client.screens.payment

import android.graphics.Bitmap
import app.iti.client.iti_gp_client.contracts.PaymentContract.*
import android.graphics.BitmapFactory


/**
 * Created by Hesham on 6/7/2018.
 * Responsible for handling actions in OrderActivity and updating UI if required
 */
class PaymentPresenter : Presenter {
    // references to model and view of payment
    private var mModel: Model? = null
    private var mView: View? = null

    override fun initPresenter(view: View) {
        mView = view
        mModel = PaymentModel()
    }

    override fun convertPathsToBitmap(pathList: ArrayList<String>): ArrayList<Bitmap> {
        val bitArrayList = ArrayList<Bitmap>()
        var bitmap:Bitmap? = null
        for (path in pathList){
             bitmap = BitmapFactory.decodeFile(path)
            bitArrayList.add(bitmap)
        }
        mView?.updateImage(bitmap!!)
        return bitArrayList
    }
}