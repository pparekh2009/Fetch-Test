package com.priyanshparekh.fetchtest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException

class DataViewModel : ViewModel() {

    private val _dataStatus = MutableLiveData<List<Item>?>()
    val dataStatus: LiveData<List<Item>?> = _dataStatus

    fun getData() {
        viewModelScope.launch(Dispatchers.IO) {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url("https://hiring.fetch.com/hiring.json")
                .build()

            client.newCall(request).enqueue(object : Callback {
                override fun onFailure(request: Request?, e: IOException?) {
                    _dataStatus.postValue(null)
                }

                override fun onResponse(response: Response?) {
                    if (response != null && response.isSuccessful) {
                        val data = response.body().string()

                        val dataObj = Gson().fromJson<List<Item>>(data, object : TypeToken<List<Item>>() {}.type)

                        _dataStatus.postValue(dataObj)
                    } else {
                        _dataStatus.postValue(null)
                    }
                }

            })
        }
    }

}