package app.iti.client.iti_gp_client.utilities

import okhttp3.RequestBody

/**
 * Created by Hazem on 6/13/2018.
 */
object RequestCreation {
    var title:String? = null
    var time:String? = null
    var provider_id:Int? = null
    lateinit var images:ArrayList<RequestBody>
    var weight:Int? = null
    lateinit var payment_method:String
    var src_latitude:Double? = null
    var src_longitude:Double? = null
    var src_address:String? = null
    var dest_latitude:Double? = null
    var dest_longitude:Double? = null
    var dest_address:String? = null
    override fun toString(): String {
        return "request title: " + title +
                "request time: " + time +
                "request provider_id: " + provider_id +
                "src lat: " + src_latitude +
                "src long " + src_longitude +
                "src address " + src_address +
                "dist lat: " + dest_latitude +
                "dest long " + dest_longitude +
                "dest address " + dest_address


    }
}