package net.basicmodel

import android.Manifest
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.layout_bottom.*
import net.fragment.InterActiveFragment
import net.fragment.MapFragment
import net.fragment.NearbyFragment
import net.fragment.StreetViewFragment


class MainActivity : AppCompatActivity() {

    var mapFragment: MapFragment? = null
    var nearbyFragment: NearbyFragment? = null
    var streetViewFragment: StreetViewFragment? = null
    var interActiveFragment: InterActiveFragment? = null

    private val permissions = arrayOf(
        Manifest.permission.ACCESS_COARSE_LOCATION,
        Manifest.permission.ACCESS_FINE_LOCATION
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        requestPermissions()
        initView()
    }

    private fun initView() {
        map.setOnClickListener {
            showPosition(0)
        }
        near.setOnClickListener {
            showPosition(1)
        }
        street.setOnClickListener {
            showPosition(2)
        }
        interactive.setOnClickListener {
            showPosition(3)
        }
    }

    private fun showPosition(position: Int) {
        val fm = supportFragmentManager
        val ft = fm.beginTransaction()
        hideAll(ft)
        if (position == 0) {
            mapFragment = fm.findFragmentByTag("map") as MapFragment?
            if (mapFragment == null) {
                mapFragment = MapFragment()
                ft.add(R.id.content, mapFragment!!, "map")
            } else {
                ft.show(mapFragment!!)
            }
        }

        if (position == 1) {
            nearbyFragment = fm.findFragmentByTag("nearby") as NearbyFragment?
            if (nearbyFragment == null) {
                nearbyFragment = NearbyFragment()
                ft.add(R.id.content, nearbyFragment!!, "nearby")
            } else {
                ft.show(nearbyFragment!!)
            }
        }

        if (position == 2) {
            streetViewFragment = fm.findFragmentByTag("streetview") as StreetViewFragment?
            if (streetViewFragment == null) {
                streetViewFragment = StreetViewFragment()
                ft.add(R.id.content, streetViewFragment!!, "streetview")
            } else {
                ft.show(streetViewFragment!!)
            }
        }

        if (position == 3) {
            interActiveFragment = fm.findFragmentByTag("interactive") as InterActiveFragment?
            if (interActiveFragment == null) {
                interActiveFragment = InterActiveFragment()
                ft.add(R.id.content, interActiveFragment!!, "interactive")
            } else {
                ft.show(interActiveFragment!!)
            }
        }
        ft.commit()
    }

    fun hideAll(ft: FragmentTransaction) {
        if (mapFragment != null) {
            ft.hide(mapFragment!!)
        }
        if (nearbyFragment != null) {
            ft.hide(nearbyFragment!!)
        }
        if (streetViewFragment != null) {
            ft.hide(streetViewFragment!!)
        }
        if (interActiveFragment != null) {
            ft.hide(interActiveFragment!!)
        }
    }

    fun requestPermissions() {
        if (checkPermission(permissions[0]) && checkPermission(permissions[1])) {
            showPosition(0)
        } else {
            ActivityCompat.requestPermissions(this, permissions, 321)
        }
    }

    private fun checkPermission(per: String): Boolean {
        return ContextCompat.checkSelfPermission(this, per) == PackageManager.PERMISSION_GRANTED
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == 321) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                if (grantResults[0] != PackageManager.PERMISSION_GRANTED) {
                } else {
                    showPosition(0)
                    Log.i("xxxxxxH", "获取权限成功")
                }
            }
        }
    }
}