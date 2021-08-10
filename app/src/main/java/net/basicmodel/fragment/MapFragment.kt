package net.basicmodel.fragment

import androidx.fragment.app.Fragment
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import net.basicmodel.R

/**
 * Copyright (C) 2021,2021/8/10, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class MapFragment : Fragment() {

//    mapview.onCreate(savedInstanceState)
//    mapview.onResume()
//    MapsInitializer.initialize(this)
//
//    val errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
//    if (ConnectionResult.SUCCESS != errorCode){
//        GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show()
//    }else{
//        mapview.getMapAsync(this)
//    }
//
//}
//
//override fun onMapReady(googleMap: GoogleMap?) {
//    val lat = 40.73
//    val lng = -73.99
//    val appointLoc = LatLng(lat, lng)
//    // 移动地图到指定经度的位置
//    // 移动地图到指定经度的位置
//    googleMap?.moveCamera(CameraUpdateFactory.newLatLng(appointLoc))
//
//    //添加标记到指定经纬度
//
//    //添加标记到指定经纬度
//    googleMap?.addMarker(
//        MarkerOptions().position(LatLng(lat, lng)).title("Marker")
//            .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
//    )
//}
}