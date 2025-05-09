package com.example.uf1_proyecto.views

import android.content.pm.PackageManager
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import android.Manifest
import android.annotation.SuppressLint
import android.os.Looper
import android.util.Log
import androidx.activity.result.contract.ActivityResultContracts
import androidx.lifecycle.ViewModelProvider
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.viewmodels.PlacesViewModel
import com.example.uf1_proyecto.viewmodels.SharedViewModel
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.Granularity
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.PlaceTypes
import com.google.android.libraries.places.api.net.FindCurrentPlaceRequest
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.firebase.ktx.BuildConfig

class CinesCercanosFragment : Fragment(), OnMapReadyCallback {

    private val REQUEST_LOCATION_PERMISSION_CODE = 100;

    val placeFields = listOf(
        Place.Field.NAME,
        Place.Field.ADDRESS,
        Place.Field.LAT_LNG,
        Place.Field.PHOTO_METADATAS
    )

    private lateinit var  fusedLocationClient: FusedLocationProviderClient;
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var map: GoogleMap
    private lateinit var placesViewModel: PlacesViewModel

    private lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_cines_cercanos, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        placesViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        placesViewModel.fetchNearbyCinemas(42.878661, -8.547374, 1500)

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        createLocationRequest()
        createLocationCallback()

        placesClient = Places.createClient(requireContext())

    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap
        //TODO: Quitar el marker innecesario
        map.addMarker(
            MarkerOptions()
                .position(LatLng(40.4165000,  -3.7025600))
                .title("Marker")
        )

        if(!checkPermissionLocationService()){
            requestPermissionLocation()
        }

        startLocationUpdates()
    }

    private fun checkPermissionLocationService(): Boolean{
        return ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_FINE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED || ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissionLocation(){
        ActivityCompat.requestPermissions(requireActivity(),
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            ),
            REQUEST_LOCATION_PERMISSION_CODE
        )
    }

    //TODO: Revisar si solo podemos quitar este SupressLint, utilizando el amnejo automático de permisos y no el manual que creamos
    @SuppressLint("MissingPermission")
    private fun startLocationUpdates() {
        fusedLocationClient.requestLocationUpdates(locationRequest,
            locationCallback,
            Looper.getMainLooper()).addOnSuccessListener { createLocationCallback() }
    }

    private fun createLocationCallback() {
        locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                super.onLocationResult(locationResult)
                locationResult.lastLocation?.let { location ->
                    val userLatLng = LatLng(location.latitude, location.longitude)
                    updateMapLocation(userLatLng)
                }
            }
        }
    }

    private fun createLocationRequest() {
        locationRequest = LocationRequest.create().apply {
            interval = 10000
            fastestInterval = 5000
            priority = Priority.PRIORITY_HIGH_ACCURACY
        }

        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 10000L).apply {
            setMinUpdateDistanceMeters(10F)
            setGranularity(Granularity.GRANULARITY_PERMISSION_LEVEL) //Esto hará que el nivel de detalle se adapte l tipo de permiso de ubicación elegido
            setWaitForAccurateLocation(true)
        }.build()
    }

    private fun updateMapLocation(latLng: LatLng) {
        val cameraUpdate = CameraUpdateFactory.newLatLngZoom(latLng, 15f)
        map.animateCamera(cameraUpdate)

        map.clear()
        //Todo: queremos que haya un marcador en la posición del usuario?
        //Todo: Google maps api, tiene su propia capa de ubicación (MyLocationEnabled), y en otro proyecto ya ponía un marcador propio, a lo mejor sería bueno usarlo
        map.addMarker(
            MarkerOptions()
                .position(latLng)
        )
    }

    //TODO: Revisar Manejo propio de android de los permisos (https://developer.android.com/develop/sensors-and-location/location/permissions/runtime?hl=es-419#kotlin)
    /*private fun updatePosition(){
        val locationPermissionRequest = registerForActivityResult(
            ActivityResultContracts.RequestMultiplePermissions()
        ) { permissions ->
            when {
                permissions.getOrDefault(Manifest.permission.ACCESS_FINE_LOCATION, false) -> {
                    // Precise location access granted.
                }
                permissions.getOrDefault(Manifest.permission.ACCESS_COARSE_LOCATION, false) -> {
                    // Only approximate location access granted.
                }
                else -> {
                    // No location access granted.
                }
            }
        }

        locationPermissionRequest.launch(
            arrayOf(
                Manifest.permission.ACCESS_FINE_LOCATION,
                Manifest.permission.ACCESS_COARSE_LOCATION
            )
        )
    }*/

    //Uso de sdk, no incluye nearby plCES, como  si lo hace la APi
    /*@SuppressLint("MissingPermission")
    private fun fetchNearbyCinemas(latLng: LatLng) {
        val request = FindCurrentPlaceRequest.newInstance(placeFields)

        placesClient.findCurrentPlace(request).addOnSuccessListener { response ->
            for (placeLikelihood in response.placeLikelihoods) {
                val place = placeLikelihood.place

                if (place.types?.contains(PlaceTypes.MOVIE_THEATER) == true) {
                    val name = place.name ?: "Sin nombre"
                    val address = place.address ?: "Dirección no disponible"
                    val latLng = place.latLng


                    latLng?.let { cinemaLatLng ->
                        map.addMarker(
                            MarkerOptions()
                                .position(cinemaLatLng)
                                .title(name)
                                .snippet(address)
                        )
                    }
                }
            }
        }.addOnFailureListener { exception ->
            Log.e("CinesCercanos", "Error: ${exception.message}")
        }
    }*/


}