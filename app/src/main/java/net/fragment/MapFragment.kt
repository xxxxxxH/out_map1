package net.fragment

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.Intent
import android.location.Location
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.location.places.ui.PlacePicker
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.GoogleMap.OnMyLocationButtonClickListener
import com.google.android.gms.maps.GoogleMap.OnMyLocationClickListener
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.CameraPosition
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.layout_fragment_map.*
import net.basicmodel.R

/**
 * Copyright (C) 2021,2021/8/10, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MapFragment : Fragment(), OnMapReadyCallback, OnMyLocationButtonClickListener,
    OnMyLocationClickListener {

    var mMap: GoogleMap? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView(savedInstanceState)
    }

    private fun initView(savedInstanceState: Bundle?) {
        mapview.onCreate(savedInstanceState)
        mapview.onResume()
        MapsInitializer.initialize(activity)

        val errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(activity)
        if (ConnectionResult.SUCCESS != errorCode) {
            GooglePlayServicesUtil.getErrorDialog(errorCode, activity, 0).show()
        } else {
            mapview.getMapAsync(this)
        }

        mapNormal.setOnClickListener {
            mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL
        }
        mapHybrid.setOnClickListener {
            mMap!!.mapType = GoogleMap.MAP_TYPE_HYBRID
        }
        mapSat.setOnClickListener {
            mMap!!.mapType = GoogleMap.MAP_TYPE_SATELLITE
        }
        mapTer.setOnClickListener {
            mMap!!.mapType = GoogleMap.MAP_TYPE_TERRAIN
        }
        search.setOnClickListener {
            val builder = PlacePicker.IntentBuilder()
            startActivityForResult(
                builder.build(activity),
                1
            )
        }

    }

    @SuppressLint("MissingPermission")
    override fun onMapReady(googleMap: GoogleMap?) {
        mMap = googleMap
        mMap!!.mapType = GoogleMap.MAP_TYPE_NORMAL

        val lat = 40.73
        val lng = -73.99
        val appointLoc = LatLng(lat, lng)
        // 移动地图到指定经度的位置
        // 移动地图到指定经度的位置
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(appointLoc))

        mMap!!.uiSettings.isZoomControlsEnabled = true
        mMap!!.isMyLocationEnabled = true
        mMap!!.uiSettings.isMyLocationButtonEnabled = true
        mMap!!.setOnMyLocationButtonClickListener(this)
        mMap!!.setOnMyLocationClickListener(this)

        //添加标记到指定经纬度
        googleMap?.addMarker(
            MarkerOptions().position(LatLng(lat, lng)).title("Marker")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
        )
    }

    @SuppressLint("MissingPermission")
    override fun onMyLocationButtonClick(): Boolean {
        val locationManager: LocationManager =
            activity?.getSystemService(Context.LOCATION_SERVICE) as LocationManager
        val location =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER) as Location
        var latitude = location.latitude
        var longitude = location.longitude
        if (latitude != 0.0 && longitude != 0.0) {
            val position = CameraPosition.builder().target(LatLng(latitude, longitude))
                .zoom(15.5f)
                .bearing(0f)
                .tilt(25f)
                .build()
            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000, null)
        } else {
            latitude = 33.9204
            longitude = -117.9465
            val position = CameraPosition.Builder().target(LatLng(latitude, longitude))
                .zoom(15.5f)
                .bearing(0f)
                .tilt(25f)
                .build()
            mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000, null)
        }
        //添加标记到指定经纬度
        mMap?.addMarker(
            MarkerOptions().position(LatLng(latitude, longitude)).title("Marker")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
        )
        return false
    }

    override fun onMyLocationClick(p0: Location) {
        Toast.makeText(activity, "Current location:\n$p0", Toast.LENGTH_LONG).show()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == 1) {
            if (resultCode == Activity.RESULT_OK) {
                val place = PlacePicker.getPlace(data, activity)
                val toastMsg = String.format("Place: %s", place.name)
                Toast.makeText(activity, toastMsg, Toast.LENGTH_LONG).show()
                mMap!!.addMarker(MarkerOptions().position(place.latLng).title("Marker in Sydney"))

                val position = CameraPosition.Builder().target(place.latLng)
                    .zoom(15f)
                    .bearing(0f)
                    .tilt(25f)
                    .build()
                mMap!!.animateCamera(CameraUpdateFactory.newCameraPosition(position), 1000, null)
            }
        }
    }

}