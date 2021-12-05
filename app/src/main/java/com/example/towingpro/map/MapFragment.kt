package com.example.towingpro.map

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.location.Address
import android.location.Geocoder
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.os.Looper
import android.provider.Settings
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.core.app.ActivityCompat
import androidx.fragment.app.FragmentActivity
import com.example.towingpro.R
import com.example.towingpro.databinding.FragmentMapBinding
import com.google.android.gms.location.*
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.gms.tasks.Task
import java.io.IOException
import java.util.*

class MapActivity : FragmentActivity(), OnMapReadyCallback {

    private var binding: FragmentMapBinding? = null
    lateinit var fusedLocationClient: FusedLocationProviderClient
    private var googleMap: GoogleMap? = null
    var geocoder: Geocoder? = null
    lateinit var addresses: MutableList<Address>
    private val mLocationCallback: LocationCallback = object : LocationCallback() {
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        override fun onLocationResult(locationResult: LocationResult) {
            val mLastLocation = locationResult.lastLocation
            getAddress(mLastLocation)
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding= FragmentMapBinding.inflate(layoutInflater)
        setContentView(binding!!.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment!!.getMapAsync(this)
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)
        lastLocation

        binding!!.confirmAddress.setOnClickListener { finish() }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    override fun onMapReady(map: GoogleMap) {
        googleMap = map
//         googleMap.setMyLocationEnabled(true);
        googleMap!!.uiSettings.isMyLocationButtonEnabled = true
        googleMap!!.uiSettings.isZoomControlsEnabled = true

        googleMap!!.setOnMapClickListener { latLng: LatLng ->
            googleMap!!.clear()
            googleMap!!.addMarker(
                MarkerOptions()
                    .position(latLng)
            )

            val location = Location("a")
            location.latitude = latLng.latitude
            location.longitude = latLng.longitude
            getAddress(location)
        }
        //        fusedLocationClient.getLastLocation()
//                .addOnSuccessListener(this, location -> {
//                    googleMap.animateCamera(CameraUpdateFactory
//                            .newLatLngZoom((new LatLng(location.getLatitude(),
//                                    location.getLongitude())), 15));
//                });

        // Add a marker in Sydney and move the camera
//        LatLng sydney = new LatLng(-34, 151);
//        googleMap.addMarker(new MarkerOptions().position(sydney).title("Marker in Sydney"));
//        googleMap.moveCamera(CameraUpdateFactory.newLatLng(sydney));
    }

    @get:SuppressLint("MissingPermission")
    private val lastLocation : Location?
        @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
        get() {
            if (checkPermissions()) {
                if (isLocationEnabled) {
                    fusedLocationClient.lastLocation.addOnCompleteListener { task: Task<Location?> ->
                        val location = task.result
                        if (location == null) {
                            requestNewLocationData()
                        } else {
                            googleMap?.animateCamera(
                                CameraUpdateFactory
                                    .newLatLngZoom(
                                        LatLng(
                                            location.latitude,
                                            location.longitude
                                        ), 15f
                                    )
                            )
                            googleMap?.addMarker(
                                MarkerOptions()
                                    .position(
                                        LatLng(
                                            location.latitude,
                                            location.longitude
                                        )
                                    )

                            )
                            getAddress(location)
                        }
                    }
                } else {
                    Toast.makeText(this, "Turn on location", Toast.LENGTH_LONG).show()
                    val intent = Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS)
                    startActivity(intent)
                }
            } else {
                requestPermissions()
            }
            return null
        }

    @SuppressLint("MissingPermission")
    private fun requestNewLocationData() {
        val mLocationRequest = LocationRequest()
        mLocationRequest.priority = LocationRequest.PRIORITY_HIGH_ACCURACY
        mLocationRequest.interval = 0
        mLocationRequest.fastestInterval = 0
        mLocationRequest.numUpdates = 1

        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this)

        fusedLocationClient.requestLocationUpdates(
            mLocationRequest, mLocationCallback,
            Looper.myLooper()
        )
    }

    public override fun onResume() {
        super.onResume()
        if (checkPermissions()) {
            lastLocation
        }
    }

    private fun checkPermissions(): Boolean {
        return ActivityCompat.checkSelfPermission(
            this,
            Manifest.permission.ACCESS_COARSE_LOCATION
        ) == PackageManager.PERMISSION_GRANTED &&
                ActivityCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
    }

    private fun requestPermissions() {
        ActivityCompat.requestPermissions(
            this, arrayOf(
                Manifest.permission.ACCESS_COARSE_LOCATION,
                Manifest.permission.ACCESS_FINE_LOCATION
            ),
            1
        )
    }

    private val isLocationEnabled: Boolean
        get() {
            val locationManager = getSystemService(LOCATION_SERVICE) as LocationManager
            return (locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
                    || locationManager.isProviderEnabled(
                LocationManager.NETWORK_PROVIDER
            ))
        }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 1) {
            if (grantResults.size > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // Granted. Start getting the location information
            }
        }
    }

    @RequiresApi(Build.VERSION_CODES.LOLLIPOP)
    private fun getAddress(location: Location) {
        try {
            geocoder = Geocoder(this, Locale.forLanguageTag("en"))
            addresses = geocoder!!.getFromLocation(
                location.latitude,
                location.longitude, 1
            )

            val address = addresses.get(0).getAddressLine(0)
            val city = addresses.get(0).locality
            val state = addresses.get(0).subAdminArea
            val country = addresses.get(0).countryName
            val stringTokenizer = StringTokenizer(address, ",")
            var street = ""
            if (stringTokenizer.hasMoreTokens()) {
                street = stringTokenizer.nextToken().trim { it <= ' ' }
            }

            binding?.tvAddress?.text ?: address!!

            val i = Intent()
            i.putExtra("lat" , location.latitude.toString())
            i.putExtra("lng" , location.longitude.toString())
            i.putExtra("address" , address)
//            i.putExtra("street", street)
            setResult(RESULT_OK , i )

        } catch (e: IOException) {
            e.printStackTrace()
        }
    }

    override fun onPause() {
        super.onPause()
        stopLocationUpdates()
    }

    private fun stopLocationUpdates() {
        fusedLocationClient.removeLocationUpdates(mLocationCallback)
    }

    override fun onDestroy() {
        super.onDestroy()
        binding = null
    }
}