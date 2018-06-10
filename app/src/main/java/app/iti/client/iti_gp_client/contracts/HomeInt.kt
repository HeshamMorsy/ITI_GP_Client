package app.iti.client.iti_gp_client.contracts

import android.widget.Adapter
import app.iti.client.iti_gp_client.screens.home.PlacesAdapter

/**
 * Created by Hazem on 6/9/2018.
 */
interface HomeInt {
    interface Model{

    }
    interface View{
        fun initRecyclerView(mAutoCompleteAdapter: PlacesAdapter)
    }
    interface Presenter{
        fun placesInformation(position:Int): PlacesAdapter.PlaceAutocomplete
        fun filterPlaces(s: CharSequence)
    }
}