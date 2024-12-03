package com.example.uf1_proyecto.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.uf1_proyecto.core.Constants
import com.example.uf1_proyecto.models.PeliculaModel
import com.example.uf1_proyecto.network.RetrofitClient
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class PeliculasViewModel : ViewModel() {
    private var peliculaList = MutableLiveData<List<PeliculaModel>>()
    val peliculasList: LiveData<List<PeliculaModel>> = peliculaList

    private var peliculaListPopular = MutableLiveData<List<PeliculaModel>>()
    val peliculasListPopular: LiveData<List<PeliculaModel>> = peliculaListPopular

    private var peliculaListTopRated = MutableLiveData<List<PeliculaModel>>()
    val peliculasListTopRated: LiveData<List<PeliculaModel>> = peliculaListTopRated

    private var peliculaListUpcoming = MutableLiveData<List<PeliculaModel>>()
    val peliculasListUpcoming: LiveData<List<PeliculaModel>> = peliculaListUpcoming

    fun getNowPlaying() {
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.webService.getNowPlaying(Constants.API_KEY)
            withContext(Dispatchers.Main){
                peliculaList.value = response.body()!!.results.sortedBy {it.title}
            }
        }
    }

    fun getPopular() {
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.webService.getPopular(Constants.API_KEY)
            withContext(Dispatchers.Main){
                peliculaListPopular.value = response.body()!!.results.sortedByDescending {(it.popularidad).toFloat()}
            }
        }
    }

    fun getTopRated() {
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.webService.getTopRated(Constants.API_KEY)
            withContext(Dispatchers.Main){
                peliculaListTopRated.value = response.body()!!.results.sortedByDescending {(it.puntuacion).toFloat()}
            }
        }
    }

    fun getUpcoming() {
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.webService.getUpcoming(Constants.API_KEY)
            withContext(Dispatchers.Main){
                peliculaListUpcoming.value = response.body()!!.results.sortedBy {it.title}
            }
        }
    }

    fun getParasite(){
        viewModelScope.launch(Dispatchers.IO){
            val response = RetrofitClient.webService.getParasite(Constants.API_KEY, "Parasite")
            withContext(Dispatchers.Main){
                peliculaList.value = response.body()!!.results.sortedBy { it.popularidad.toFloat() }
            }
        }
    }
}