package app.iti.client.iti_gp_client.contracts

/**
 * Created by Hazem on 6/8/2018.
 */
interface DropOff {
    interface Model{

    }
    interface Presenter{
        fun editDistinationLocation()
        fun editPickUpLocation()
        fun updatePickUpLocation(longitude: Double, latitude: Double, add: String)
        fun updateDestLocation(longitude: Double, latitude: Double, add: String)
        fun handleRequest()
    }
    interface View{
        fun startOrderDetails()
        fun editDistinationLocation()
        fun editPickUpLocation()
        fun displayError(msg: String)
    }
}