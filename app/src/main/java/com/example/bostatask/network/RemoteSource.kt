package com.example.bostatask.network

import com.example.bostatask.model.AlbumsResponseItem
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.model.UserResponse
import retrofit2.http.Query

interface RemoteSource {
    suspend fun getUserById(
        id: Int=1,
    ):  List<UserResponse>

    suspend fun getAlbumById(
        userId: Int=1,
    ):  List<AlbumsResponseItem>

    suspend fun getAlbumImages(
        albumId: Int,
    ): List<ImagesResponseItem>

    suspend fun getImageById(
        imgId: Int,
    ): List<ImagesResponseItem>

}