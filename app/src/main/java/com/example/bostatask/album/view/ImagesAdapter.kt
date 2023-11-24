package com.example.bostatask.album.view

import android.content.Context
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.example.bostatask.R
import com.example.bostatask.databinding.AlbumsItemBinding
import com.example.bostatask.databinding.ImagesItemBinding
import com.example.bostatask.model.AlbumsResponseItem
import com.example.bostatask.model.ImagesResponseItem
import com.example.bostatask.profile.view.OnAlbumClickListener

class ImagesAdapter(val context: Context, private val listener: OnImagesClickListener) :
    ListAdapter<ImagesResponseItem?, ImagesViewHolder>(ImagesDiffUtil()) {
    lateinit var binding: ImagesItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ImagesViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = ImagesItemBinding.inflate(inflater, parent, false)
        return ImagesViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ImagesViewHolder, position: Int) {

        val currentObject = getItem(position)
        Log.i("img","${currentObject?.thumbnailUrl}")
        Glide.with(context)
            .load(currentObject?.thumbnailUrl)
            .error(R.drawable.img_error)
            .placeholder(R.drawable.loading_img)
            .into(  holder.binding.imageView)
        holder.binding.imageView.setOnClickListener {
            listener.onImagesClick(currentObject)
        }
    }

}

class ImagesViewHolder(var binding: ImagesItemBinding) : RecyclerView.ViewHolder(binding.root)


class ImagesDiffUtil : DiffUtil.ItemCallback<ImagesResponseItem?>() {
    override fun areItemsTheSame(
        oldItem: ImagesResponseItem,
        newItem: ImagesResponseItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: ImagesResponseItem,
        newItem: ImagesResponseItem
    ): Boolean {
        return oldItem == newItem
    }

}