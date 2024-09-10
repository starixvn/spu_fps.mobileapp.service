package fps.mobile.client.InfoProviders

import android.content.Context
import android.hardware.Sensor
import android.hardware.SensorManager

public data class SensorInfo(
    val sensorName: String,
    val vendorName: String
)

interface ISensorInfoProvider {
    public fun getSensorList(): List<SensorInfo>
}

internal class SensorInfoProvider(
    context: Context
) : ISensorInfoProvider {
    private val sensorManager: SensorManager = context.getSystemService(Context.SENSOR_SERVICE) as SensorManager

    override fun getSensorList(): List<SensorInfo> {
        return sensorManager.getSensorList(Sensor.TYPE_ALL)!!.map {
            SensorInfo(
                it!!.name!!,
                it.vendor!!
            )
        }
    }

}