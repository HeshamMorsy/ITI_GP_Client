package app.iti.client.iti_gp_client.screens.dropOffLocation

import app.iti.client.iti_gp_client.contracts.DropOff

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

}