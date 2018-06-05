package app.iti.client.iti_gp_client.utilities

import android.content.Context
import android.content.DialogInterface
import android.support.v7.app.AlertDialog

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