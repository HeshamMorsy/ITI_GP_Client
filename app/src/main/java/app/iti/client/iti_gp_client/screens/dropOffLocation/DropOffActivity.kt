package app.iti.client.iti_gp_client.screens.dropOffLocation

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.iti.client.iti_gp_client.R
import kotlinx.android.synthetic.main.activity_drop_off.*
import android.content.Intent
import com.google.android.gms.location.places.ui.PlacePicker
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.DropOff





class DropOffActivity : AppCompatActivity(),View.OnClickListener, DropOff.View {

    //ref to the presenter of the activity
    var presenter:DropOff.Presenter? = null
    val PLACE_PICKER_REQUEST = 1
    val PLACE_DISTINATION_REQUEST = 2
    var pickUpLong:Double? =null
    var pickUpLat:Double? =null
    var distLong:Double? =null
    var distLat:Double? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_off)

        //register the pickup and distnation buttons
        editPickUp.setOnClickListener(this)
        editDistination.setOnClickListener(this)

        //create the presenter for the activity
        presenter = DropOffPresenter(this)
    }
    override fun onClick(v: View?) {
        when(v?.id){
            //when editpickup clicked
            R.id.editPickUp -> editPickUpLocation()
            //when edit distnition clicked
            R.id.editDistination -> editDistinationLocation()
        }
    }

    override fun editDistinationLocation() {

        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this), PLACE_DISTINATION_REQUEST)
    }

    override fun editPickUpLocation() {

        val builder = PlacePicker.IntentBuilder()

        startActivityForResult(builder.build(this),PLACE_PICKER_REQUEST )
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent) {
        if (requestCode == PLACE_PICKER_REQUEST) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this,data)
                pickUpLat = place.latLng.latitude
                pickUpLong = place.latLng.longitude
                val toastMsg = String.format("Place1: %s", place.name)
                pickUpName.text = place.name
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }else if(requestCode == PLACE_DISTINATION_REQUEST){
            if (resultCode == Activity.RESULT_OK){
                val place = PlacePicker.getPlace(this, data)
                distLat = place.latLng.latitude
                distLong = place.latLng.longitude
                distName.text = place.name
                val toastMsg = String.format("Place2: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
            }
        }
    }
}
