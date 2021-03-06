package app.iti.client.iti_gp_client.screens.home

import android.Manifest
import android.app.AlertDialog
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.provider.Settings
import android.support.design.widget.NavigationView
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v4.content.res.ResourcesCompat
import android.support.v4.view.GravityCompat
import android.support.v7.app.ActionBarDrawerToggle
import android.support.v7.app.AppCompatActivity
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.Gravity
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.*
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.R.id.userImage
import app.iti.client.iti_gp_client.contracts.HomeInt
import app.iti.client.iti_gp_client.entities.CancelOrderResponse
import app.iti.client.iti_gp_client.entities.Provider
import app.iti.client.iti_gp_client.screens.about.AboutActivity
import app.iti.client.iti_gp_client.screens.dropOffLocation.DropOffActivity
import app.iti.client.iti_gp_client.screens.profile.ProfileActivity
import app.iti.client.iti_gp_client.screens.trip_history.TripActivity
import app.iti.client.iti_gp_client.services.createRegisterTokinRequest
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.formatDateTime
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.places.Places
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.LatLngBounds
import com.google.android.gms.maps.model.MarkerOptions
import com.google.firebase.iid.FirebaseInstanceId
import kotlinx.android.synthetic.main.activity_home.*
import java.util.*
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import com.bumptech.glide.Glide
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

class HomeActivity : AppCompatActivity(),
        NavigationView.OnNavigationItemSelectedListener,
        GoogleApiClient.ConnectionCallbacks,
        GoogleApiClient.OnConnectionFailedListener,
        View.OnClickListener,
        OnMapReadyCallback,
        GoogleMap.OnMapClickListener,HomeInt.View {

    lateinit var presenter: HomeInt.Presenter
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    protected var mGoogleApiClient: GoogleApiClient? = null
    private var mAutoCompleteAdapter: PlacesAdapter? = null
    private var mLinearLayoutManager: LinearLayoutManager? = null
    private var cLattitude: Double = 0.0
    private var cLongitude: Double = 0.0
    private val REQUEST = 112
    private val BOUNDS = LatLngBounds(
            LatLng(-0.0, 0.0), LatLng(0.0, 0.0))

    private var mMapView: GoogleMap? = null
    private val FINE_LOCATION = Manifest.permission.ACCESS_FINE_LOCATION
    private val COURSE_LOCATION = Manifest.permission.ACCESS_COARSE_LOCATION
    private var locationPermissionGranted = false
    private lateinit var mCalender:Calendar
    private lateinit var formattedDateTime:String


    //activity on create
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)


        //handle FCM
        val intent = intent
        val message = intent.getStringExtra("message")
        if(!message.isNullOrEmpty()) {
            AlertDialog.Builder(this)
                    .setTitle("Notification")
                    .setMessage(message)
                    .setPositiveButton("Ok", { dialog, which -> }).show()
        }


        //init map
        initMap()


        //register FCM tokin
        registerTokin()
        Log.i("push",FirebaseInstanceId.getInstance().getToken())
        //initialize the presenter
        presenter = HomePresenter(this)


        //start next request screen
        order.setOnClickListener(this)

        //create the calender object to store date and time
        mCalender = Calendar.getInstance()

        //register the viewer button
        hamb.setOnClickListener(this)
        //register the cancel button
        cancel.setOnClickListener(this)
        //register the datetime button
        dateTime.setOnClickListener(this)

//        checkLocationDialog()
//        //test location permission
//        getLocationPermission()

//        //register the spinner
//        spinnerRegister()

        // register the navigation viewer toggle button
        val toggle = ActionBarDrawerToggle(
                this, drawer_layout, null, R.string.navigation_drawer_open, R.string.navigation_drawer_close)
        drawer_layout.addDrawerListener(toggle)
        toggle.syncState()

        nav_view.setNavigationItemSelectedListener(this)

