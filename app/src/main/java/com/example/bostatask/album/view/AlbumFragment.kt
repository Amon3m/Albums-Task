package com.example.bostatask.album.view

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import androidx.navigation.Navigation

import com.example.bostatask.album.viewmodel.AlbumViewModel
import com.example.bostatask.album.viewmodel.AlbumViewModelFactory
import com.example.bostatask.databinding.FragmentAlbumBinding
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.model.Repository
import com.example.bostatask.network.ApiClient
import com.example.bostatask.network.ApiState
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class AlbumFragment : Fragment(), OnImagesClickListener {
    private lateinit var binding: FragmentAlbumBinding
    private lateinit var albumFragmentViewModel: AlbumViewModel
    private lateinit var albumFragmentViewModelFactory: AlbumViewModelFactory
    lateinit var imagesAdapter: ImagesAdapter
    var imagesList: List<ImagesResponseItem>? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        albumFragmentViewModelFactory = AlbumViewModelFactory(
            Repository.getInstance(ApiClient)
        )

        albumFragmentViewModel =
            ViewModelProvider(
                this,
                albumFragmentViewModelFactory
            ).get(AlbumViewModel::class.java)

        val albumId = AlbumFragmentArgs.fromBundle(requireArguments()).albumId

        albumFragmentViewModel.getImages(requireContext(), albumId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentAlbumBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val albumName = AlbumFragmentArgs.fromBundle(requireArguments()).albumName
        binding.albumNameTxt.text = albumName

        imagesAdapter = ImagesAdapter(requireContext(), this)

        binding.imagesRecyclerview.apply {
            adapter = imagesAdapter
        }
        lifecycleScope.launch {
            albumFragmentViewModel.images.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? List<ImagesResponseItem>
                        Log.i("img", "${data?.get(0)?.thumbnailUrl}")

                        imagesList = data
                        binding.progressBar.visibility = View.INVISIBLE
                        imagesAdapter.submitList(data)
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.progressBar.visibility = View.INVISIBLE
                    }

                    else -> {
                        binding.progressBar.visibility = View.VISIBLE
                    }
                }
            }
        }
        binding.editTextSearch.addTextChangedListener(object : TextWatcher {
            private var searchJob: Job? = null

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val searchStr = s?.toString()

                searchJob?.cancel() // Cancel any previous job if still running

                searchJob = viewLifecycleOwner.lifecycleScope.launch {

                    delay(1000) // 1 second delay between every submitList
                    val filteredList = imagesList?.filter {
                        it.title?.contains(searchStr.orEmpty(), ignoreCase = true) ?: false
                    }

                    imagesAdapter.submitList(filteredList.orEmpty())
                }
            }

            override fun afterTextChanged(p0: Editable?) {
            }
        })
    }


    override fun onImagesClick(imagesResponseItem: ImagesResponseItem?) {
        val action = AlbumFragmentDirections.actionAlbumFragmentToImageFragment(
            imagesResponseItem?.id ?: 1
        )
        Navigation.findNavController(requireView()).navigate(action)
    }

}