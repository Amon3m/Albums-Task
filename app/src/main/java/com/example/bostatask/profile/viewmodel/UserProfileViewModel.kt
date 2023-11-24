package com.example.bostatask.profile.viewmodel

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.bostatask.Utilities.isNetworkAvailable
import com.example.bostatask.model.RepoInterface
import com.example.bostatask.network.ApiState
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.launch
import kotlin.random.Random

class UserProfileViewModel(private val repoInterface: RepoInterface) : ViewModel() {
    private val _user = MutableStateFlow<ApiState>(ApiState.Loading)
    val user: StateFlow<ApiState>
        get() = _user

    private val _albums = MutableStateFlow<ApiState>(ApiState.Loading)
    val albums: StateFlow<ApiState>
        get() = _albums

    fun getUser(context: Context, id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNetworkAvailable(context)) {
                repoInterface.getUserById(id).catch { e ->
                    _user.emit(ApiState.Failure(e.message ?: ""))
                }.collect {

                    _user.emit(ApiState.Success(it))
                }
            } else {
                _user.emit(
                    ApiState.Failure(
                        "internet connection not found"
                    )
                )
            }
        }
    }

    fun getAlbums(context: Context, userId: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            if (isNetworkAvailable(context)) {
                repoInterface.getAlbumById(userId).catch { e ->
                    _albums.emit(ApiState.Failure(e.message ?: ""))
                }.collect {
                    _albums.emit(ApiState.Success(it))
                }
            } else {
                _albums.emit(
                    ApiState.Failure(
                        "internet connection not found"
                    )
                )
            }
        }
    }


    // Generate a random number between 1 and 10
    fun generateRandom():Int {
      return  Random.nextInt(1, 11)
    }
}