package com.example.bostatask.profile.view

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.Toast
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation
import androidx.navigation.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.bostatask.R
import com.example.bostatask.databinding.FragmentUserProfileBinding
import com.example.bostatask.model.AlbumsResponse
import com.example.bostatask.model.AlbumsResponseItem
import com.example.bostatask.model.Repository
import com.example.bostatask.model.UserResponse
import com.example.bostatask.network.ApiClient
import com.example.bostatask.network.ApiState
import com.example.bostatask.profile.viewmodel.UserProfileViewModel
import com.example.bostatask.profile.viewmodel.UserProfileViewModelFactory
import kotlinx.coroutines.launch

class UserProfileFragment : Fragment(), OnAlbumClickListener {
    private lateinit var binding: FragmentUserProfileBinding
    private lateinit var userProfileViewModel: UserProfileViewModel
    private lateinit var userProfileViewModelFactory: UserProfileViewModelFactory
    private lateinit var albumsAdapter: AlbumsAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        userProfileViewModelFactory = UserProfileViewModelFactory(
            Repository.getInstance(ApiClient)
        )

        userProfileViewModel =
            ViewModelProvider(
                this,
                userProfileViewModelFactory
            ).get(UserProfileViewModel::class.java)

        val id: Int = userProfileViewModel.generateRandom()

        userProfileViewModel.getUser(requireContext(), id)
        userProfileViewModel.getAlbums(requireContext(), id)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        binding = FragmentUserProfileBinding.inflate(inflater, container, false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        lifecycleScope.launch {
            userProfileViewModel.user.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? List<UserResponse>

                        binding.userProgressBar.visibility = View.INVISIBLE
                        binding.userAddressTxt.text = data?.get(0)?.address?.let {
                            "${it.street}, ${it.suite}, ${it.city}, ${it.zipcode}"
                        } ?: ""

                        binding.userNameTxt.text = data?.get(0)?.name ?: ""
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.userProgressBar.visibility = View.INVISIBLE
                    }

                    else -> {
                        binding.userProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }

        albumsAdapter = AlbumsAdapter(requireContext(), this)

        binding.recyclerView.apply {
            adapter = albumsAdapter
            layoutManager = LinearLayoutManager(requireContext())
        }
        lifecycleScope.launch {
            userProfileViewModel.albums.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? List<AlbumsResponseItem>

                        binding.albumProgressBar.visibility = View.INVISIBLE
                        albumsAdapter.submitList(data)
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.albumProgressBar.visibility = View.INVISIBLE
                    }

                    else -> {
                        binding.albumProgressBar.visibility = View.VISIBLE
                    }
                }
            }
        }


    }

    override fun onAlbumsClick(albumsResponseItem: AlbumsResponseItem?) {

        val action = UserProfileFragmentDirections.actionUserProfileFragmentToAlbumFragment(
            albumsResponseItem?.title ?: "",
            albumsResponseItem?.id ?: 1
        )
        Navigation.findNavController(requireView()).navigate(action)
    }

}