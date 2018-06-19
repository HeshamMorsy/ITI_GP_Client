package app.iti.client.iti_gp_client.utilities

import android.support.annotation.LayoutRes
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hazem on 6/11/2018.
 */
fun ViewGroup.inflate(@LayoutRes layoutRes: Int, attachToRoot: Boolean = false): View {
    return LayoutInflater.from(context).inflate(layoutRes, this, attachToRoot)
}

fun Calendar.formatDateTime(calender:Calendar):String{
    val sdfDay   = SimpleDateFormat("dd")
    val sdfMonth = SimpleDateFormat("MM")
    val sdfYear  = SimpleDateFormat("yyyy")
    val sdfHour = SimpleDateFormat("HH")
    val sdfMinute = SimpleDateFormat("mm")
    val sdfSec = SimpleDateFormat("ss")

    return (calender.get(Calendar.YEAR)).toString() + "-" + (calender.get(Calendar.MONTH) + 1).toString() + "-" + (calender.get(Calendar.DAY_OF_MONTH)).toString()+
            " " +
            (calender.get(Calendar.HOUR_OF_DAY)).toString() + ":" + (calender.get(Calendar.MINUTE)).toString() + ":" + (calender.get(Calendar.SECOND)).toString()

}