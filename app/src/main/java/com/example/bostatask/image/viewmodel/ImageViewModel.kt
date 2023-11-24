package com.example.bostatask.image.viewmodel

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

class ImageViewModel (private val repoInterface: RepoInterface) : ViewModel() {
    private val _image = MutableStateFlow<ApiState>(ApiState.Loading)
    val image: StateFlow<ApiState>
        get() = _image


    fun getImage(context: Context, albumId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            _image.emit(ApiState.Loading)

            if (Utilities.isNetworkAvailable(context)) {
                repoInterface.getImageById(albumId).catch { e ->
                    _image.emit(ApiState.Failure(e.message ?: ""))
                }.collect {
                    _image.emit(ApiState.Success(it))
                }
            } else {
                _image.emit(ApiState.Failure("Internet connection not found"))
            }
        }
    }


}