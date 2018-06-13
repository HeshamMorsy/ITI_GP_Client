package app.iti.client.iti_gp_client.screens.home

import android.content.Context
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.HomeInt
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Hazem on 6/9/2018.
 */
class HomePresenter(var view: HomeInt.View,var mGoogleApiClient: GoogleApiClient):HomeInt.Presenter {
    var model:HomeInt.Model

    init {
//        createPlaceAdapter()
        var defaultPref = PreferenceHelper.defaultPrefs(view as Context)
        val auth = defaultPref.get("auth_token","0")
        model = HomeModel(this)
        model.getProviders(auth!!)
    }


    private var mAutoCompleteAdapter: PlacesAdapter? = null
    private val BOUNDS = LatLngBounds(
            LatLng(-0.0, 0.0), LatLng(0.0, 0.0))



    fun createPlaceAdapter(){
        mAutoCompleteAdapter = PlacesAdapter(view as Context, R.layout.searchview_adapter,
                mGoogleApiClient!!, BOUNDS, null)
        view.initRecyclerView(mAutoCompleteAdapter!!)
    }

    override fun filterPlaces(s: CharSequence){
        mAutoCompleteAdapter!!.filter.filter(s.toString())
    }

    override fun placesInformation(position:Int): PlacesAdapter.PlaceAutocomplete {
        return mAutoCompleteAdapter!!.getItem(position)
    }

}