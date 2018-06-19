package app.iti.client.iti_gp_client.utilities

/**
 * Created by Hesham on 6/5/2018.
 */
 class Constants {
    companion object {
        // request codes for read, write external data and accessing camera permissions
        val READ_GALARY_REQUEST: Int = 1
        val WRITE_GALARY_REQUEST: Int = 2
        val CAMERA_REQUEST: Int = 3

        // shared preferences constants
        val EMAIL_SHARED_PREFERENCE = "email"
        val NAME_SHARED_PREFERENCE = "name"
        val PHONE_SHARED_PREFERENCE = "phone"
        val AVATAR_SHARED_PREFERENCE = "avatar"
        val TOKEN_SHARED_PREFERENCE = "auth_token"
        val LOGIN_STATUS_SHARED_PREFERENCE = "login_status"
        val CURRENT_LANGUAGE_SHARED_PREFERENCE = "current_language"

        // request codes in drop off package
        val PLACE_PICKER_REQUEST = 1
        val PLACE_DISTINATION_REQUEST = 2

        //

    }
}