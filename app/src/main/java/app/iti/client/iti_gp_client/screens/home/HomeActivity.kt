package app.iti.client.iti_gp_client.screens.home

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.os.Build
import android.os.Bundle
import android.support.annotation.RequiresApi
import android.support.design.widget.NavigationView
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.SelectCarriers
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*


class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        OnMapReadyCallback{


    protected var mGoogleApiClient: GoogleApiClient? = null
    private var mAutoCompleteAdapter: PlacesAdapter? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var cLattitude:Double = 0.0
    private var cLongitude:Double = 0.0

    private val BOUNDS = LatLngBounds(
            LatLng(-0.0, 0.0), LatLng(0.0, 0.0))

    private var mMapView: GoogleMap? = null



    // implement onMapReady interface
    override fun onMapReady(p0: GoogleMap?) {
        mMapView = p0

        Log.i("googleplaces","map bacame ready" + mMapView)

    }




    //GoogleApiClient.ConnectionCallbacks
    override fun onConnected(p0: Bundle?) {
        Log.i("googleplaces","connection Done")
        var mapFragment: SupportMapFragment =  getSupportFragmentManager()
            .findFragmentById(R.id.mMapView) as SupportMapFragment
            mapFragment.getMapAsync(this)
    }


    override fun onConnectionSuspended(p0: Int) {
        Log.i("googleplaces","connection suspended")
    }


