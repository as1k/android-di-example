package com.test.diproject

import com.test.diproject.model.Comment
import com.test.diproject.model.Post
import com.test.diproject.model.Todo
import io.reactivex.Single

interface MainRepository {
    fun getPosts(): Single<ApiResponse<List<Post>>>
    fun getTodos(): Single<ApiResponse<List<Todo>>>
    fun getComments(): Single<ApiResponse<List<Comment>>>

    fun getWrappedPosts(): Single<ApiResponse<List<Post>>>
}