//        //build google api client
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

        //load data
        loadInitialData()

    }

    private fun registerTokin() {
        val FCMTokin = FirebaseInstanceId.getInstance().getToken()
        val FCMRequest = createRegisterTokinRequest()
        val defaultPref = PreferenceHelper.defaultPrefs(this)
        val auth = defaultPref.get("auth_token", "0")
        FCMRequest.registerTokin(auth!!)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleTokinResponse, this::handleTokinError)
    }

    private fun handleTokinResponse(response: CancelOrderResponse){
        Log.i("response","respons tokin" + response)
    }
    private fun handleTokinError(error: Throwable){
        Log.i("response","respons error" + error)
    }
    private fun loadInitialData() {
        //load image url from shared default
        var sharedPref = PreferenceHelper.defaultPrefs(this)
        var imageUrl = sharedPref.get("avatar","image")
        var userName = sharedPref.get("name","Wassal User")
        var userEmail = sharedPref.get("email","user@yahoo.com")
        if(imageUrl!=""){
            Glide.with(this)
                    .load(imageUrl)
                    .into(nav_view.getHeaderView(0).findViewById(R.id.userImage))
        }




        nav_view.getHeaderView(0).findViewById<TextView>(R.id.userName).text = userName
        nav_view.getHeaderView(0).findViewById<TextView>(R.id.user_email).text = userEmail

    }

    private fun createTextChangedLisntner(): TextWatcher? {
        return object : TextWatcher {

            override fun onTextChanged(s: CharSequence, start: Int, before: Int,
                                       count: Int) {
                Log.i("googleplaces", "inside ontextchanged")
                //s.toString() != "" &&
                if (mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces", "mgoogleapi is connected")
                    mAutoCompleteAdapter!!.filter.filter(s.toString())
                } else if (!mGoogleApiClient!!.isConnected) {
                    Log.i("googleplaces", "mgoogleapi is not connected")
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
                Log.i("googleplaces", "Autocomplete item selected: " + item.description)
                /*
                     Issue a request to the Places Geo Data API to retrieve a Place object with additional details about the place.
                 */

                val placeResult = Places.GeoDataApi
                        .getPlaceById(mGoogleApiClient!!, placeId)
                placeResult.setResultCallback { places ->
                    if (places.count == 1) {
                        //Do the things here on Click.....
                        updatePickUpLocation(places.get(0).latLng)
                        searchPlaces.setText("")
                        mAutoCompleteAdapter!!.clearPlacesData()
                    } else {
                        Toast.makeText(applicationContext, Constants.SOMETHING_WENT_WRONG, Toast.LENGTH_SHORT).show()
                    }
                }
                Log.i("googleplaces", "Clicked: " + item.description)
                Log.i("googleplaces", "Called getPlaceById to get Place details for " + item.placeId)
            }
        })
    }


    override fun spinnerRegister(options: Array<Provider>) {

//        paymentSpinner.adapter = ArrayAdapter<SelectCarriers>(this,android.R.layout.simple_list_item_1,options)
        paymentSpinner.adapter = CarriersSpinnerAdapter(this, R.layout.payment_list_row, options.toMutableList())
        paymentSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onNothingSelected(parent: AdapterView<*>?) {
                //android.R.layout.simple_list_item_1

            }

            override fun onItemSelected(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
                //update presenter with provider id
                if (position !=0){
                    presenter.updateProvider(options[position].id)
                }

            }

        }
    }

    private fun getDateAndTime() {
        Log.i("clicked","date time button clicked")
        val now = Calendar.getInstance()
        Log.i("datetime","toString: " + now.toString())
        Log.i("datetime","simpleDateFormat: " +formatDateTime(now))
        val datePicker = DatePickerDialog(this, DatePickerDialog.OnDateSetListener {

            view, year, month, dayOfMonth ->
            val timePicker = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener {

                view, hourOfDay, minute ->
                if (year.equals(now.get(Calendar.YEAR)) && month.equals(now.get(Calendar.MONTH)) && dayOfMonth.equals(now.get(Calendar.DAY_OF_MONTH)) && hourOfDay < now.get(Calendar.HOUR_OF_DAY) ||
                        year.equals(now.get(Calendar.YEAR)) && month.equals(now.get(Calendar.MONTH)) && dayOfMonth.equals(now.get(Calendar.DAY_OF_MONTH)) && hourOfDay == now.get(Calendar.HOUR_OF_DAY) && minute < now.get(Calendar.MINUTE)){
                    displayError("please select a vaild time")
                }else{
                    mCalender.set(year,month,dayOfMonth,hourOfDay,minute)
                    Log.i("datetime","mcalender: " + mCalender.get(Calendar.YEAR))
                    formattedDateTime = mCalender.formatDateTime(mCalender)
                    presenter.updateDateTime(formattedDateTime)
                    Log.i("datetime","extensionfn: " + formattedDateTime)
                }

            }, now.get(Calendar.HOUR_OF_DAY), now.get(Calendar.MINUTE), false)
            timePicker.show()

        }, now.get(Calendar.YEAR), now.get(Calendar.MONTH), now.get(Calendar.DAY_OF_MONTH))
        datePicker.datePicker.minDate = System.currentTimeMillis() - 1000
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
//            super.onBackPressed()
            finishAffinity()
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
            R.id.nav_home -> {
                // close the navigationViewer
                drawer_layout.closeDrawer(Gravity.LEFT)
            }
            R.id.nav_myOrders -> {
                val myOrdersIntent = Intent(this, TripActivity::class.java)
                // start the orders history activity
                startActivity(myOrdersIntent)
            }
            R.id.nav_profile -> {
                val profileIntent = Intent(this, ProfileActivity::class.java)
                // start profile activity
                startActivity(profileIntent)
                finish()
            }
            R.id.nav_about -> {
                val aboutIntent = Intent(this, AboutActivity::class.java)
                // start about activity
                startActivity(aboutIntent)
                finish()
            }
        }

        drawer_layout.closeDrawer(GravityCompat.START)
        return true
    }

    private fun getMyLocation() {
        Log.i("clicked","get mylocation button button clicked")
        if (mMapView != null) {
            mMapView!!.clear()
        }

        if (Build.VERSION.SDK_INT >= 23) {
            var PERMISSIONS = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
            if (!hasPermission(this, PERMISSIONS)) {
                ActivityCompat.requestPermissions(this, PERMISSIONS, REQUEST)
            } else {
                getLocation()

            }
        } else {
            checkLocationDialog()
        }
//        var mLastKnownLocation = LocationServices.FusedLocationApi
//                .getLastLocation(mGoogleApiClient)


    }

    // implement View.OnClickListener interface
    override fun onClick(v: View?) {
        when (v!!.id) {
            R.id.dateTime -> getDateAndTime()

            R.id.location -> getMyLocation()

            R.id.order -> orderClicked()

            R.id.cancel -> cancelPlacessesCall()

            R.id.hamb -> openNavigationViewer()
        }
    }

    private fun openNavigationViewer() {
        Log.i("clicked","navigation button clicked")
        drawer_layout.openDrawer(Gravity.LEFT)
    }

    private fun cancelPlacessesCall() {
        Log.i("clicked","cancel button clicked")
        searchPlaces.setText("")
        mAutoCompleteAdapter!!.clearPlacesData()
    }

    private fun orderClicked() {
        Log.i("clicked","order button clicked")
        presenter.completeOrder()
    }

    override fun startDropOffLocation(){
        val requestIntent = Intent(this, DropOffActivity::class.java)
        // start your next activity
        startActivity(requestIntent)
    }

    //GoogleApiClient.ConnectionCallbacks
    override fun onConnected(p0: Bundle?) {
        Log.i("googleplaces", "connection Done")

    }

    private fun initMap(){
        var mapFragment: SupportMapFragment = getSupportFragmentManager()
                .findFragmentById(R.id.mMapView) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onConnectionSuspended(p0: Int) {
        Log.i("googleplaces", "connection suspended")
    }


    //implement GoogleApiClient.OnConnectionFailedListener interface
    override fun onConnectionFailed(p0: ConnectionResult) {
        Log.i("googleplaces", "connection failed")
    }

    // implement onMapReady interface
//    @SuppressLint("MissingPermission")
    override fun onMapReady(p0: GoogleMap?) {
        mMapView = p0
//        mMapView.setLatLngBoundsForCameraTarget(BOUNDS)
        //go to my location
        getMyLocation()
        //set onmap click listner
        mMapView!!.setOnMapClickListener(this)
        var cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(LatLng(29.9062619,31.1926205), 16f)
        mMapView!!.animateCamera(cameraUpdate)
//        mMapView!!.isMyLocationEnabled = true
        Log.i("googleplaces", "map bacame ready" + mMapView)

    }

    //handle clicks on map
    override fun onMapClick(p0: LatLng?) {
        Log.i("googleplaces", "in map click listner" + p0)
        if(checkGpsOrNetwork()){
            if (p0 != null){
                updatePickUpLocation(p0)

            }
        }else{
            checkLocationDialog()
        }
//29.9062619  //31.1926205
    }

    private fun updatePickUpLocation(p0:LatLng) {
        var gcd: Geocoder = Geocoder(applicationContext, Locale.getDefault())
        var addresses = gcd.getFromLocation(p0!!.latitude, p0!!.longitude, 1)
        if (addresses != null && addresses.size > 0) {
            val add = addresses.get(0).getAddressLine(0)
            currentPlace.text = add
            addMarker(p0,add)
            Log.i("googleplaces","lat: "+p0.latitude + "long: " + p0.longitude + "address: "+ addresses.get(0).toString())
            presenter.updatePickUpLocation(p0!!.longitude,p0!!.latitude,add)
        }

    }

    fun hasPermission(context: Context, permissions: Array<String>): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M && context != null && permissions != null) {
            for (permission in permissions) {
                if (ActivityCompat.checkSelfPermission(context, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true

    }

    private fun getLocation() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED) {

            if (checkGpsOrNetwork()){
                fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
                fusedLocationClient.lastLocation
                        .addOnSuccessListener { location: Location? ->
                            // Got last known location. In some rare situations this can be null.
                            cLattitude = location?.latitude ?: 0.0
                            cLongitude = location?.longitude ?: 0.0
                            var latLng: LatLng = LatLng(cLattitude, cLongitude)
                            Log.i("googleplaces", "mMap: " + mMapView)
                            Log.i("googleplaces", "current longitude:" + cLongitude + "current latitude: " + cLattitude)
                            addMarker(latLng,"current location")
                        }
            }else{
                checkLocationDialog()
            }


        }else{
            checkLocationDialog()
        }
    }

    private fun addMarker(latLng: LatLng,address:String) {
        mMapView?.clear()
        val height = 100
        val width = 100
        val bitmapdraw= resources.getDrawable(R.mipmap.ic_myself) as BitmapDrawable
        val b=bitmapdraw.getBitmap()
        val smallMarker = Bitmap.createScaledBitmap(b, width, height, false);

        mMapView!!.addMarker(MarkerOptions()
                .title(address)
                .position(latLng)
                .icon(BitmapDescriptorFactory.fromBitmap(smallMarker))
                )
        var cameraUpdate: CameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 16f)
        mMapView!!.animateCamera(cameraUpdate)
    }

    private fun getLocationPermission(){
        Log.i("locationPermission","getLocationPermission function called")
        val PERMISSIONS = arrayOf(android.Manifest.permission.ACCESS_COARSE_LOCATION, android.Manifest.permission.ACCESS_FINE_LOCATION)
        if (ContextCompat.checkSelfPermission(this,FINE_LOCATION)
                == PackageManager.PERMISSION_GRANTED){
            Log.i("locationPermission","FINE_LOCATION ok")
            if(ContextCompat.checkSelfPermission(this,COURSE_LOCATION)
                    == PackageManager.PERMISSION_GRANTED){
                locationPermissionGranted = true
                Log.i("locationPermission","Course_LOCATION ok")
                getLocation()
            }else{
                ActivityCompat.requestPermissions(this,PERMISSIONS,REQUEST)
            }
        }else{
            ActivityCompat.requestPermissions(this,PERMISSIONS,REQUEST)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        locationPermissionGranted = false
        Log.i("locationPermission","onRequestPermissionsResult called"+requestCode.toString()+grantResults.toString())
        when(requestCode){
            REQUEST -> {
                if (grantResults.size>0 ){
                    for (i in grantResults){
                        if (grantResults[i] != PackageManager.PERMISSION_GRANTED){
                            locationPermissionGranted = false
                            return
                        }
                    }

                    locationPermissionGranted = true
                    // initialize map
//                    initMap()
                }
            }
            2 -> getLocationPermission()
        }
    }


    override fun onResume() {
        super.onResume()
//        checkLocationDialog()
    }

    //check gps
    fun checkGpsOrNetwork():Boolean{
        var lm: LocationManager =  getApplicationContext().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        var gps_enabled = lm.isProviderEnabled(LocationManager.GPS_PROVIDER)
        var network_enabled = lm.isProviderEnabled(LocationManager.NETWORK_PROVIDER)
        return (gps_enabled||network_enabled)
    }
    // this dialog is shown when GPS is disabled ..
    fun checkLocationDialog(){

        if (!checkGpsOrNetwork()) {
            // notify user
            val askPermission = AlertDialog.Builder(this);
            askPermission.setTitle("gps wanted");
            askPermission.setCancelable(false);
//            askPermission.setIcon(R.mipmap.hamb);
            askPermission.setMessage("gps is wanted to get your current location");

            askPermission.setPositiveButton("Ok",DialogInterface.OnClickListener {
                dialog, which ->
                startActivityForResult(Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS),2)
            } )
            askPermission.setNegativeButton("cancel",DialogInterface.OnClickListener {
                dialog, which ->
                askPermission.setCancelable(false)

            } )
            askPermission.create();
            askPermission.show();
        }else{
            //test location permission
            getLocationPermission()
        }
    }




    override fun displayError(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }

    override fun initRecyclerView(mAutoCompleteAdapter: PlacesAdapter) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }



}