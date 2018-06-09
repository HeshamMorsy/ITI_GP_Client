package app.iti.client.iti_gp_client.utilities

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog
import android.util.Log
import android.view.LayoutInflater
import android.widget.TextView
import app.iti.client.iti_gp_client.R

/**
 * Created by Hesham on 6/3/2018.
 * this file is to prepare the alert dialogs to use it with simple way
 */

fun alertWithOneButton(context: Context, title:String, message:String, btnTitle:String){
    var alert: AlertDialog.Builder = AlertDialog.Builder(context)
    alert.setMessage(message)
    alert.setTitle(title)
    alert.setPositiveButton(btnTitle, DialogInterface.OnClickListener { dialog, which ->
        alert.setCancelable(true)
    })
    alert.show()
}

fun getAlertDialog (context: Context, title:String, message:String) : AlertDialog.Builder{
    var alert: AlertDialog.Builder = AlertDialog.Builder(context)
    alert.setMessage(message)
    alert.setTitle(title)
    return alert
}


// progress dialog
 fun startLoading(context: Context, mes:String){
    Log.i("response", "start loading function")
    val builder = android.app.AlertDialog.Builder(context)
    val dialogeView = LayoutInflater.from(context).inflate(R.layout.progress_dialouge,null)
    val message = dialogeView.findViewById<TextView>(R.id.loadingmessage)
    message.text = mes
    builder.setView(dialogeView)
    builder.setCancelable(false)
    var dialoge: android.app.AlertDialog = builder.create()
    dialoge.show()
}

 fun endLoading(dialoge : android.app.AlertDialog?){
    Log.i("response", "end loading function")
    dialoge?.dismiss()
}