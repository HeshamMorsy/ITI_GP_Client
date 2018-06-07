package app.iti.client.iti_gp_client.screens.home

import android.content.Context
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import com.google.android.gms.common.api.GoogleApiClient
import com.google.android.gms.location.places.AutocompleteFilter
import com.google.android.gms.maps.model.LatLngBounds
import android.view.View
import android.widget.Filter
import android.widget.Filterable
import android.widget.Toast
import com.google.android.gms.location.places.Places
import kotlinx.android.synthetic.main.searchview_adapter.view.*
import java.util.ArrayList
import java.util.concurrent.TimeUnit

/**
 * Created by Hazem on 6/4/2018.
 */
class PlacesAdapter(val mContext: Context,val resource:Int,var mGoogleApiClient: GoogleApiClient,var mBounds:LatLngBounds,var mPlaceFilter: AutocompleteFilter?): RecyclerView.Adapter<PlacesAdapter.PredictionHolder>(), Filterable {




    override fun onBindViewHolder(holder: PredictionHolder, position: Int) {
        holder.placeAddress.setText(mResultList!!.get(position).description)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): PredictionHolder {
        val layoutInflater = mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        val convertView = layoutInflater.inflate(resource, parent, false)
        return PredictionHolder(convertView)
    }

    fun getItem(position: Int): PlaceAutocomplete {
        return mResultList!![position]
    }
    override fun getItemCount(): Int {
        return if (mResultList != null)
            mResultList!!.size
        else 0
    }

    private var mResultList: ArrayList<PlaceAutocomplete>? = null


    override fun getFilter(): Filter {

        return object : Filter() {
            override fun performFiltering(constraint: CharSequence?): FilterResults {
                val results = FilterResults()
                // Skip the autocomplete query if no constraints are given.
                if (constraint != null) {
                    // Query the autocomplete API for the (constraint) search string.
                    mResultList = getAutocomplete(constraint)
                    if (mResultList != null) {
                        // The API successfully returned results.
                        results.values = mResultList
                        results.count = mResultList!!.size
                    }
                }
                return results
            }


            override fun publishResults(constraint: CharSequence, results: FilterResults?) {
//                && results.count > 0
                if (results != null ) {
                    Log.i("googleplaces", "inside publish results" + constraint)
                    // The API returned at least one result, update the data.
                    notifyDataSetChanged()
                } else {
                    // The API did not return any results, invalidate the data set.
                    //notifyDataSetInvalidated();
                }
            }
        }
    }

//    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
//        holder.mPrediction.setText(mResultList.get(position).description)
//    }



//    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {


//    }


    private fun getAutocomplete(constraint: CharSequence): ArrayList<PlaceAutocomplete>? {
        Log.i("googleplaces", "inside getautocomplete with constraints: "+constraint)
        if (mGoogleApiClient.isConnected) {
            Log.i("googleplaces", "Starting autocomplete query for: " + constraint)

            // Submit the query to the autocomplete API and retrieve a PendingResult that will
            // contain the results when the query completes.
            val results = Places.GeoDataApi
                    .getAutocompletePredictions(mGoogleApiClient, constraint.toString(),
                            mBounds, mPlaceFilter)

            // This method should have been called off the main UI thread. Block and wait for at most 60s
            // for a result from the API.
            val autocompletePredictions = results
                    .await(60, TimeUnit.SECONDS)

            // Confirm that the query completed successfully, otherwise return null
            val status = autocompletePredictions.status
            if (!status.isSuccess) {
                Toast.makeText(mContext, "Error contacting API: " + status.toString(),
                        Toast.LENGTH_SHORT).show()
                Log.e("", "Error getting autocomplete prediction API call: " + status.toString())
                autocompletePredictions.release()
                return null
            }

            Log.i("googleplaces", "Query completed. Received " + autocompletePredictions.count
                    + " predictions.")

            // Copy the results into our own data structure, because we can't hold onto the buffer.
            // AutocompletePrediction objects encapsulate the API response (place ID and description).

            val iterator = autocompletePredictions.iterator()
            val resultList = ArrayList<PlaceAutocomplete>(autocompletePredictions.count)
            while (iterator.hasNext()) {
                val prediction = iterator.next()
                // Get the details of this prediction and copy it into a new PlaceAutocomplete object.
                resultList.add(PlaceAutocomplete(prediction.placeId.toString(),
                        prediction.getFullText(null).toString()))
                Log.i("googleplaces","placeId: " + prediction.placeId.toString() + "placeTypes" + prediction.placeTypes
            +"full text"+ prediction.getFullText(null).toString())
            }

            // Release the buffer now that all data has been copied.
            autocompletePredictions.release()

            return resultList
        }
        Log.e("", "Google API client is not connected for autocomplete query.")
        return null
    }




    inner class PlaceAutocomplete internal constructor(var placeId: String, var description: String) {

        override fun toString(): String {
            return description.toString()
        }
    }

    class PredictionHolder(view: View) : RecyclerView.ViewHolder(view) {
        val placeAddress = view.address
    }
}
class ViewHolder (view: View) : RecyclerView.ViewHolder(view) {
    // Holds the TextView that will add each animal to
//    val tvAnimalType = view.tv_animal_type
}