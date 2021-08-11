package net.fragment

import android.content.ContentResolver
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import kotlinx.android.synthetic.main.layout_fragment_nearby.*
import net.adapter.NearbyAdapter
import net.basicmodel.R
import net.utils.OnItemClickListener
import net.utils.PackageUtils
import java.util.*

/**
 * Copyright (C) 2021,2021/8/10, a Tencent company. All rights reserved.
 *
 * User : v_xhangxie
 *
 * Desc :
 */
class NearbyFragment : Fragment(), OnItemClickListener {

    var typeName: ArrayList<String> = ArrayList()
    var typeBg: ArrayList<String> = ArrayList()
    var adapter: NearbyAdapter? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.layout_fragment_nearby, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        getData()
        adapter = NearbyAdapter(typeName, typeBg, activity, activity)
        var layoutManager = GridLayoutManager(activity, 3)
        recyclerview.layoutManager = layoutManager
        recyclerview.adapter = adapter
        adapter!!.setListener(this)
    }

    private fun getData() {
        typeName.add("school")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.school))

        typeName.add("post office")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.post_office))

        typeName.add("shoe store")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.shoe))

        typeName.add("gas station")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.gas_station))

        typeName.add("bakery")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.bakery))

        typeName.add("parks")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.park))

        typeName.add("mosques")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.mosque))

        typeName.add("hotels")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.hotel))

        typeName.add("doctor")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.doctor))

        typeName.add("police station")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.police))

        typeName.add("fire station")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.fire_station))

        typeName.add("church")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.church))

        typeName.add("banks")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.bank))

        typeName.add("jewelry store")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.jewelry))

        typeName.add("dentist")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.dentist))

        typeName.add("atms")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.atm))

        typeName.add("hospital")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.hospital))

        typeName.add("zoo")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.zoo))

        typeName.add("pharmacy")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.pharmacy))

        typeName.add("pets store")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.pet))

        typeName.add("airports")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.airport))

        typeName.add("beauty salon")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.salon))

        typeName.add("bus stop")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.bus))

        typeName.add("university")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.university))

        typeName.add("shopping mall")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.mall))

        typeName.add("stadium")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.stadium))

        typeName.add("cafe")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.cafe))

        typeName.add("clothing store")
        typeBg.add(imageTranslateUri(requireActivity(), R.mipmap.cloth))

    }

    private fun imageTranslateUri(context: Context, resId: Int): String {
        val r = context.resources
        val uri = Uri.parse(
            ContentResolver.SCHEME_ANDROID_RESOURCE + "://"
                    + r.getResourcePackageName(resId) + "/"
                    + r.getResourceTypeName(resId) + "/"
                    + r.getResourceEntryName(resId)
        )
        return uri.toString()
    }

    override fun onItemClick(view: View?, position: Int, flag: String?) {
        if (!PackageUtils.checkAppInstalled(activity, "com.google.android.apps.maps")) {
            Toast.makeText(activity, "not found google map", Toast.LENGTH_SHORT).show()
            return
        }
        val key = typeName[position]
        val url = "http://maps.google.com/maps?q=$key&hl=en"
        val mapIntent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
        mapIntent.setPackage("com.google.android.apps.maps")
        requireActivity().startActivity(mapIntent)
    }
}