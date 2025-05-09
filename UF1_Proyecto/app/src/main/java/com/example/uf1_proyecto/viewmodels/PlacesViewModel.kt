package com.example.uf1_proyecto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.models.PlaceDetailsModel
import com.example.uf1_proyecto.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext


class PlacesViewModel : ViewModel() {
    private var _placesList = MutableLiveData<List<PlaceDetailsModel>>()
    val placesList: LiveData<List<PlaceDetailsModel>> = _placesList

    fun fetchNearbyCinemas(latitude: Number, longitude: Number, radius: Number){

        val location: String = "$latitude,$longitude"

        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.googlePlacesService.getNearbyPlaces(Constants.keycode_cinema, location, radius, Constants.keycode_cinema, R.string.google_maps_key.toString()) //ToDO: Verificar el String resources
            withContext(Dispatchers.Main){
                _placesList.value = response.body()!!.results.sortedBy { it.placeName }
            }

        }
    }
}