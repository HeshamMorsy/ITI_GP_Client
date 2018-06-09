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
    }
    interface View{
        fun editDistinationLocation()
        fun editPickUpLocation()
    }
}