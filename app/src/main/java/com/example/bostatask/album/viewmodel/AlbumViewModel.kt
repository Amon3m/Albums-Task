package com.example.bostatask.album.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bostatask.Utilities
import com.example.bostatask.model.RepoInterface
import com.example.bostatask.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch

class AlbumViewModel (private val repoInterface: RepoInterface) : ViewModel() {
    private val _images = MutableStateFlow<ApiState>(ApiState.Loading)
    val images: StateFlow<ApiState>
        get() = _images




    fun getImages(context: Context, albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _images.emit(ApiState.Loading)

            if (Utilities.isNetworkAvailable(context)) {
                repoInterface.getAlbumImages(albumId).catch { e ->
                    _images.emit(ApiState.Failure(e.message ?: ""))
                }.collect {
                    _images.emit(ApiState.Success(it))
                }
            } else {
                _images.emit(ApiState.Failure("Internet connection not found"))
            }
        }
    }

}