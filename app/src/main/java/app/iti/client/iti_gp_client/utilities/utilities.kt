package app.iti.client.iti_gp_client.utilities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection
import java.text.SimpleDateFormat
import java.util.*

/**
 * Created by Hazem on 6/2/2018.
 */
//fun isOnline(context: Context):Boolean{
//
//    if (isNetworkAvailable(context)){
//
//        val urlc: HttpURLConnection = URL("http://www.google.com").openConnection() as HttpURLConnection
//        urlc.setRequestProperty("User-Agent", "Test")
//        urlc.setRequestProperty("Connection", "close")
//        urlc.connectTimeout = 1500
//        urlc.connect()
//        return (urlc.responseCode == 200)
//    }
//    return false
//}

fun isNetworkAvailable(context: Context):Boolean{
    val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    val activeNetwork: NetworkInfo? = cm.activeNetworkInfo
    val isConnected: Boolean = activeNetwork?.isConnectedOrConnecting == true
    return isConnected
}

fun formatDateTime(cal:Calendar):String{
    val sdfDay   = SimpleDateFormat("dd")
    val sdfMonth = SimpleDateFormat("MM")
    val sdfYear  = SimpleDateFormat("yyyy")
    val sdfHour = SimpleDateFormat("HH")
    val sdfMinute = SimpleDateFormat("mm")
    val sdfSec = SimpleDateFormat("ss")

    return sdfYear.format(cal.get(Calendar.YEAR)) + "-" + sdfMonth.format(cal.get(Calendar.MONTH)) + "-" + sdfDay.format(cal.get(Calendar.DAY_OF_MONTH))+
            " " +
            sdfHour.format(cal.get(Calendar.HOUR_OF_DAY)) + ":" + sdfMinute.format(cal.get(Calendar.MINUTE)) + ":" + sdfSec.format(cal.get(Calendar.SECOND))
}