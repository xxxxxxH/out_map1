package net.http

import net.entiy.BigPlaceEntity
import retrofit2.Call
import retrofit2.http.GET

interface RequestService {

    @GET("streetview/feed/gallery/data.json")
    fun getData(): Call<BigPlaceEntity>

}