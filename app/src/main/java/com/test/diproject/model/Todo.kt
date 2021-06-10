package com.test.diproject.model

import com.google.gson.annotations.SerializedName

data class Todo(
    @SerializedName("userId")
    val userId: String,
    @SerializedName("id")
    val id: Int,
    @SerializedName("title")
    val title: String,
    @SerializedName("completed")
    val completed: Boolean
)