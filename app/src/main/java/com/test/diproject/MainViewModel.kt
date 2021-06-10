package com.test.diproject

import android.util.Log
import androidx.lifecycle.ViewModel
import com.test.diproject.model.Comment
import com.test.diproject.model.FinalResult
import com.test.diproject.model.Post
import com.test.diproject.model.Todo
import io.reactivex.Single
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.functions.Function3
import io.reactivex.schedulers.Schedulers

class MainViewModel constructor(private val mainRepository: MainRepository) : ViewModel() {

    private var disposable = CompositeDisposable()

    fun getPosts() {
        disposable.add(
            mainRepository.getPosts()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result ->
                        when (result) {
                            is ApiResponse.Success<List<Post>> -> {
                                Log.d("my_post_list", result.result.toString())
                            }
                            is ApiResponse.Error -> {
                                Log.d("my_post_list", result.error)
                            }
                        }
                    },
                    { error ->
                        error.printStackTrace()
                        Log.d("my_post_list", error.toString())
                    }
                )
        )
    }

    fun getAllRequest() {
        disposable.add(
            Single.zip(
                mainRepository.getPosts(),
                mainRepository.getComments(),
                mainRepository.getTodos(),
                Function3 {
                        t1: ApiResponse<List<Post>>,
                        t2: ApiResponse<List<Comment>>,
                        t3: ApiResponse<List<Todo>> ->
                    val posts = if (t1 is ApiResponse.Success<List<Post>>) t1.result else emptyList()
                    val comments = if (t2 is ApiResponse.Success<List<Comment>>) t2.result else emptyList()
                    val todos = if (t3 is ApiResponse.Success<List<Todo>>) t3.result else emptyList()
                    Log.d("final_result", "posts: ${posts.isNotEmpty()} \ncomments: ${comments.isNotEmpty()} \ntodos ${todos.isNotEmpty()}")
                    return@Function3 FinalResult(posts, comments, todos)
                }
            )
                .subscribeOn(Schedulers.io())
                .doOnSuccess { result -> Log.d("final_result", result.toString()) }
                //.flatMap { mainRepository.getWrappedPosts() }
                //.doOnSuccess { callbackResult -> Log.d("callback_result", callbackResult.toString()) }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    { result -> },
                    { error -> error.printStackTrace() }
                )
        )
    }

    fun getAllRequestEnqueue() {
        disposable.add(
            mainRepository.getComments()
                .subscribeOn(Schedulers.io())
                .flatMap { mainRepository.getWrappedPosts() }
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                    {},
                    {}
                )

        )
    }

    override fun onCleared() {
        super.onCleared()
        disposable.clear()
    }
}