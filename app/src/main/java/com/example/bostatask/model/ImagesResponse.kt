package com.example.bostatask.model

import com.google.gson.annotations.SerializedName

data class ImagesResponse(

	@field:SerializedName("ImagesResponse")
	val imagesResponse: List<ImagesResponseItem?>? = null
)

data class ImagesResponseItem(

	@field:SerializedName("albumId")
	val albumId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("title")
	val title: String? = null,

	@field:SerializedName("url")
	val url: String? = null,

	@field:SerializedName("thumbnailUrl")
	val thumbnailUrl: String? = null
)
