package com.genzen.zenspire.ui.clinic

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Location
import android.net.Uri
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.genzen.zenspire.BuildConfig
import com.genzen.zenspire.R
import com.genzen.zenspire.databinding.ActivityClinicMapsBinding
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.libraries.places.api.Places
import com.google.android.libraries.places.api.model.Place
import com.google.android.libraries.places.api.model.RectangularBounds
import com.google.android.libraries.places.api.net.FetchPlaceRequest
import com.google.android.libraries.places.api.net.FetchPlaceResponse
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsRequest
import com.google.android.libraries.places.api.net.FindAutocompletePredictionsResponse
import com.google.android.libraries.places.api.net.PlacesClient
import com.google.android.material.dialog.MaterialAlertDialogBuilder

class ClinicMapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var googleMap: GoogleMap
    private lateinit var binding: ActivityClinicMapsBinding
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var placesClient: PlacesClient

    companion object {
        private const val LOCATION_PERMISSION_REQUEST_CODE = 1
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityClinicMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, 0)
            insets
        }

        // Obtain the SupportMapFragment and get notified when the map is ready to be used.
        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)

        Places.initialize(applicationContext, BuildConfig.MAPS_API_KEY)
        placesClient = Places.createClient(this)

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        with(binding) {
            topAppBar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    override fun onMapReady(googleMap: GoogleMap) {
        this.googleMap = googleMap
        getLocationPermission()
    }

    @SuppressLint("MissingPermission")
    private fun getUserLocation() {
        fusedLocationClient.lastLocation.addOnSuccessListener { location ->
            googleMap.clear()
            location?.let {
                val userLatLng = LatLng(it.latitude, it.longitude)
                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
            }
        }
        searchNearbyPlaces("psikolog")
        searchNearbyPlaces("klinik psikolog")
        searchNearbyPlaces("praktik psikolog")
        searchNearbyPlaces("konsultasi psikolog")
    }

    @SuppressLint("MissingPermission")
    private fun searchNearbyPlaces(keyword: String) {
        fusedLocationClient.lastLocation.addOnSuccessListener { location: Location? ->
            location?.let {
                val userLatLng = LatLng(it.latitude, it.longitude)
                val request = FindAutocompletePredictionsRequest.builder()
                    .setQuery(keyword)
                    .setLocationBias(RectangularBounds.newInstance(
                        LatLng(userLatLng.latitude - 0.05, userLatLng.longitude - 0.05),
                        LatLng(userLatLng.latitude + 0.05, userLatLng.longitude + 0.05)))
                    .build()

                placesClient.findAutocompletePredictions(request).addOnSuccessListener { response: FindAutocompletePredictionsResponse ->
                    val unwantedKeywords = listOf("fakultas")
                    val filteredPredictions = response.autocompletePredictions.filter { prediction ->
                        unwantedKeywords.none { keyword -> prediction.getFullText(null).toString().contains(keyword, ignoreCase = true) }
                    }

                    for (prediction in filteredPredictions) {
                        val placeId = prediction.placeId
                        placesClient.fetchPlace(FetchPlaceRequest.builder(placeId, listOf(Place.Field.LAT_LNG, Place.Field.NAME)).build()).addOnSuccessListener { fetchPlaceResponse: FetchPlaceResponse ->
                            val place = fetchPlaceResponse.place
                            place.latLng?.let { latLng: LatLng ->
                                googleMap.addMarker(MarkerOptions().position(latLng).title(place.name))
                                googleMap.moveCamera(CameraUpdateFactory.newLatLngZoom(userLatLng, 14f))
                            }
                        }.addOnFailureListener { exception: Exception ->
                            Log.e("debug", "Place not found: ${exception.message}")
                        }
                    }
                }.addOnFailureListener { exception: Exception ->
                    Log.e("debug", "Error getting autocomplete predictions: ${exception.message}")
                }
            }
        }
    }

    private fun getLocationPermission() {
        if (ContextCompat.checkSelfPermission(
                this.applicationContext,
                Manifest.permission.ACCESS_FINE_LOCATION
            ) == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            getUserLocation()
        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionRationale()
//            } else {
//                showSettingsDialog()
//            }
        }
    }

    @SuppressLint("MissingPermission")
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<out String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == LOCATION_PERMISSION_REQUEST_CODE && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
            googleMap.isMyLocationEnabled = true
            getUserLocation()
        } else {
//            if (ActivityCompat.shouldShowRequestPermissionRationale(this, Manifest.permission.ACCESS_FINE_LOCATION)) {
                showPermissionRationale()
//            } else {
//                showSettingsDialog()
//            }
        }
    }

    private fun showPermissionRationale() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Izinkan Akses Lokasi")
            .setMessage("Untuk menemukan klinik psikolog terdekat, izinkan Zensipire untuk mengakses lokasi Anda.")
            .setPositiveButton("Lanjutkan") { _, _ ->
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    LOCATION_PERMISSION_REQUEST_CODE
                )
            }
            .setNegativeButton("Nanti") { _, _ ->
                finish()
            }
            .show()
    }

    private fun showSettingsDialog() {
        MaterialAlertDialogBuilder(this)
            .setTitle("Butuh Izin lokasi")
            .setMessage("Fitur ini membutuhkan akses lokasi. Aktifkan izin lokasi pada pengaturan.")
            .setPositiveButton("Ke Pengaturan") { _, _ ->
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS)
                val uri: Uri = Uri.fromParts("package", packageName, null)
                intent.data = uri
                startActivity(intent)
                finish()
            }
            .setNegativeButton("Nanti") { _, _ ->
                finish()
            }
            .show()
    }
}