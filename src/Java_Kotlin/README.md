# FPS Android SDK
The FPS Flutter SDK provides a seamless integration of FPS (Fraud Prevention Service) capabilities within your native Android applications. This plugin supports both Kotlin and Java.

Fully written in Kotlin. 100% Crash-free.

## Table of Contents
- [FPS Android SDK](#fps-android-sdk)
  - [Table of Contents](#table-of-contents)
  - [Requirements](#requirements)
  - [Platform Support](#platform-support)
  - [Installation](#installation)
  - [Quick Started](#quick-started)
    - [Basic Setup](#basic-setup)
    - [Define the FPS Service layer](#define-the-fps-service-layer)
    - [Integrate in Authentication Flow](#integrate-in-authentication-flow)
    - [Integrating in Session Management](#integrating-in-session-management)
  - [Changelog](#changelog)

## Requirements
- Android SDK 24+
- Java 8
- Gradle

## Platform Support
- Android `minSDK >= 24`

## Installation
1. **Download the AAR file**
   - Download the latest FPS Android SDK AAR file from the official repository or contact support
   - Save the AAR file to your project's `libs` directory (create one if it doesn't exist)

2. **Add the libs directory to your build path**
   - In your app-level `build.gradle` file, add the following:

   ```groovy
   repositories {
       flatDir {
           dirs 'libs'
       }
   }
   ```

3. **Add the AAR as a dependency**
In the same build.gradle file, add the dependency:
```kts
dependencies {
    // Your app dependencies

    implementation(name: 'com.fps.android.core-x.x.x', ext: 'aar')

    implementation(name: 'com.fps.android.sdk-x.x.x', ext: 'aar')
}
```

## Quick Started
### Basic Setup
Import the FPS package in your Kotlin file:
```kt
import com.fps.android.sdk.FPS
```

### Define the FPS Service layer
Implement a service layer that utilizes the FPS SDK methods to handle transactions and forwards the `transaction` data to the `FPS Backend` through your application's service layer.

```kt
package com.fps.demoapp.services

import android.content.Context
import android.util.Log
import com.fps.android.sdk.FPS
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.json.JSONObject
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
        action: String,
        isOnStart: Boolean = false
    ) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val fpsTrans = FPS.onCheckFPSTrans(context, subscriber, username, action, isOnStart)

                send(fpsTrans)
            }
            catch (e: HttpException) {
                Log.d("HttpException",  e.toString())
            }
            catch (e: Exception) {
                Log.d("ApiService", e.toString())
            }

        }
    }

    private suspend fun send(transaction: String?) {
        if(transaction == null)
            return

        val jsonObject = JSONObject()
        jsonObject.put("transaction", transaction)

        apiService.send(endpoint, jsonObject.toString())
    }
}
```

### Integrate in Authentication Flow
```kt
// Example of login flow
fun login(
) {
    // Your authentication logic
    // After successful authentication:

    val fpsService = FpsService("APP_SERVICE_BASE_URL","/FPS/MobileCheckTransaction")
    fpsService.checkTransaction(context, _uiState.value.subscriber, _uiState.value.username, "login")
}
```

### Integrating in Session Management
Handle app lifecycle states to properly manage FPS sessions:
- When the user is inactive in the app, invoke `onResetFPSLastLiveTime` to record the last active time.
- When the user returns to the app (e.g., after switching apps, turning off the screen, or experiencing a Wi-Fi disconnection), call `checkTransaction` to log the `login` or `resume app` activity.
```kt
    override fun onStart() {
        super.onStart()

        if(isAuthenticated()) {
            val fpsService = FpsService("APP_SERVICE_BASE_URL","/FPS/MobileCheckTransaction")

            fpsService.checkTransaction(this, subscriber, username, "resume app", true)
        }
    }

    override fun onStop() {
        super.onStop()

        FPS.onResetFPSLastLiveTime()
    }
```
## Changelog
* 1.0.6
    * Fix bug on handling Burmese numerals (and similar non-Arabic numerals)
* 1.0.5
    * None
* 1.0.4
    * None
* 1.0.3
    * Various bug fixes and optimizations
* 1.0.2
    * Various bug fixes and optimizations
* 1.0.1
    * Various bug fixes and optimizations
* 1.0.0
    * Initial Release for Android SDK