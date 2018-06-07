package app.iti.client.iti_gp_client.contracts

import android.graphics.Bitmap

/**
 * Created by Hesham on 6/7/2018.
 * define the contract between the view PaymentActivity, the model PaymentModel and the presenter PaymentPresenter
 */
interface PaymentContract {
    interface Model {
//
    }

    interface View {
        fun updateImage(photo: Bitmap)
   }

    interface Presenter {
        fun convertPathsToBitmap(pathList: ArrayList<String>) : ArrayList<Bitmap>
        fun initPresenter(view: View)
    }
}