    //implement GoogleApiClient.OnConnectionFailedListener interface
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("googleplaces","connection failed")
    }


    // implement View.OnClickListener interface
    @SuppressLint("MissingPermission")
    override fun onClick(v: View?) {
        when (v!!.id){
            R.id.dateTime -> getDateAndTime()


            R.id.location -> {
                Log.i("googleplaces"," location button pressed")
                var mLastKnownLocation = LocationServices.FusedLocationApi
                        .getLastLocation(mGoogleApiClient)

                cLattitude = mLastKnownLocation.latitude
                cLongitude = mLastKnownLocation.longitude
                getMyLocation()
            }


        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("MissingPermission")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)

        //register the datetime button
        dateTime.setOnClickListener(this)


        //register the spinner
        spinnerRegister()

        // register the navigation viewer toggle button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

        //build google api client
        buildGoogleApiClient()



        // onitemclicklistner for the recyler view
        var listner = createItemClickListner()
        // register the listner for recyclerview
        mRecyclerView.addOnItemTouchListener(listner)

        //register location button
        location.setOnClickListener(this)


        mAutoCompleteAdapter = PlacesAdapter(this, R.layout.searchview_adapter,
                mGoogleApiClient!!, BOUNDS, null)
        mLinearLayoutManager = LinearLayoutManager(this)
        mRecyclerView.layoutManager = mLinearLayoutManager
        mRecyclerView.adapter = mAutoCompleteAdapter

        //register edit text listner
        var textChangedListner = createTextChangedLisntner()
        searchPlaces.addTextChangedListener(textChangedListner)

    }

    private fun createTextChangedLisntner(): TextWatcher? {
        return object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                Log.i("googleplaces","inside ontextchanged")
                //s.toString() != "" &&
                if ( mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces","mgoogleapi is connected")
                    mAutoCompleteAdapter!!.filter.filter(s.toString())
                } else if (!mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces","mgoogleapi is not connected")
                }

            }

            override fun beforeTextChanged(s: CharSequence, start: Int, count: Int,
                                           after: Int) {

            }

            override fun afterTextChanged(s: Editable) {

            }
        }
    }

    private fun createItemClickListner(): RecyclerView.OnItemTouchListener? {
        return RecyclerItemClickListener(this, object : RecyclerItemClickListener.OnItemClickListener {
            override fun onItemClick(view: View, position: Int) {
                val item = mAutoCompleteAdapter!!.getItem(position)
                val placeId = item.placeId
                Log.i("googleplaces", "Autocomplete item selected: " + item.description )
                /*
                     Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                 */

                val placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient!!, placeId)
                placeResult.setResultCallback { places ->
                    if (places.count == 1) {
                        //Do the things here on Click.....
                        Toast.makeText(applicationContext, places.get(0).latLng.toString(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(applicationContext, Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
                    }
                }
                Log.i("googleplaces", "Clicked: " + item.description)
                Log.i("googleplaces", "Called getPlaceById to get Place details for " + item.placeId)
            }
        })
    }


    private fun spinnerRegister() {
        val carrier1 = SelectCarriers("image1","fedix")
        val carrier2 = SelectCarriers("image2","aramix")
        val carrier3 = SelectCarriers("image3","TNT")
        val options = arrayOf(carrier1,carrier2,carrier3)
        paymentSpinner.adapter = ArrayAdapter<SelectCarriers>(this,android.R.layout.simple_list_item_1,options)
        paymentSpinner.onItemSelectedListener = object :AdapterView.OnItemSelectedListener{
            override fun onNothingSelected(parent: AdapterView<*>?) {

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {

            }

        }
    }

    private fun getDateAndTime() {
        val now = Calendar.getInstance()
        val datePicker = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            Toast.makeText(this,"year:"+year + "month:"+month + "day:"+dayOfMonth,Toast.LENGTH_SHORT).show()
            val timePicker =TimePickerDialog(this,TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                Toast.makeText(this,"hour:"+hourOfDay + "minute:"+minute ,Toast.LENGTH_SHORT).show()
            },now.get(Calendar.HOUR_OF_DAY),now.get(Calendar.MINUTE),false)
            timePicker.show()
        },now.get(Calendar.YEAR),now.get(Calendar.MONTH),now.get(Calendar.DAY_OF_MONTH))
        datePicker.show()
    }


    override fun onStart() {
        super.onStart()
        if (!mGoogleApiClient!!.isConnected() && !mGoogleApiClient!!.isConnecting()) {
            Log.v("Google API", "Connecting")
            mGoogleApiClient!!.connect()
        }

    }

    internal fun buildGoogleApiClient() {


        mGoogleApiClient = GoogleApiClient.Builder(this)

                .addConnectionCallbacks(this)
                .addOnConnectionFailedListener(this)
                .addApi(LocationServices.API)
                .addApi(Places.GEO_DATA_API)
                .addApi(Places.PLACE_DETECTION_API)
                .build()

//            mGoogleApiClient.connect()

    }

    override fun onBackPressed() {
        if (drawer_layout.isDrawerOpen(GravityCompat.START)) {
            drawer_layout.closeDrawer(GravityCompat.START)
        } else {
            super.onBackPressed()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        // Inflate the menu; this adds items to the action bar if it is present.
        menuInflater.inflate(R.menu.home, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        when (item.itemId) {
            R.id.action_settings -> return true
            else -> return super.onOptionsItemSelected(item)
        }
    }

    override fun onNavigationItemSelected(item: MenuItem): Boolean {
        // Handle navigation view item clicks here.
        when (item.itemId) {
            R.id.nav_camera -> {
                // Handle the camera action
            }
            R.id.nav_gallery -> {

            }
            R.id.nav_slideshow -> {

            }
            R.id.nav_manage -> {

            }
            R.id.nav_share -> {

            }
            R.id.nav_send -> {

            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getMyLocation(){
        if(mMapView != null) {
            mMapView!!.clear()
        }
        var latLng:LatLng = LatLng(cLattitude,cLongitude)
        Log.i("googleplaces","mMap: " + mMapView)
        Log.i("googleplaces","current longitude:" + cLongitude + "current latitude: "+ cLattitude)
        mMapView!!.addMarker(MarkerOptions().title("here").position(latLng))
        var cameraUpdate:CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng,18f)
        mMapView!!.animateCamera(cameraUpdate)

    }



}
