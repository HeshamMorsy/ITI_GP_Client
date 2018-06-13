package app.iti.client.iti_gp_client.screens.profile

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseExpandableListAdapter
import android.widget.ImageView
import android.widget.TextView
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.entities.ProfileOption


class ExpandableListAdapter(val context: Context, val header: ArrayList<ProfileOption>,
                            private val subList: HashMap<String, ArrayList<String>> ) : BaseExpandableListAdapter() {

    override fun getGroup(groupPosition: Int): Any {
        return header[groupPosition]
    }

    override fun isChildSelectable(groupPosition: Int, childPosition: Int): Boolean {
        return true
    }

    override fun hasStableIds(): Boolean {
        return false
    }

    override fun getGroupView(groupPosition: Int, isExpanded: Boolean, convertView: View?, parent: ViewGroup?): View {
        var view: View
        val viewHolder: ViewHolderHeader
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.profile_list_row, parent, false)
            viewHolder = ViewHolderHeader(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolderHeader
        }
        val option = getGroup(groupPosition) as ProfileOption
        viewHolder.optionTitle.text = option.optionTitle
        viewHolder.arrowImage.setImageResource(option.arrowImage)
        viewHolder.currentLanguage.text = option.language
        if(option.optionTitle == context.resources.getString(R.string.language)) {
            viewHolder.currentLanguage.visibility = View.VISIBLE
            viewHolder.currentLanguage.text = option.language
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

    override fun getChildrenCount(groupPosition: Int): Int {
        return subList[header[groupPosition].optionTitle]!!.count()
    }

    override fun getChild(groupPosition: Int, childPosition: Int): Any {
        // get the list of key payment
        return subList[header[groupPosition].optionTitle]!![childPosition]
    }

    override fun getGroupId(groupPosition: Int): Long {
        return groupPosition.toLong()
    }

    override fun getChildView(groupPosition: Int, childPosition: Int, isLastChild: Boolean, convertView: View?, parent: ViewGroup?): View {
        val view: View
        val viewHolder: ViewHolderSub
        if(convertView == null){
            view = LayoutInflater.from(context).inflate(R.layout.language_list_row, parent, false)
            viewHolder = ViewHolderSub(view)
            view.tag = viewHolder
        }else{
            view = convertView
            viewHolder = view.tag as ViewHolderSub
        }
        val option = getChild(groupPosition ,childPosition) as String
        viewHolder.languageTitle.text = option

        return view
    }

    override fun getChildId(groupPosition: Int, childPosition: Int): Long {
        return childPosition.toLong()
    }

    override fun getGroupCount(): Int {
        return header.count()
    }


    private class ViewHolderHeader(view: View){
        var optionTitle: TextView
        var arrowImage: ImageView
        var currentLanguage: TextView

        init {
            optionTitle = view.findViewById(R.id.profile_list_option)
            arrowImage = view.findViewById(R.id.profile_list_arrow)
            currentLanguage = view.findViewById(R.id.profile_list_language)
        }
    }

    private class ViewHolderSub(view: View){
        var languageTitle: TextView

        init {
            languageTitle = view.findViewById(R.id.language_txt)
        }
    }
}