package com.example.bostatask.model

import kotlinx.coroutines.flow.Flow

interface RepoInterface {
    suspend fun getUserById(
        id:Int
    ): Flow< List<UserResponse>>
    suspend fun getAlbumById(
        userId:Int
    ): Flow< List<AlbumsResponseItem>>
    suspend fun getAlbumImages(
        albumId:Int
    ): Flow< List<ImagesResponseItem>>
    suspend fun getImageById(
        imgId: Int
    ): Flow<List<ImagesResponseItem>>


}