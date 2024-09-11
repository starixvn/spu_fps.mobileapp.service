@file:Suppress("DEPRECATION")

package fps.mobile.client.infoProviders

import android.content.Context
import android.hardware.Camera
import android.hardware.camera2.CameraManager
import java.util.LinkedList

data class CameraInfo (
    val cameraName: String,
    val cameraType: String,
    val cameraOrientation: String
)

interface ICameraInfoProvider {
    fun isCameraPresent(): Boolean
    fun getCameraList(): List<CameraInfo>
}

class CameraInfoProvider(
    private val context: Context
) : ICameraInfoProvider {

    private val cameraManager = context.getSystemService(Context.CAMERA_SERVICE) as CameraManager

    override fun isCameraPresent(): Boolean {
        return try {
            return cameraManager.cameraIdList.isNotEmpty()
        } catch (e: Exception) {
            false
        }
    }

    override fun getCameraList(): List<CameraInfo> {
        val cameraList = LinkedList<CameraInfo>()
        try {
            val numberOfCameras = cameraManager.cameraIdList.size
            for (i in 0 until numberOfCameras) {
                val info = Camera.CameraInfo()
                Camera.getCameraInfo(i, info)
                val type = stringDescriptionForType(info.facing)
                val orientation = info.orientation.toString()
                cameraList.add(
                    CameraInfo(
                        i.toString(),
                        type,
                        orientation
                    )
                )
            }
        } catch (e: Exception) {
            return cameraList
        }
        return cameraList
    }

    private fun stringDescriptionForType(type: Int) = when (type) {
        Camera.CameraInfo.CAMERA_FACING_FRONT -> "front"
        Camera.CameraInfo.CAMERA_FACING_BACK -> "back"
        else -> ""
    }
}