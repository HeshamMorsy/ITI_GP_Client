package app.iti.client.iti_gp_client.screens.home

import android.content.Context
import android.util.Log
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.contracts.HomeInt
import app.iti.client.iti_gp_client.entities.Image
import app.iti.client.iti_gp_client.entities.Provider
import app.iti.client.iti_gp_client.entities.ProvidersRequest
import app.iti.client.iti_gp_client.entities.SelectCarriers
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import app.iti.client.iti_gp_client.utilities.RequestCreation
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds

/**
 * Created by Hazem on 6/9/2018.
 */
class HomePresenter(var view: HomeInt.View,var mGoogleApiClient: GoogleApiClient):HomeInt.Presenter {

    //validate request paremeters
    private var srcLocationValid = false
    private var dateTimeValid = false
    private var providerValid = false




    var model:HomeInt.Model

    init {
//        createPlaceAdapter()
        var defaultPref = PreferenceHelper.defaultPrefs(view as Context)
        val auth = defaultPref.get("auth_token","0")
        model = HomeModel(this)
        model.getProviders(auth!!)
    }


    //handle providers
    override fun handleProviders(response: ProvidersRequest) {
        Log.i("response",response.toString())
        if (response.message == "success"){
            val providers = arrayOf(Provider(0,"Select Carrier", Image("xxx")),*response.providers)
            view.spinnerRegister(providers)

        }else{
            view.displayError("cannot find providers")
        }
        val carrier1 = SelectCarriers("image1","Select Carriers")
        val carrier2 = SelectCarriers("image2","4.6")
        val carrier3 = SelectCarriers("image3","4.1")
        val carrier4 = SelectCarriers("image3","4.9")
        val options = arrayOf(carrier1,carrier2,carrier3,carrier4)
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

    override fun completeOrder(){
        if (srcLocationValid && dateTimeValid &&providerValid){
            view.startDropOffLocation()
        }else if(!srcLocationValid){
            view.displayError("please enter pickUp location information...")
        }else if (!dateTimeValid){
            view.displayError("please enter date and time")
        }else if (!providerValid){
            view.displayError("please choose a provider")
        }
    }

    override fun updateDateTime(dateTime: String){
        RequestCreation.time = dateTime
        dateTimeValid = true
    }
    override fun updateProvider(provider_id:Int){
        RequestCreation.provider_id = provider_id
        providerValid = true
    }
    override fun updatePickUpLocation(longitude: Double, latitude: Double, add: String) {
        RequestCreation.src_latitude = latitude
        RequestCreation.src_longitude = longitude
        RequestCreation.src_address = add
        srcLocationValid = true
    }
}