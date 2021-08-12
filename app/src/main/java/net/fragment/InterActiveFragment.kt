package net.fragment

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.layout_fragment_interactive.*
import net.adapter.PlaceAdapter
import net.basicmodel.InterActiveActivity
import net.basicmodel.R
import net.entiy.BigPlaceEntity
import net.http.RequestService
import net.http.RetrofitUtils
import net.utils.LoadingDialog
import net.utils.NetUtils
import net.utils.OnItemClickListener
import org.json.JSONException
import org.json.JSONObject

/**
 * Copyright (C) 2021,2021/8/10, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class InterActiveFragment : Fragment(), OnItemClickListener {

    var data: ArrayList<BigPlaceEntity> = ArrayList()
    var adapter: PlaceAdapter? = null
    var dialog: LoadingDialog? = null

    val retrofit = RetrofitUtils.get().retrofit()
    val service = retrofit.create(RequestService::class.java)

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_interactive, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        showDlg()
        getData()
    }

    private fun getData() {
        Thread {
            val data: String =
                NetUtils.getUrlContent("https://www.google.com/streetview/feed/gallery/data.json")
            requireActivity().runOnUiThread {
                try {
                    val jsonObject = JSONObject(data)
                    val iterator = jsonObject.keys()
                    var i = 0
                    while (iterator.hasNext()) {
                        val key = iterator.next()
                        val jsonObject1 = jsonObject.getJSONObject(key)
                        val bigPlace = BigPlaceEntity()
                        bigPlace.title = jsonObject1.getString("title")
                        bigPlace.lat = jsonObject1.getDouble("lat")
                        bigPlace.lng = jsonObject1.getDouble("lng")
                        bigPlace.key = key
                        bigPlace.panoid = jsonObject1.getString("panoid")
                        if ("LiAWseC5n46JieDt9Dkevw" == bigPlace.panoid) {      //这个没图片
                            continue
                        }
                        if (jsonObject1.has("isFife")) {
                            bigPlace.isFife = jsonObject1.getBoolean("isFife")
                            continue  //这种情况略过
                        }
                        if (bigPlace.isFife) {
                            bigPlace.imageUrl =
                                "https://lh4.googleusercontent.com/" + bigPlace.panoid
                                    .toString() + "/w400-h300-fo90-ya0-pi0/"
                            continue  //这种情况略过
                        } else {
                            bigPlace.imageUrl =
                                "https://geo0.ggpht.com/cbk?output=thumbnail&thumb=2&panoid=" + jsonObject1.getString(
                                    "panoid"
                                )
                        }
                        this.data.add(bigPlace)
                    }
                    setAdapter(this.data)
                } catch (e: JSONException) {
                    e.printStackTrace()
                    isShowEmptyView(true)
                } finally {
                    closeDlg()
                }
            }
        }.start()
    }

    private fun setAdapter(data: ArrayList<BigPlaceEntity>) {
        adapter = PlaceAdapter(data, null, activity, activity)
        recycler.layoutManager = LinearLayoutManager(activity)
        recycler.adapter = adapter
        adapter!!.setListener(this)
    }

    private fun isShowEmptyView(isShow: Boolean) {
        if (isShow) {
            recycler.visibility = View.GONE
            emptyView.visibility = View.VISIBLE
        } else {
            recycler.visibility = View.VISIBLE
            emptyView.visibility = View.GONE
        }
    }

    private fun showDlg() {
        if (dialog == null) {
            dialog = LoadingDialog(activity)
        }
        dialog!!.show()
    }

    private fun closeDlg() {
        if (dialog != null && dialog!!.isShowing) {
            dialog!!.dismiss()
        }
    }

    override fun onItemClick(view: View?, position: Int, flag: String?) {
        if (TextUtils.equals(flag, "big")) {
            val entity = data[position]
            val intent = Intent(activity, InterActiveActivity::class.java)
            intent.putExtra("data", entity)
            activity?.startActivity(intent)
        }
    }

}