package app.iti.client.iti_gp_client.utilities.pushnotification

import com.google.firebase.iid.FirebaseInstanceId
import com.google.firebase.iid.FirebaseInstanceIdService

/**
 * Created by Hazem on 6/16/2018.
 */
class MyFirebaseInstanceIDService: FirebaseInstanceIdService() {
    override fun onTokenRefresh() {
        super.onTokenRefresh()
        FirebaseInstanceId.getInstance().getToken()
    }
}