package net.basicmodel

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.common.ConnectionResult
import com.google.android.gms.common.GooglePlayServicesUtil
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.MapsInitializer
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() ,OnMapReadyCallback{
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        mapview.onCreate(savedInstanceState)
        mapview.onResume()
        MapsInitializer.initialize(this)

        val errorCode = GooglePlayServicesUtil.isGooglePlayServicesAvailable(this)
        if (ConnectionResult.SUCCESS != errorCode){
            GooglePlayServicesUtil.getErrorDialog(errorCode, this, 0).show()
        }else{
            mapview.getMapAsync(this)
        }

    }

    override fun onMapReady(googleMap: GoogleMap?) {
        val lat = 40.73
        val lng = -73.99
        val appointLoc = LatLng(lat, lng)
        // 移动地图到指定经度的位置
        // 移动地图到指定经度的位置
        googleMap?.moveCamera(CameraUpdateFactory.newLatLng(appointLoc))

        //添加标记到指定经纬度

        //添加标记到指定经纬度
        googleMap?.addMarker(
            MarkerOptions().position(LatLng(lat, lng)).title("Marker")
                .icon(BitmapDescriptorFactory.fromResource(R.mipmap.ic_launcher_round))
        )
    }
}