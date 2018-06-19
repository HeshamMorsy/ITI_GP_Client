package app.iti.client.iti_gp_client.utilities.pushnotification

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.RingtoneManager
import android.support.v4.app.NotificationCompat
import android.util.Log
import app.iti.client.iti_gp_client.R
import app.iti.client.iti_gp_client.screens.home.HomeActivity
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage

/**
 * Created by Hazem on 6/16/2018.
 */
class MyFirebaseMessagingService: FirebaseMessagingService() {
    val TAG = "Service"
    override fun onMessageReceived(remoteMessage: RemoteMessage?) {
        // Handle FCM messages here.
        // If the application is in the foreground handle both data and notification messages here.
        // Also if you intend on generating your own notifications as a result of a received FCM
        // message, here is where that should be initiated.

//        remoteMessage
//        Log.d(TAG, "From: " + remoteMessage!!.from)
//        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification!!.body)
//        Log.i(TAG,"Notification Message Body: " + remoteMessage.notification!!.title!!)
        //        Log.d(TAG, "Notification Message Body: " + remoteMessage.data.get("title"))
        Log.d(TAG, "From: " + remoteMessage!!.from)
        Log.d(TAG, "Notification Message Body: " + remoteMessage.notification?.body!!)
        sendNotification(remoteMessage)
        val intent = Intent(this@MyFirebaseMessagingService, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
        intent.putExtra("message", remoteMessage.notification?.body!!)
        startActivity(intent)
    }

    private fun sendNotification(remoteMessage: RemoteMessage) {
        val intent = Intent(this, HomeActivity::class.java)
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
        val pendingIntent = PendingIntent.getActivity(this, 0 /* Request code */, intent,
                PendingIntent.FLAG_ONE_SHOT)
        val defaultSoundUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
        val notificationBuilder = NotificationCompat.Builder(this)
                .setContentText(remoteMessage.notification?.body)
                .setAutoCancel(true)
                .setSmallIcon(R.mipmap.ic_launcher)
                .setSound(defaultSoundUri)
                .setContentIntent(pendingIntent)
        val notificationManager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.notify(0 /* ID of notification */, notificationBuilder.build())
    }

}