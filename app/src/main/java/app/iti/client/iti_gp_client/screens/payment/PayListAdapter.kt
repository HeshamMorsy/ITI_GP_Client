package app.iti.client.iti_gp_client.screens.payment

import android.content.Context
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.Payment


class PayListAdapter(val context: Context,val array: ArrayList<Payment>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        var view: View? = null
        var viewHolder: ViewHolder
//        view = LayoutInflater.from(context).inflate(R.layout.payment_list_row,parent,null)
        if(convertView == null) {
            view = LayoutInflater.from(context).inflate(R.layout.payment_method_list_row,parent,false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        var payment: Payment = getItem(position) as Payment
        viewHolder.methodTxt.text = payment.paymentMethod
        viewHolder.statusTxt.text = payment.status
        viewHolder.payImageView.setImageDrawable(context.resources.getDrawable(payment.Rid))
        if(position == 0){
            viewHolder.defaultImg.visibility = android.view.View.VISIBLE
        }
        return view
    }

    override fun getItem(position: Int): Any? {
        return array[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return array.count()
    }

    private class ViewHolder(row: View?){
        var methodTxt: TextView
        var statusTxt: TextView
        var payImageView: ImageView
        var defaultImg : ImageView

        init{
            methodTxt = row!!.findViewById<TextView>(R.id.list_paymentMethodTxt) as TextView
            statusTxt = row.findViewById<TextView>(R.id.list_statusTxt) as TextView
            payImageView = row.findViewById<ImageView>(R.id.list_img) as ImageView
            defaultImg = row.findViewById<ImageView>(R.id.list_defaultImg) as ImageView
        }
    }
}