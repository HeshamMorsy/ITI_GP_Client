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
        return createItemView(position, convertView, parent)
    }
    private fun createItemView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view:View  = mInflater.inflate(resource, parent, false)
        var spinnerText = view.findViewById<TextView>(R.id.spinnerText)
        var spinnerImage = view.findViewById<ImageView>(R.id.spinnerImage)
        var carriers = objects!![position]
        spinnerText.text = carriers.name
        return view
    }


}