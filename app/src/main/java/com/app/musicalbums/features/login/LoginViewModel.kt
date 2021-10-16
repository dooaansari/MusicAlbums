package com.app.musicalbums.features.login

import android.util.Log
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.app.musicalbums.di.IoDispatcher
import com.app.musicalbums.models.todos.ToDoResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltViewModel
open class LoginViewModel @Inject constructor(@IoDispatcher private val ioDispatcher: CoroutineDispatcher, val repository: LoginRepository) : ViewModel() {

    val commentsSuccess = MutableLiveData<List<ToDoResponse>?>()
    val commentsFailure = MutableLiveData<Int>()

    fun getAppComments() {
       val scope = viewModelScope.launch {
           withContext(ioDispatcher){
               val result = async{repository.getComments()

               }.await()

               Log.i("response","commetns"+result.toString())
               if(result.isSuccessful){
                   commentsSuccess.value = result.body()
               }else{
                   commentsFailure.value = result.code()
               }

               val result2 = async{repository.getComments()

               }.await()

           }


       }
    }

    val loginUser = {

    }

    val isLogin = {

    }


    fun test(){
        repository.checkPrint()
    }

    override fun onCleared() {
        super.onCleared()
        Log.i("response","oncleare")
    }
}