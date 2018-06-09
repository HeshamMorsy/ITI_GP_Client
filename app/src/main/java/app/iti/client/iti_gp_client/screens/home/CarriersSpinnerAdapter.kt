package app.iti.client.iti_gp_client.screens.home

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.TextView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.SelectCarriers

/**
 * Created by Hazem on 6/7/2018.
 */
class CarriersSpinnerAdapter(var context1: Context?,var resource: Int,var objects: MutableList<SelectCarriers>?) : ArrayAdapter<SelectCarriers>(context1, resource, objects) {

    var mInflater = LayoutInflater.from(context)

    override fun getDropDownView(position:Int, convertView:View?,
                                 parent: ViewGroup): View {
        return createItemView(position, convertView, parent)
    }

    override fun getView(position: Int,convertView: View?, parent: ViewGroup): View {
        return displayItemView(position, convertView, parent)
    }

    override fun isEnabled(position: Int): Boolean {
        return if (position == 0) {
            // Disable the first item from Spinner
            // First item will be use for hint
            false
        } else {
            true
        }
    }
    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View  = mInflater.inflate(resource, parent, false)
        if(position == 0){
            //set the hint
            var dev = view.findViewById<View>(R.id.devider)
            dev.visibility = View.GONE
            var label = view.findViewById<TextView>(R.id.carrierLabel)
//            var carrierArrow = view.findViewById<ImageView>(R.id.spinnerArrow)
            label.visibility = View.VISIBLE
//            carrierArrow.visibility = View.VISIBLE
            var spinnerText = view.findViewById<TextView>(R.id.carrierRate)
            spinnerText.visibility = View.INVISIBLE
        }
        var spinnerText = view.findViewById<TextView>(R.id.carrierRate)
        var spinnerImage = view.findViewById<ImageView>(R.id.carrierImage)
        var carriers = objects!![position]
        spinnerText.text = carriers.name
        when(position){
            0 -> spinnerImage.setBackgroundResource(R.mipmap.ic_carriers)
            1 -> spinnerImage.setBackgroundResource(R.mipmap.ic_fedex)
            2 -> spinnerImage.setBackgroundResource(R.mipmap.ic_dhl)
            3 -> spinnerImage.setBackgroundResource(R.mipmap.ic_tnt)
        }

        return view
    }
    private fun displayItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View  = mInflater.inflate(resource, parent, false)
        if(position == 0){


            var label = view.findViewById<TextView>(R.id.carrierLabel)
            label.visibility = View.VISIBLE
            var spinnerText = view.findViewById<TextView>(R.id.carrierRate)
            spinnerText.visibility = View.INVISIBLE
        }

        var carrierArrow = view.findViewById<ImageView>(R.id.spinnerArrow)
//        carrierArrow.visibility = View.VISIBLE
        var dev = view.findViewById<View>(R.id.devider)
        dev.visibility = View.GONE
        var spinnerText = view.findViewById<TextView>(R.id.carrierRate)
        var spinnerImage = view.findViewById<ImageView>(R.id.carrierImage)
        var carriers = objects!![position]
        spinnerText.text = carriers.name
        when(position){
            0 -> spinnerImage.setBackgroundResource(R.mipmap.ic_carriers)
            1 -> spinnerImage.setBackgroundResource(R.mipmap.ic_fedex)
            2 -> spinnerImage.setBackgroundResource(R.mipmap.ic_dhl)
            3 -> spinnerImage.setBackgroundResource(R.mipmap.ic_tnt)
        }

        return view
    }



}