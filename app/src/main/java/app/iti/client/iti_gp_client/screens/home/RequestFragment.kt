package app.iti.client.iti_gp_client.screens.home


import android.annotation.SuppressLint
//import android.app.Fragment
//import android.os.Build
import android.os.Bundle
import android.support.v4.app.Fragment
import android.support.v7.widget.LinearLayoutManager
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.EditText
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.model.LatLngBounds
import kotlinx.android.synthetic.main.fragment_request.*
//import com.razor.googleplacesautocompletesample.utility.Constants


/**
 * A simple [Fragment] subclass.
 * Use the [RequestFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class RequestFragment : Fragment(), GoogleApiClient.ConnectionCallbacks, GoogleApiClient.OnConnectionFailedListener, View.OnClickListener{
    protected var mGoogleApiClient: GoogleApiClient? = null
    private var mAutoCompleteAdapter: PlacesAdapter? = null
    private var mLinearLayoutManager:LinearLayoutManager? = null

    private val BOUNDS_INDIA = LatLngBounds(
            LatLng(-0.0, 0.0), LatLng(0.0, 0.0))

    override fun onConnected(p0: Bundle?) {
        Log.i("googleplaces","connection Done")
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i("googleplaces","connection suspended")
    }

    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("googleplaces","connection failed")
    }

    override fun onClick(p0: View?) {

    }

    //    var mMapView: MapView? = null
    //    private val googleMap: GoogleMap? = null
    //    var markerPoints: ArrayList<LatLng>? = null
    var googleMap: GoogleMap? = null
    var mMapView: MapView? = null
    var lastSearches:MutableList<String>? = null
    var editPlaces:EditText?=null

    // TODO: Rename and change types of parameters
    private var mParam1: String? = null
    private var mParam2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mParam1 = arguments!!.getString(ARG_PARAM1)
            mParam2 = arguments!!.getString(ARG_PARAM2)
        }

        buildGoogleApiClient()


    }


    @SuppressLint("MissingPermission")
    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        val view: View= inflater.inflate(R.layout.fragment_request, container, false)
        mMapView = view.findViewById(R.id.mapView)
        editPlaces = view.findViewById(R.id.searchPlaces)

        mMapView?.onCreate(savedInstanceState)

        mMapView?.onResume() // needed to get the map to display immediately

        try {
            MapsInitializer.initialize(activity!!.applicationContext)
        } catch (e: Exception) {
            e.printStackTrace()
        }


        mMapView?.getMapAsync({ mMap ->
            googleMap = mMap

            // For showing a move to my location button
            googleMap?.setMyLocationEnabled(true)

            // For dropping a marker at a point on the Map
            val sydney = LatLng(-34.0, 151.0)
            googleMap?.addMarker(MarkerOptions().position(sydney).title("Marker Title").snippet("Marker Description"))

            // For zooming automatically to the location of the marker
            val cameraPosition = CameraPosition.Builder().target(sydney).zoom(12f).build()
            googleMap?.animateCamera(CameraUpdateFactory.newCameraPosition(cameraPosition))
        })

        return view
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        searchPlaces1.addTextChangedListener(object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                Log.i("googleplaces","inside ontextchanged")
                if (s.toString() != "" && mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces","mgoogleapi is connected")
                    mAutoCompleteAdapter!!.filter.filter(s.toString())
                } else if (!mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces","mgoogleapi is not connected")
//                    Toast.makeText(this, SyncStateContract.Constants.API_NOT_CONNECTED, Toast.LENGTH_SHORT).show()
//                    Log.e(SyncStateContract.Constants.PlacesTag, Constants.API_NOT_CONNECTED)
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        })


        mRecyclerView11.addOnItemTouchListener(
                RecyclerItemClickListener(context, object : RecyclerItemClickListener.OnItemClickListener {
                    override fun onItemClick(view: View, position: Int) {
                        val item = mAutoCompleteAdapter!!.getItem(position)
                        val placeId = item.placeId
                        Log.i("TAG", "Autocomplete item selected: " + item.description)
                        /*
                             Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                         */

                        val placeResult = Places.GeoDataApi
                                .getPlaceById(mGoogleApiClient!!, placeId)
                        placeResult.setResultCallback { places ->
                            if (places.count == 1) {
                                //Do the things here on Click.....
                                Toast.makeText(context, places.get(0).latLng.toString(), Toast.LENGTH_SHORT).show()
                            } else {
                                Toast.makeText(context, Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
                            }
                        }
                        Log.i("TAG", "Clicked: " + item.description)
                        Log.i("TAG", "Called getPlaceById to get Place details for " + item.placeId)
                    }
                })
        );

        mAutoCompleteAdapter = PlacesAdapter(context!!, R.layout.searchview_adapter,
                mGoogleApiClient!!, BOUNDS_INDIA, null)
        mLinearLayoutManager = LinearLayoutManager(context)
        mRecyclerView11.layoutManager = mLinearLayoutManager
        mRecyclerView11.adapter = mAutoCompleteAdapter
//        var mapFragment : SupportMapFragment  = childFragmentManager.findFragmentById(R.id.mapView) as SupportMapFragment
//        mapFragment.getMapAsync(this)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"
        private val ARG_PARAM2 = "param2"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.
         *
         * @param param1 Parameter 1.
         * @param param2 Parameter 2.
         * @return A new instance of fragment RequestFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(param1: String, param2: String): RequestFragment {
            val fragment = RequestFragment()
            val args = Bundle()
            args.putString(ARG_PARAM1, param1)
            args.putString(ARG_PARAM2, param2)
            fragment.arguments = args
            return fragment
        }
    }

/*    override fun onMapReady(p0: GoogleMap?) {
        googleMap = p0
        var myLatLng: LatLng = LatLng(0.0,0.0)
        var option: MarkerOptions = MarkerOptions()
        option.position(myLatLng).title("test")
        googleMap?.addMarker(option)
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(myLatLng))
    }*/

    override fun onResume() {
        super.onResume()
        mMapView?.onResume()
        if (!mGoogleApiClient!!.isConnected() && !mGoogleApiClient!!.isConnecting()) {
            Log.v("Google API", "Connecting")
            mGoogleApiClient!!.connect()
        }
    }

    override fun onPause() {
        super.onPause()
        mMapView?.onPause()
    }

    override fun onDestroy() {
        super.onDestroy()
        mMapView?.onDestroy()
    }

    override fun onLowMemory() {
        super.onLowMemory()
        mMapView?.onLowMemory()
    }



    internal fun buildGoogleApiClient() {


            mGoogleApiClient = GoogleApiClient.Builder(this.context!!)

                    .addConnectionCallbacks(this)
                    .addOnConnectionFailedListener(this)
                    .addApi(LocationServices.API)
                    .addApi(Places.GEO_DATA_API)
                    .addApi(Places.PLACE_DETECTION_API)
                    .build()

//            mGoogleApiClient.connect()

    }


}// Required empty public constructor

object Constants {
    val API_NOT_CONNECTED = "Google API not connected"
    val SOMETHING_WENT_WRONG = "OOPs!!! Something went wrong..."
    var PlacesTag = "Google Places Auto Complete"
}

