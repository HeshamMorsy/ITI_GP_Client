package app.iti.client.iti_gp_client.utilities

import okhttp3.RequestBody

/**
 * Created by Hazem on 6/13/2018.
 */
object RequestCreation {
    lateinit var title:String
    lateinit var time:String
    var provider_id:Int? = null
    lateinit var images:ArrayList<RequestBody>
    var weight:Int? = null
    lateinit var payment_method:String
    var src_latitude:Double? = null
    var src_longitude:Double? = null
    var dest_latitude:Double? = null
    var dest_longitude:Double? =null
}