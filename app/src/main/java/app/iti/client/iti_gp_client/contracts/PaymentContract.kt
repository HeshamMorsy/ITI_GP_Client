package app.iti.client.iti_gp_client.contracts

import android.graphics.Bitmap
import app.iti.client.iti_gp_client.entities.FinalOrderRequest
import app.iti.client.iti_gp_client.screens.payment.*
import app.iti.client.iti_gp_client.entities.Order
import app.iti.client.iti_gp_client.entities.OrderResponse
import app.iti.client.iti_gp_client.entities.OrderToBeSent
import app.iti.client.iti_gp_client.utilities.RequestCreation

/**
 * Created by Hesham on 6/7/2018.
 * define the contract between the view [PaymentActivity], the model [PaymentModel] and the presenter [PaymentPresenter]
 */
interface PaymentContract {
    interface Model {
        fun uploadOrderData( auth: String)
    }

    interface View {
        fun updateImage(photo: Bitmap)
        fun startLoading(mes:String)
        fun endLoading()
        fun goToHomeScreen()

    }

    interface Presenter {
        fun initPresenter(view: View)
        fun receiveResponse(response: OrderResponse)
        fun errorResponse(error: Throwable)
        fun prepareOrderAndSend(order: Order)
    }
}