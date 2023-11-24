package com.example.bostatask.image.view

import android.content.Intent
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.lifecycleScope
import com.bumptech.glide.Glide
import com.example.bostatask.R
import com.example.bostatask.databinding.FragmentImageBinding
import com.example.bostatask.image.viewmodel.ImageViewModel
import com.example.bostatask.image.viewmodel.ImageViewModelFactory
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.model.Repository
import com.example.bostatask.network.ApiClient
import com.example.bostatask.network.ApiState
import kotlinx.coroutines.flow.collect

import kotlinx.coroutines.launch

class ImageFragment : Fragment() {
    private lateinit var binding: FragmentImageBinding
    private lateinit var imageViewModel: ImageViewModel
    private lateinit var imageViewModelFactory: ImageViewModelFactory
    private lateinit var scaleGestureDetector: ScaleGestureDetector
    private var imageUrl: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        imageViewModelFactory = ImageViewModelFactory(
            Repository.getInstance(ApiClient)
        )

        imageViewModel =
            ViewModelProvider(
                this,
                imageViewModelFactory
            ).get(ImageViewModel::class.java)

        val imgId = ImageFragmentArgs.fromBundle(requireArguments()).imageId

        imageViewModel.getImage(requireContext(), imgId)
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentImageBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scaleGestureDetector = ScaleGestureDetector(
            requireContext(),
            object : ScaleGestureDetector.SimpleOnScaleGestureListener() {
                override fun onScale(detector: ScaleGestureDetector): Boolean {
                    val scaleFactor = detector.scaleFactor
                    binding.imageView.scaleX *= scaleFactor
                    binding.imageView.scaleY *= scaleFactor
                    return true
                }
            })

        binding.imageView.setOnTouchListener { _, event ->
            scaleGestureDetector.onTouchEvent(event)
            true
        }

        binding.floatingActionButton.setOnClickListener {
            shareImageLink()
        }

        lifecycleScope.launch {
            imageViewModel.image.collect {
                when (it) {
                    is ApiState.Success<*> -> {
                        val data = it.data as? List<ImagesResponseItem>

                        binding.progressBar2.visibility = View.INVISIBLE
                        binding.floatingActionButton.visibility = View.VISIBLE

                        imageUrl = data?.get(0)?.url
                        Glide.with(requireContext())
                            .load(data?.get(0)?.url ?: "")
                            .error(R.drawable.img_error)
                            .into(binding.imageView)
                        binding.imageNameTxt.text = data?.get(0)?.title ?: ""
                    }

                    is ApiState.Failure -> {
                        Toast.makeText(requireContext(), it.msg, Toast.LENGTH_SHORT).show()
                        binding.progressBar2.visibility = View.INVISIBLE
                        binding.floatingActionButton.visibility = View.GONE
                    }

                    else -> {
                        binding.progressBar2.visibility = View.VISIBLE
                        binding.floatingActionButton.visibility = View.GONE
                    }
                }
            }
        }
    }

    private fun shareImageLink() {
        if (!imageUrl.isNullOrBlank()) {
            val shareIntent = Intent(Intent.ACTION_SEND)
            shareIntent.type = "text/plain"
            shareIntent.putExtra(Intent.EXTRA_TEXT, imageUrl)
            startActivity(Intent.createChooser(shareIntent, "Share Image Link"))
        } else {
            Toast.makeText(requireContext(), "Image URL not available", Toast.LENGTH_SHORT).show()
        }
    }
}
