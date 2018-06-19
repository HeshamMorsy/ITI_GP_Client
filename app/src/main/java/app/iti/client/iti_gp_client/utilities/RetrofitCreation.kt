package app.iti.client.iti_gp_client.utilities

import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory

/**
 * Created by Hazem on 5/30/2018.
 */
class RetrofitCreation {

    companion object {
        private val baseUrl = "https://driveo.herokuapp.com/api/v1/"
        private var retrofit:Retrofit? = null
        fun createRetrofit(): Retrofit {
            if(retrofit == null){
                retrofit = Retrofit.Builder()
                        .addCallAdapterFactory(
                                RxJava2CallAdapterFactory.create())
                        .addConverterFactory(
                                GsonConverterFactory.create())
                        .baseUrl(baseUrl)
                        .build()
            }

            return retrofit!!
        }



    }
}