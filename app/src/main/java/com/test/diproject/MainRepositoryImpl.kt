package com.test.diproject

import android.util.Log
import com.test.diproject.api.ApiService
import com.test.diproject.api.CallbackApiService
import com.test.diproject.model.Comment
import com.test.diproject.model.Post
import com.test.diproject.model.Todo
import io.reactivex.Single
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class MainRepositoryImpl(
    private val apiService: ApiService,
    private val callbackApiService: CallbackApiService
) : MainRepository {

    override fun getPosts(): Single<ApiResponse<List<Post>>> {
        return apiService.getPosts()
            .map { response ->
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    ApiResponse.Success(list)
                } else {
                    ApiResponse.Error<List<Post>>("Response error")
                }
            }
    }

    override fun getTodos(): Single<ApiResponse<List<Todo>>> {
        return apiService.getTodos()
            .map { response ->
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    ApiResponse.Success(list)
                } else {
                    ApiResponse.Error<List<Todo>>("Response error")
                }
            }
    }

    override fun getComments(): Single<ApiResponse<List<Comment>>> {
        return apiService.getComments()
            .map { response ->
                if (response.isSuccessful) {
                    val list = response.body() ?: emptyList()
                    ApiResponse.Success(list)
                } else {
                    ApiResponse.Error<List<Comment>>("Response error")
                }
            }
    }

    override fun getWrappedPosts(): Single<ApiResponse<List<Post>>> {
        return Single.create { emitter ->
            callbackApiService.getPostsCallback().enqueue(object : Callback<List<Post>> {
                override fun onFailure(call: Call<List<Post>>, t: Throwable) {
                    emitter.onError(t)
                }

                override fun onResponse(
                    call: Call<List<Post>>,
                    response: Response<List<Post>>
                ) {
                    if (response.isSuccessful) {
                        val list = response.body() ?: emptyList()
                        Log.d("wrapped_posts", list.toString())
                        emitter.onSuccess(ApiResponse.Success(list))
                    } else {
                        Log.d("wrapped_posts", "Callback response error")
                        emitter.onSuccess(ApiResponse.Error("Callback response error"))
                    }
                }
            })
        }
    }

}