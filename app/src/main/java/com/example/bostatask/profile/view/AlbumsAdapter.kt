package com.example.bostatask.profile.view

import android.content.Context
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.bostatask.databinding.AlbumsItemBinding
import com.example.bostatask.model.AlbumsResponseItem

class AlbumsAdapter(val context: Context, private val listener: OnAlbumClickListener) :
    ListAdapter<AlbumsResponseItem?, AlbumsViewHolder>(AlbumsDiffUtil()) {
    lateinit var binding: AlbumsItemBinding
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AlbumsViewHolder {
        val inflater: LayoutInflater =
            parent.context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
        binding = AlbumsItemBinding.inflate(inflater, parent, false)
        return AlbumsViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AlbumsViewHolder, position: Int) {

        val currentObject = getItem(position)

        holder.binding.albumTxt.text = currentObject?.title
        holder.binding.albumTxt.setOnClickListener {
            listener.onAlbumsClick(currentObject)
        }
    }

}

class AlbumsViewHolder(var binding: AlbumsItemBinding) : RecyclerView.ViewHolder(binding.root)


class AlbumsDiffUtil : DiffUtil.ItemCallback<AlbumsResponseItem?>() {
    override fun areItemsTheSame(
        oldItem: AlbumsResponseItem,
        newItem: AlbumsResponseItem
    ): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(
        oldItem: AlbumsResponseItem,
        newItem: AlbumsResponseItem
    ): Boolean {
        return oldItem == newItem
    }

}