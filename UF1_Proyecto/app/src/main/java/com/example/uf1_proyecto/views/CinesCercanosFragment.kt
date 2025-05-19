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
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.uf1_proyecto.R
import com.example.uf1_proyecto.adapters.AdapterCinema
import com.example.uf1_proyecto.adapters.AdapterPelicula
import com.example.uf1_proyecto.databinding.FragmentCinesCercanosBinding
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

    var _binding: FragmentCinesCercanosBinding? = null
    val binding: FragmentCinesCercanosBinding get() = _binding!!

    private val REQUEST_LOCATION_PERMISSION_CODE = 100;

    private lateinit var  fusedLocationClient: FusedLocationProviderClient;
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback
    private lateinit var map: GoogleMap
    private lateinit var placesViewModel: PlacesViewModel
    private lateinit var adapterCinema: AdapterCinema

    private lateinit var placesClient: PlacesClient

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentCinesCercanosBinding.inflate(inflater, container, false)
        val view = binding.root
        setUpRecyclerView()

        placesViewModel = ViewModelProvider(this)[PlacesViewModel::class.java]
        //placesViewModel.fetchNearbyCinemas(42.878661, -8.547374, 2000, getString(R.string.google_maps_key) )

        placesViewModel.placesList.observe(viewLifecycleOwner){
            adapterCinema.cinemasList = it
            adapterCinema.notifyDataSetChanged()
        }

        val mapFragment = childFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?

        mapFragment?.getMapAsync(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(requireActivity())
        createLocationRequest()
        createLocationCallback()

        placesClient = Places.createClient(requireContext())

        binding.btnBuscarCines.setOnClickListener {
            val lastLocation = map.cameraPosition.target
            val lat = lastLocation.latitude
            val lng = lastLocation.longitude

            placesViewModel.fetchNearbyCinemas(lat, lng, 1500, getString(R.string.google_maps_key))
        }

        return view

    }


    private fun setUpRecyclerView(){

        val layoutManager = LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false)

        binding.recyclerCinemasCercanos.layoutManager = layoutManager
        adapterCinema = AdapterCinema(requireContext(), arrayListOf())
        binding.recyclerCinemasCercanos.adapter = adapterCinema

        val decoration = DividerItemDecoration(requireContext(), layoutManager.orientation)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        map = googleMap

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
        placesViewModel.placesList.observe(viewLifecycleOwner) { lista ->
            for (item in lista) {

                map.addMarker(
                    MarkerOptions()
                        .position(LatLng(item.placeLocation.location.latitude.toDouble(),item.placeLocation.location.longitude.toDouble()))
                        .title(item.placeName)
                )
            }
        }
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }


}