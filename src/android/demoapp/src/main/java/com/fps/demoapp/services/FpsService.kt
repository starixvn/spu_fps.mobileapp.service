package com.fps.demoapp.services

import android.content.Context
import android.util.Log
import com.fps.android.sdk.FPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface IApiService {
    @Headers("Content-Type: application/json")
    @POST
    suspend fun checkTransaction(@Url endpoint: String, @Body body: String): String
}

class FpsService {
    private lateinit var retrofit: Retrofit
    private lateinit var apiService: IApiService
    private lateinit var endpoint: String

    fun initialRequest(baseUrl: String, endpoint: String): FpsService {
        retrofit = Retrofit.Builder()
            .addConverterFactory(ScalarsConverterFactory.create())
            .baseUrl(baseUrl)
            .build()

        apiService = retrofit.create(IApiService::class.java)
        this.endpoint = endpoint

        return this
    }

    fun checkTransaction(
        context: Context,
        subscriber: String,
        username: String,
        action: String
    ): FpsService {

        CoroutineScope(Dispatchers.IO).launch {
            val fpsTrans = FPS.onCheckFPSTrans(context, subscriber, username, action)

            if(fpsTrans != null) {
                try {
                    apiService.checkTransaction(endpoint, fpsTrans)
                }
                catch (e: Exception) {
                    Log.d("ApiService", e.message!!);
                }

            }
        }

        return this
    }
}