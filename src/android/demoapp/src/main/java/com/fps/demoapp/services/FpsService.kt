package com.fps.demoapp.services

import android.content.Context
import android.util.Log
import com.fps.android.sdk.FPS
import com.fps.demoapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

private const val BASE_URL =
    "http://10.0.2.2:5010/"

private val retrofit = Retrofit.Builder()
    .addConverterFactory(ScalarsConverterFactory.create())
    .baseUrl(BASE_URL)
    .build()

interface IApiService {
    @Headers("Content-Type: application/json")
    @POST("/FPS/MobileCheckTransaction")
    suspend fun checkTransaction(@Body body: String): String
}

object ApiService {
    val retrofitService: IApiService by lazy {
        retrofit.create(IApiService::class.java)
    }
}

class FpsService {
    companion object {
        fun checkTransaction(
            context: Context,
            username: String
        ) {

            CoroutineScope(Dispatchers.IO).launch {
                val fpsTrans = FPS.onCheckFPSTrans(context, username, context.getString(R.string.subscriber_name), "login")

                if(fpsTrans != null) {
                    try {
                        ApiService.retrofitService.checkTransaction(fpsTrans)
                    }
                    catch (e: Exception) {
                        Log.d("ApiService", e.message!!);
                    }

                }
            }
        }
    }
}