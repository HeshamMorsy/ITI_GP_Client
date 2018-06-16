package app.iti.client.iti_gp_client.contracts

import app.iti.client.iti_gp_client.entities.Provider
import app.iti.client.iti_gp_client.entities.ProvidersRequest
import app.iti.client.iti_gp_client.entities.SelectCarriers
import app.iti.client.iti_gp_client.screens.home.PlacesAdapter

/**
 * Created by Hazem on 6/9/2018.
 */
interface HomeInt {
    interface Model{
        fun getProviders(auth:String)

    }
    interface View{
        fun initRecyclerView(mAutoCompleteAdapter: PlacesAdapter)
        fun spinnerRegister(options:Array<Provider>)
        fun startDropOffLocation()
        fun displayError(msg:String)
    }
    interface Presenter{
        fun placesInformation(position:Int): PlacesAdapter.PlaceAutocomplete
        fun filterPlaces(s: CharSequence)
        fun handleProviders(response: ProvidersRequest)
        fun completeOrder()
        fun updatePickUpLocation(longitude: Double, latitude: Double, add: String)
        fun updateDateTime(dateTime: String)
        fun updateProvider(provider_id:Int)
    }

}