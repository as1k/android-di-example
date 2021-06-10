package com.test.diproject.api

import com.test.diproject.model.Comment
import com.test.diproject.model.Post
import com.test.diproject.model.Todo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Response
import retrofit2.http.GET

interface ApiService {

    @GET("posts")
    fun getPosts(): Single<Response<List<Post>>>

    @GET("comments")
    fun getComments(): Single<Response<List<Comment>>>

    @GET("todos")
    fun getTodos(): Single<Response<List<Todo>>>
}