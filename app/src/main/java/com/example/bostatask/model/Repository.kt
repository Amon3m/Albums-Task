package com.example.bostatask.model

import com.example.bostatask.network.RemoteSource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

class Repository private constructor(
    var remoteSource: RemoteSource
) : RepoInterface {


    companion object {
        private var instance: Repository? = null
        fun getInstance(
            remoteSource: RemoteSource
        ): Repository {
            return instance ?: synchronized(this) {
                val temp = Repository(remoteSource)
                instance = temp
                temp
            }
        }
    }

    override suspend fun getUserById(id: Int): Flow< List<UserResponse>> {
        return flowOf(remoteSource.getUserById(id))
    }

    override suspend fun getAlbumById(userId: Int): Flow<List<AlbumsResponseItem>> {
        return flowOf(remoteSource.getAlbumById(userId))
    }

    override suspend fun getAlbumImages(albumId: Int): Flow<List<ImagesResponseItem>> {
        return flowOf(remoteSource.getAlbumImages(albumId))
    }

    override suspend fun getImageById(imgId: Int): Flow<List<ImagesResponseItem>> {
        return flowOf(remoteSource.getImageById(imgId))
    }


}