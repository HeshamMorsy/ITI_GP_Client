package app.iti.client.iti_gp_client.services

import app.iti.client.iti_gp_client.entities.AboutResponse
import app.iti.client.iti_gp_client.utilities.RetrofitCreation
import io.reactivex.Observable
import retrofit2.http.GET


interface AboutService {
    @GET("")
    fun getAbout(): Observable<AboutResponse>
}

fun createAboutRequest():AboutService{
    return RetrofitCreation.createRetrofit().create(AboutService::class.java)
}
