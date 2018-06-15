package app.iti.client.iti_gp_client.screens.dropOffLocation

import app.iti.client.iti_gp_client.contracts.DropOff
import app.iti.client.iti_gp_client.utilities.RequestCreation

/**
 * Created by Hazem on 6/8/2018.
 */
class DropOffPresenter(var view:DropOff.View):DropOff.Presenter {

    var validDestination = false
    var validPickUpLocation = false

    override fun editDistinationLocation() {
        view.editDistinationLocation()
    }

    override fun editPickUpLocation() {
        view.editPickUpLocation()
    }

    override fun updatePickUpLocation(longitude: Double, latitude: Double, add: String) {
        RequestCreation.src_latitude = latitude
        RequestCreation.src_longitude = longitude
        RequestCreation.src_address = add
        validPickUpLocation = true
    }

    override fun updateDestLocation(longitude: Double, latitude: Double, add: String) {
        RequestCreation.src_latitude = latitude
        RequestCreation.src_longitude = longitude
        RequestCreation.src_address = add
        validDestination = true
    }
    override fun handleRequest(){
        if (validDestination ){
            view.startOrderDetails()
        }else if (!validDestination){
            view.displayError("please select distination location")
        }
    }
}