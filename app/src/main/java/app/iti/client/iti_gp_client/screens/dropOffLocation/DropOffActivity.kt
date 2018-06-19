package app.iti.client.iti_gp_client.screens.dropOffLocation

import android.app.Activity
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import app.iti.client.iti_gp_client.R
import kotlinx.android.synthetic.main.activity_drop_off.*
import android.content.Intent
import android.util.Log
import com.google.android.gms.location.places.ui.PlacePicker
import android.widget.Toast
import app.iti.client.iti_gp_client.contracts.DropOff
import app.iti.client.iti_gp_client.screens.order_description.OrderActivity
import app.iti.client.iti_gp_client.utilities.Constants.Companion.PLACE_DISTINATION_REQUEST
import app.iti.client.iti_gp_client.utilities.Constants.Companion.PLACE_PICKER_REQUEST
import app.iti.client.iti_gp_client.utilities.RequestCreation

/**
 * created by Hazem
 */

class DropOffActivity : AppCompatActivity(),View.OnClickListener, DropOff.View{

    //ref to the presenter of the activity
    var presenter:DropOff.Presenter? = null
    var pickUpLong:Double? =null
    var pickUpLat:Double? =null
    var distLong:Double? =null
    var distLat:Double? =null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_drop_off)
        //check the Registrition request
        pickUpName.setText(RequestCreation.src_address)
        checkRequest()
        //register the pickup and distnation buttons
        editPickUp.setOnClickListener(this)
        editDistination.setOnClickListener(this)

        //regester the complete order button
        requestNext.setOnClickListener(this)

        //create the presenter for the activity
        presenter = DropOffPresenter(this)
    }

    private fun checkRequest() {
        Log.i("requestObject",RequestCreation.toString())
    }

    override fun onClick(v: View?) {
        when(v?.id){
            //when editpickup clicked
            R.id.editPickUp -> presenter!!.editPickUpLocation()
            //when edit distnition clicked
            R.id.editDistination -> presenter!!.editDistinationLocation()

            R.id.requestNext -> presenter!!.handleRequest()
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode == PLACE_PICKER_REQUEST && data !=null) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(this,data)
                pickUpLat = place.latLng.latitude
                pickUpLong = place.latLng.longitude
                val toastMsg = String.format("Place1: %s", place.name)
                pickUpName.text = place.name
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
                presenter!!.updatePickUpLocation(pickUpLong!!,pickUpLat!!,place.name.toString())
            }
        }else if(requestCode == PLACE_DISTINATION_REQUEST && data !=null){
            if (resultCode == Activity.RESULT_OK){
                val place = PlacePicker.getPlace(this, data)
                distLat = place.latLng.latitude
                distLong = place.latLng.longitude
                distName.text = place.name
                val toastMsg = String.format("Place2: %s", place.name)
                Toast.makeText(this, toastMsg, Toast.LENGTH_LONG).show()
                presenter!!.updateDestLocation(distLong!!,distLat!!,place.name.toString())
            }
        }
    }

    override fun startOrderDetails(){
        val requestIntent = Intent(this, OrderActivity::class.java)
        // start your next activity
        startActivity(requestIntent)
    }
    override fun displayError(msg:String){
        Toast.makeText(this,msg,Toast.LENGTH_SHORT).show()
    }
}
