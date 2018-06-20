package app.iti.client.iti_gp_client.screens.tracking

import android.content.Intent
import android.graphics.Bitmap
import android.graphics.drawable.BitmapDrawable
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.OrderDetails

import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import android.os.AsyncTask.execute
import android.os.Handler
import java.util.*
import android.os.AsyncTask.execute
import android.util.Log
import app.iti.client.iti_gp_client.entities.TrackingRequest
import app.iti.client.iti_gp_client.screens.login.LoginActivity
import app.iti.client.iti_gp_client.services.createTrackingRequest
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class TrackingActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var myOrder:OrderDetails
    private var mDelayHandler: Handler? = null
    private val SPLASH_DELAY: Long = 3000 //3 seconds


//    private var mRunnable: Runnable = Runnable {
//
//        Log.i("handler","bta3 hesham")
//        mDelayHandler?.postDelayed(mRunnable,SPLASH_DELAY)
//
//    }



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_tracking)
        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager
                .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        val i = intent
        val order = i.getSerializableExtra("order") as OrderDetails
        myOrder = order

        //start handler
        mDelayHandler = Handler()

        //Navigate with delay
        mDelayHandler?.postDelayed(object : Runnable {
            override fun run() {
                mDelayHandler?.postDelayed(this, 5000)
                Log.i("handler","hi handler")
                setUpRequest()
            }
        },5000)
    }

    private fun setUpRequest() {
        var trackingRequest = createTrackingRequest()
        val defaultPref = PreferenceHelper.defaultPrefs(this)
        val auth = defaultPref.get("auth_token", "0")
        trackingRequest.trackLocation(auth!!,myOrder?.id)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleTrackingResponse, this::handleTrackingError)
    }

    private fun handleTrackingResponse(response: TrackingRequest){
        Log.i("response","respons tracking" + response)
        if (response.message == "success"){
            val bitmapdraw= resources.getDrawable(R.mipmap.ic_myself) as BitmapDrawable
            val b=bitmapdraw.getBitmap()
            val currentMarker = Bitmap.createScaledBitmap(b, 100, 100, false)
            val currentLocation = LatLng(response.data.location.latitude, response.data.location.longitude)
            mMap.addMarker(MarkerOptions().position(currentLocation).title(myOrder.pickup_location)
                    .icon(BitmapDescriptorFactory.fromBitmap(currentMarker))
            )
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(currentLocation,13f))
        }



    }
    private fun handleTrackingError(error: Throwable){
        Log.i("response","respons error" + error)
    }

    override fun onDestroy() {

        mDelayHandler?.removeCallbacksAndMessages(null);


        super.onDestroy()
    }

    /**
     * Manipulates the map once available.
     * This callback is triggered when the map is ready to be used.
     * This is where we can add markers or lines, add listeners or move the camera. In this case,
     * we just add a marker near Sydney, Australia.
     * If Google Play services is not installed on the device, the user will be prompted to install
     * it inside the SupportMapFragment. This method will only be triggered once the user has
     * installed Google Play services and returned to the app.
     */
    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap


        val height = 100
        val width = 100
        val bitmapdraw= resources.getDrawable(R.mipmap.pickup) as BitmapDrawable
        val b=bitmapdraw.getBitmap()
        val pickupMarker = Bitmap.createScaledBitmap(b, width, height, false)

        val bitmapdraw2= resources.getDrawable(R.mipmap.dest) as BitmapDrawable
        val b2=bitmapdraw2.getBitmap()
        val destMarker = Bitmap.createScaledBitmap(b2, width, height, false)

        // Add a marker in Sydney and move the camera
        val pickUpLocation = LatLng(myOrder.src_latitude, myOrder.src_longitude)
        val destLocation = LatLng(myOrder.dest_latitude,myOrder.dest_longitude)
        mMap.addMarker(MarkerOptions().position(pickUpLocation).title(myOrder.pickup_location)
                .icon(BitmapDescriptorFactory.fromBitmap(pickupMarker))
        )

        mMap.addMarker(MarkerOptions().position(destLocation).title(myOrder.dropoff_location)
                .icon(BitmapDescriptorFactory.fromBitmap(destMarker))
        )

        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(pickUpLocation,16f))

    }




}
