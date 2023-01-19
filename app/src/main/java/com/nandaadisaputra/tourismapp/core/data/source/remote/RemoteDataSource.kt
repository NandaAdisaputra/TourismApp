package com.nandaadisaputra.tourismapp.core.data.source.remote

import android.os.Handler
import android.os.Looper
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiResponse
import com.nandaadisaputra.tourismapp.core.data.source.remote.network.ApiService
import com.nandaadisaputra.tourismapp.core.data.source.remote.response.ListTourismResponse
import com.nandaadisaputra.tourismapp.core.data.source.remote.response.TourismResponse
import com.nandaadisaputra.tourismapp.core.utils.JsonHelper
import org.json.JSONException
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class RemoteDataSource private constructor(private val apiService: ApiService) {
    //class RemoteDataSource private constructor(private val jsonHelper: JsonHelper) {
    companion object {
        @Volatile
        private var instance: RemoteDataSource? = null

        //        fun getInstance(helper: JsonHelper): RemoteDataSource =
        fun getInstance(service: ApiService): RemoteDataSource =
            instance ?: synchronized(this) {
//                instance ?: RemoteDataSource(helper)
                instance ?: RemoteDataSource(service)
            }
    }

    fun getAllTourism(): LiveData<ApiResponse<List<TourismResponse>>> {
        val resultData = MutableLiveData<ApiResponse<List<TourismResponse>>>()

        //get data from local json
        val client = apiService.getList()
//        val handler = Handler(Looper.getMainLooper())
//        handler.postDelayed({
//            try {
//                val dataArray = jsonHelper.loadData()
//                if (dataArray.isNotEmpty()) {
//                    resultData.value = ApiResponse.Success(dataArray)
//                } else {
//                    resultData.value = ApiResponse.Empty
//                }
//            } catch (e: JSONException) {
//                resultData.value = ApiResponse.Error(e.toString())
//                Log.e("RemoteDataSource", e.toString())
//            }
//        }, 2000)
//
//        return resultData
//    }
        //enqueue, artinya request dijalankan secara asynchronous.
        // Pengambilan data dijalankan di background thread dan Anda
        // akan mendapatkan hasilnya melalui callback onResponse dan onFailure.
        client.enqueue(object : Callback<ListTourismResponse> {
            override fun onResponse(
                call: Call<ListTourismResponse>,
                response: Response<ListTourismResponse>
            ) {
                val dataArray = response.body()?.places
                resultData.value = if (dataArray != null) ApiResponse.Success(dataArray) else ApiResponse.Empty
            }
            override fun onFailure(call: Call<ListTourismResponse>, t: Throwable) {
                resultData.value = ApiResponse.Error(t.message.toString())
                Log.e("RemoteDataSource", t.message.toString())
            }
        })

        return resultData
    }
}