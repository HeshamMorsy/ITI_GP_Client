package app.iti.client.iti_gp_client.utilities

import android.app.AlertDialog
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLConnection

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

