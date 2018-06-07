package app.iti.client.iti_gp_client.screens.dropOffLocation

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.iti.client.iti_gp_client.R
import kotlinx.android.synthetic.main.activity_drop_off.*
import android.content.Intent
import android.net.Uri
import com.google.android.gms.location.places.ui.PlacePicker
import android.widget.Toast
import com.google.android.gms.location.places.Place






class DropOffActivity : AppCompatActivity(),View.OnClickListener {

    val PLACE_PICKER_REQUEST = 1
    val PLACE_DISTINATION_REQUEST = 2
    var pickUpLong:Double? =null
    var pickUpLat:Double? =null
    var distUpLong:Double? =null
    var distUpLat:Double? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_off)
        editPickUp.setOnClickListener(this)
        editDistination.setOnClickListener(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            R.id.editPickUp -> editPickUpLocation()

            R.id.editDistination -> editDistinationLocation()
        }
    }

    private fun editDistinationLocation() {

        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this), PLACE_DISTINATION_REQUEST)
    }

    private fun editPickUpLocation() {

        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this),PLACE_PICKER_REQUEST )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, this)
                pickUpLat = place.latLng.latitude
                pickUpLong = place.latLng.longitude
                val toastMsg = String.format("Place1: %s", place.name)
                pickUpName.text = place.name
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }else if(requestCode == PLACE_DISTINATION_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                val place = PlacePicker.getPlace(data, this)
                pickUpLat = place.latLng.latitude
                pickUpLong = place.latLng.longitude
                distName.text = place.name
                val toastMsg = String.format("Place2: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }
    }
}
