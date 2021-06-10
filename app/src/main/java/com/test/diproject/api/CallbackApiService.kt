package com.test.diproject.api

import com.test.diproject.model.Post
import retrofit2.Call
import retrofit2.http.GET

interface CallbackApiService {

    @GET("posts")
    fun getPostsCallback(): Call<List<Post>>
}