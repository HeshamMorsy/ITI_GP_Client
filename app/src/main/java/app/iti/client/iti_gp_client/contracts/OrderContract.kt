package app.iti.client.iti_gp_client.contracts

import android.graphics.Bitmap
import java.io.File

/**
 * Created by Hesham on 6/4/2018.
 * define the contract between the view Order, the model LoginModel and the presenter LoginPresenter
 */
interface OrderContract {
    interface Model {
//
    }

    interface View {
        fun updateImageView(image: Bitmap,path: String, counter: Int)
    }

    interface Presenter {
        var imageCounter:Int
        fun initPresenter(view:View)
        fun getImageAction()
        fun convertFilesToBitmap(imageFiles: List<File>)
        fun decrementCounter()
    }
}