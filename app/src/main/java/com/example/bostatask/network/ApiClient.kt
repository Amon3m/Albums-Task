package com.example.bostatask.network

import com.example.bostatask.model.AlbumsResponse
import com.example.bostatask.model.AlbumsResponseItem
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.model.UserResponse
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://jsonplaceholder.typicode.com/"

object ApiClient : RemoteSource {

    override suspend fun getUserById(id: Int):  List<UserResponse> {
        return Api.apiService.getUserById(id)
    }

    override suspend fun getAlbumById(userId: Int): List<AlbumsResponseItem> {
        return Api.apiService.getAlbumById(userId)
    }

    override suspend fun getAlbumImages(albumId: Int): List<ImagesResponseItem> {
        return Api.apiService.getAlbumImages(albumId)
    }

    override suspend fun getImageById(imgId: Int): List<ImagesResponseItem> {
        return Api.apiService.getImageById(imgId)
    }
}

object RetrofitHelper {
    var gson: Gson = GsonBuilder().create()
    var retrofitInstance: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()
}

object Api {
    val apiService: ApiService by lazy {
        RetrofitHelper.retrofitInstance.create(ApiService::class.java)
    }
}