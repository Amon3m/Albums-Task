package com.example.bostatask.model

import com.google.gson.annotations.SerializedName

data class UserResponse(
	@SerializedName("id")
	val id: Int? = null,

	@SerializedName("name")
	val name: String? = null,

	@SerializedName("username")
	val username: String? = null,

	@SerializedName("email")
	val email: String? = null,

	@SerializedName("address")
	val address: Address? = null,

	@SerializedName("phone")
	val phone: String? = null,

	@SerializedName("website")
	val website: String? = null,

	@SerializedName("company")
	val company: Company? = null
)

data class Geo(
	@SerializedName("lat")
	val lat: String? = null,

	@SerializedName("lng")
	val lng: String? = null
)

data class Address(
	@SerializedName("street")
	val street: String? = null,

	@SerializedName("suite")
	val suite: String? = null,

	@SerializedName("city")
	val city: String? = null,

	@SerializedName("zipcode")
	val zipcode: String? = null,

	@SerializedName("geo")
	val geo: Geo? = null
)

data class Company(
	@SerializedName("name")
	val name: String? = null,

	@SerializedName("catchPhrase")
	val catchPhrase: String? = null,

	@SerializedName("bs")
	val bs: String? = null
)
