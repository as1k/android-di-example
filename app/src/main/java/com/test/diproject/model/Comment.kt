package com.test.diproject.model

import com.google.gson.annotations.SerializedName

data class Comment(
    @SerializedName("body")
    val body: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("name")
    val name: String,
    @SerializedName("userId")
    val userId: Int,
    @SerializedName("email")
    val email: String
)