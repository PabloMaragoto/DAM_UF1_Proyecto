package com.example.uf1_proyecto.viewmodels

import android.util.Log
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

    fun fetchNearbyCinemas(latitude: Number, longitude: Number, radius: Number, apiKey: String){

        val location: String = "$latitude,$longitude"

        viewModelScope.launch(Dispatchers.IO){
            try {
                val response = RetrofitClient.googlePlacesService.getNearbyPlaces(Constants.keycode_cinema, location, radius, Constants.keycode_cinema, apiKey) //ToDO: Verificar el String resources
                withContext(Dispatchers.Main) {
                    if (response.isSuccessful) {
                        val body = response.body()
                        if (body?.results != null) {
                            _placesList.value = body.results.sortedBy { it.placeName }
                            Log.e("PlacesViewModel", "Respuesta exitosa: ${response.code()} - ${response.body()}")

                        } else {
                            Log.e("PlacesViewModel", "Resultados nulos en el cuerpo de la respuesta")
                        }
                    } else {
                        Log.e("PlacesViewModel", "Respuesta no exitosa: ${response.code()} - ${response.message()}")
                    }
                }
            } catch (e: Exception) {
            Log.e("PlacesViewModel", "Error en fetchNearbyCinemas", e)
        }

        }
    }
}