package app.iti.client.iti_gp_client.screens.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.ImageView
import android.widget.TextView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.ProfileOption


class ProfileListAdapter(val context: Context, val profileOptions: ArrayList<ProfileOption>) : BaseAdapter() {
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view: View
        val viewHolder: ViewHolder
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile_list_row, parent, false)
            viewHolder = ViewHolder(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolder
        }
        val option = getItem(position) as ProfileOption
        viewHolder.optionTitle.text = option.optionTitle
        viewHolder.arrowImage.setImageResource(option.arrowImage)
        if(option.optionTitle == context.resources.getString(R.string.language)) {
            viewHolder.currentLanguage.visibility = View.VISIBLE
        }
        else {
            viewHolder.currentLanguage.visibility = View.GONE
        }
        if(option.optionTitle == context.resources.getString(R.string.logout)){
            viewHolder.optionTitle.setTextColor(context.resources.getColor(R.color.colorPrimary))
            viewHolder.arrowImage.visibility = View.INVISIBLE
        }else{
            viewHolder.optionTitle.setTextColor(context.resources.getColor(R.color.textColor))
            viewHolder.arrowImage.visibility = View.VISIBLE
        }

        return view
    }

    override fun getItem(position: Int): Any {
        return profileOptions[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getCount(): Int {
        return profileOptions.count()
    }


    private class ViewHolder(view: View){
        var optionTitle: TextView
        var arrowImage: ImageView
        var currentLanguage: TextView

        init {
            optionTitle = view.findViewById(R.id.profile_list_option)
            arrowImage = view.findViewById(R.id.profile_list_arrow)
            currentLanguage = view.findViewById(R.id.profile_list_language)
        }
    }
}