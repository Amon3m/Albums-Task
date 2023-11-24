package com.example.bostatask.network

import com.example.bostatask.model.AlbumsResponse
import com.example.bostatask.model.AlbumsResponseItem
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.model.UserResponse
import retrofit2.http.GET
import retrofit2.http.Query

interface ApiService {
    @GET("users")
    suspend fun getUserById(
        @Query("id") id: Int,
    ): List<UserResponse>

    @GET("albums")
    suspend fun getAlbumById(
        @Query("userId") userId: Int,
    ): List<AlbumsResponseItem>

    @GET("photos")
    suspend fun getAlbumImages(
        @Query("albumId") userId: Int,
    ): List<ImagesResponseItem>


    @GET("photos")
    suspend fun getImageById(
        @Query("id") imgId: Int,
    ): List<ImagesResponseItem>
}