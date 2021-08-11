package net.fragment

import android.annotation.SuppressLint
import android.content.Context
import android.location.LocationManager
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.google.android.gms.maps.*
import com.google.android.gms.maps.GoogleMap.OnMarkerDragListener
import com.google.android.gms.maps.StreetViewPanorama.OnStreetViewPanoramaChangeListener
import com.google.android.gms.maps.model.*
import net.basicmodel.R

/**
 * Copyright (C) 2021,2021/8/10, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class StreetViewFragment:Fragment() , OnMarkerDragListener, OnStreetViewPanoramaChangeListener{

     val MARKER_POSITION_KEY = "MarkerPosition"

    // George St, Sydney
     val SYDNEY = LatLng(-33.87365, 151.20689)

     var mStreetViewPanorama: StreetViewPanorama? = null

     var mMarker: Marker? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_streetview,container,false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initView()
    }

    private fun initView(){
        val markerPosition: LatLng
        val locationManager =
            requireActivity().getSystemService(Context.LOCATION_SERVICE) as LocationManager
        @SuppressLint("MissingPermission") val location =
            locationManager.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
        if (location != null) {
            markerPosition = LatLng(location.latitude, location.longitude)
        } else {
            markerPosition = this.SYDNEY
        }

        val streeF = activity?.supportFragmentManager?.findFragmentById(R.id.streetviewpanorama) as SupportStreetViewPanoramaFragment?

        streeF?.getStreetViewPanoramaAsync {
            OnStreetViewPanoramaReadyCallback { p0 ->
                mStreetViewPanorama = p0
                mStreetViewPanorama!!.setOnStreetViewPanoramaChangeListener(
                    this@StreetViewFragment
                )
                mStreetViewPanorama!!.setPosition(markerPosition)
            }
        }

        val mapF = activity?.supportFragmentManager?.findFragmentById(R.id.mapstreet) as SupportMapFragment?
        mapF?.getMapAsync { map ->
            map.setOnMarkerDragListener(this@StreetViewFragment)
            // Creates a draggable marker. Long press to drag.
            map.moveCamera(CameraUpdateFactory.newLatLng(markerPosition))
            mMarker = map.addMarker(
                MarkerOptions()
                    .position(markerPosition)
                    .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
                    .draggable(true)
            )
            /**
             * 地图点击事件
             */
            /**
             * 地图点击事件
             */
            map.setOnMapClickListener { latLng ->
                map.clear()
                mMarker = map.addMarker(
                    MarkerOptions()
                        .position(latLng)
                        .icon(BitmapDescriptorFactory.fromResource(R.mipmap.location))
                        .draggable(true)
                )
                mStreetViewPanorama!!.setPosition(latLng)
            }
        }
    }

    override fun onMarkerDragStart(p0: Marker?) {

    }

    override fun onMarkerDrag(p0: Marker?) {

    }

    override fun onMarkerDragEnd(p0: Marker?) {
        mStreetViewPanorama!!.setPosition(p0?.position, 150)
    }

    override fun onStreetViewPanoramaChange(p0: StreetViewPanoramaLocation?) {
        if (p0 != null) {
            mMarker!!.setPosition(p0.position)
        }
    }
}