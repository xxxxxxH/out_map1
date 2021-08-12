package net.basicmodel

import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.RelativeLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnStreetViewPanoramaReadyCallback
import com.google.android.gms.maps.StreetViewPanorama
import com.google.android.gms.maps.SupportStreetViewPanoramaFragment
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.StreetViewPanoramaCamera
import com.google.android.gms.maps.model.StreetViewPanoramaLocation
import com.google.android.gms.maps.model.StreetViewPanoramaOrientation
import kotlinx.android.synthetic.main.layout_activity_interactive.*
import kotlinx.android.synthetic.main.layout_fragment_interactive.*
import net.adapter.PlaceAdapter
import net.entiy.BigPlaceEntity
import net.entiy.SmallPlaceEntity
import net.utils.LoadingDialog
import net.utils.NetUtils
import net.utils.OnItemClickListener
import org.json.JSONException
import org.json.JSONObject
import java.util.*

class InterActiveActivity : AppCompatActivity(), GoogleMap.OnMarkerDragListener,
    StreetViewPanorama.OnStreetViewPanoramaChangeListener, OnItemClickListener {

    var mStreetViewPanorama: StreetViewPanorama? = null
    var bigPlace: BigPlaceEntity? = null
    var timer: Timer? = null
    var bottomLayoutInitMarginTo = 0
    var smallPlaceList: ArrayList<SmallPlaceEntity> = ArrayList<SmallPlaceEntity>()
    var dialog: LoadingDialog? = null
    var isTop = false
    var adapter: PlaceAdapter? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.layout_activity_interactive)
        val intent = intent
        bigPlace = intent.getSerializableExtra("data") as BigPlaceEntity
        initMap(savedInstanceState)
        initSmallPlaces()
        onclick()
    }

    private fun initMap(savedInstanceState: Bundle?) {
        val streetF =
            supportFragmentManager.findFragmentById(R.id.mapFragment) as SupportStreetViewPanoramaFragment?
        streetF?.getStreetViewPanoramaAsync { p0 ->
            mStreetViewPanorama = p0
            mStreetViewPanorama!!.setOnStreetViewPanoramaChangeListener(this)
            if (savedInstanceState == null) {
                mStreetViewPanorama?.setPosition(bigPlace?.panoid)
            }
            rotateMap()
            mStreetViewPanorama!!.setOnStreetViewPanoramaClickListener { streetViewPanoramaOrientation: StreetViewPanoramaOrientation? ->
                if (timer != null) {
                    timer!!.cancel()
                }
            }
            bottomLayoutInitMarginTo =
                (gridLayout.layoutParams as RelativeLayout.LayoutParams).topMargin

            val dm = resources.displayMetrics
            val screenHeight = dm.heightPixels
            var params = gridLayout.layoutParams as RelativeLayout.LayoutParams
            params.height = screenHeight - 60
            gridLayout.layoutParams = params
            gridLayout.postInvalidate()

            params = streetF.view?.layoutParams as RelativeLayout.LayoutParams
            params.height = screenHeight - 240
            streetF.view?.layoutParams = params
            streetF.view?.postInvalidate()
        }
    }

    fun rotateMap() {
        if (timer != null) {
            timer!!.cancel()
        }
        val timerTask: TimerTask = object : TimerTask() {
            override fun run() {
                runOnUiThread {
                    val duration: Long = 5000
                    val camera = StreetViewPanoramaCamera.Builder()
                        .zoom(mStreetViewPanorama!!.panoramaCamera.zoom)
                        .tilt(mStreetViewPanorama!!.panoramaCamera.tilt)
                        .bearing(mStreetViewPanorama!!.panoramaCamera.bearing + 60)
                        .build()
                    mStreetViewPanorama!!.animateTo(camera, duration)
                }
            }
        }
        timer = Timer()
        timer!!.schedule(timerTask, 1000, 5000)
    }

    private fun initSmallPlaces() {
        showDlg()
        Thread {
            val data: String =
                NetUtils.getUrlContent("https://www.google.com/streetview/feed/gallery/collection/" + bigPlace!!.key.toString() + ".json")
            runOnUiThread {
                try {
                    val jsonObject = JSONObject(data)
                    val iterator = jsonObject.keys()
                    while (iterator.hasNext()) {
                        val key = iterator.next()
                        val jsonObject1 = jsonObject.getJSONObject(key)
                        val smallPlace = SmallPlaceEntity()
                        smallPlace.title = jsonObject1.getString("title")
                        smallPlace.lat = jsonObject1.getDouble("lat")
                        smallPlace.lng = jsonObject1.getDouble("lng")
                        smallPlace.pannoId = jsonObject1.getString("panoid")
                        if (bigPlace!!.isFife) {
                            smallPlace.imageUrl =
                                "https://lh4.googleusercontent.com/" + smallPlace.pannoId
                                    .toString() + "/w400-h300-fo90-ya0-pi0/"
                        } else {
                            smallPlace.imageUrl =
                                "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + jsonObject1
                                    .getString("panoid")
                        }
                        smallPlaceList.add(smallPlace)
                    }
                    setAdapter(smallPlaceList)
                } catch (e: JSONException) {
                    e.printStackTrace()
                } finally {
                    closeDlg()
                }
            }
        }.start()
    }

    private fun setAdapter(data: ArrayList<SmallPlaceEntity>) {
        adapter = PlaceAdapter(null, data, this, this)
        recyclerActive.layoutManager = LinearLayoutManager(this)
        recyclerActive.adapter = adapter
        adapter!!.setListener(this)
    }

    private fun onclick() {
        toggleBtn.setOnClickListener {
            if (isTop) {
                val params = gridLayout.layoutParams as RelativeLayout.LayoutParams
                params.topMargin = bottomLayoutInitMarginTo
                gridLayout.layoutParams = params
                toggleBtn.setImageResource(R.mipmap.up)
                gridLayout.invalidate()
            } else {
                val params = gridLayout.layoutParams as RelativeLayout.LayoutParams
                params.topMargin = 60
                toggleBtn.setImageResource(R.mipmap.down)
                gridLayout.layoutParams = params
                gridLayout.invalidate()
            }

            isTop = !isTop
        }

    }

    private fun setMargin(){
        if (isTop) {
            val params = gridLayout.layoutParams as RelativeLayout.LayoutParams
            params.topMargin = bottomLayoutInitMarginTo
            gridLayout.layoutParams = params
            toggleBtn.setImageResource(R.mipmap.up)
            gridLayout.invalidate()
        } else {
            val params = gridLayout.layoutParams as RelativeLayout.LayoutParams
            params.topMargin = 60
            toggleBtn.setImageResource(R.mipmap.down)
            gridLayout.layoutParams = params
            gridLayout.invalidate()
        }

        isTop = !isTop
    }

    private fun showDlg() {
        if (dialog == null) {
            dialog = LoadingDialog(this)
        }
        dialog!!.show()
    }

    private fun closeDlg() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
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

    }

    override fun onItemClick(view: View?, position: Int, flag: String?) {
        if (TextUtils.equals(flag, "small")) {
            val entity = smallPlaceList[position]
            if (mStreetViewPanorama == null) {
                Toast.makeText(
                    this,
                    "Street view init failed",
                    Toast.LENGTH_SHORT
                ).show()
                return
            }
            if (bigPlace!!.isFife) {
                mStreetViewPanorama!!.setPosition("F:" + entity.pannoId)
            } else {
                mStreetViewPanorama?.setPosition(entity.pannoId)
            }

            if (isTop) {
                setMargin()
                rotateMap()
            }
        }
    }

}