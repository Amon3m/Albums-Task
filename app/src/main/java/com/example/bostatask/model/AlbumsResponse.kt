package com.example.bostatask.model

import com.google.gson.annotations.SerializedName

data class AlbumsResponse(

	@field:SerializedName("AlbumsResponse")
	val albumsResponse: List<AlbumsResponseItem?>? = null
)

data class AlbumsResponseItem(

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("userId")
	val userId: Int? = null
)
