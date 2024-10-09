package com.fps.demoapp.services

import android.content.Context
import android.util.Log
import com.fps.android.sdk.FPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.HttpException
import retrofit2.Retrofit
import retrofit2.converter.scalars.ScalarsConverterFactory
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Url

interface IApiService {
    @Headers("Content-Type: application/json")
    @POST
    suspend fun send(@Url endpoint: String, @Body body: String): String
}

class FpsService(
    baseUrl: String,
    private val endpoint: String
) {
    private val retrofit: Retrofit = Retrofit.Builder()
        .addConverterFactory(ScalarsConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val apiService: IApiService = retrofit.create(IApiService::class.java)

    fun checkTransaction(
        context: Context,
        subscriber: String,
        username: String,
        action: String
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fpsTrans = FPS.onCheckFPSTrans(context, subscriber, username, action)

                if(fpsTrans != null) {
                    apiService.send(endpoint, fpsTrans)
                }
            }
            catch (e: HttpException) {
                Log.d("HttpException", e.message!!);
            }
            catch (e: Exception) {
                Log.d("ApiService", e.message!!);
            }

        }
    }
}