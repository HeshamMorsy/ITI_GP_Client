package app.iti.client.iti_gp_client.screens.home

import android.util.Log
import app.iti.client.iti_gp_client.contracts.HomeInt
import app.iti.client.iti_gp_client.entities.ProvidersRequest
import app.iti.client.iti_gp_client.services.ProvidersService
import app.iti.client.iti_gp_client.services.createProvidersRequest
import app.iti.client.iti_gp_client.utilities.PreferenceHelper
import app.iti.client.iti_gp_client.utilities.PreferenceHelper.get
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers

/**
 * Created by Hazem on 6/13/2018.
 */
class HomeModel(var presenter: HomeInt.Presenter):HomeInt.Model {
    override fun getProviders(auth:String){
        var providerRequest = createProvidersRequest()

        //hard codded auth
        //"eyJhbGciOiJIUzI1NiJ9.eyJ1c2VyX2lkIjo0LCJleHAiOjE1MzE1MTM0MTd9.U2ImzhS9kCVgoCYkpU8PMW8fEggxYVofwPIuvz0MDUM"
        providerRequest.getProviders(auth)
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeOn(Schedulers.io())
                .subscribe(this::handleProvidersResponse, this::handleProvidersError)
    }
    fun handleProvidersResponse(response:ProvidersRequest){
        Log.i("response",response.toString())
        presenter.handleProviders(response)
    }
    fun handleProvidersError(error: Throwable){
        Log.i("response",error.toString())
    }
}