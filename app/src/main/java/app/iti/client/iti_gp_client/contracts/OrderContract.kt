package app.iti.client.iti_gp_client.contracts

import android.content.Intent
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
        fun updateImageView(data: Intent?)
        fun getImageFromGallery()
        fun getImageFromCamera()
        fun getApplicationPackageName() : String
    }

    interface Presenter {
        fun initPresenter(view:View)
        fun getImageAction()
        fun getFile() : File?
    }
}