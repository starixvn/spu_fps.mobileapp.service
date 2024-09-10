package fps.mobile.client.FingerprintProviders

import android.content.Context
import fps.mobile.client.InfoProviders.BatteryInfoProvider
import fps.mobile.client.InfoProviders.CameraInfoProvider
import fps.mobile.client.InfoProviders.CpuInfoProvider
import fps.mobile.client.InfoProviders.ICpuInfoProvider
import fps.mobile.client.InfoProviders.IBatteryInfoProvider
import fps.mobile.client.InfoProviders.ICameraInfoProvider
import fps.mobile.client.InfoProviders.IMemoryInfoProvider
import fps.mobile.client.InfoProviders.ISensorInfoProvider
import fps.mobile.client.InfoProviders.MemoryInfoProvider
import fps.mobile.client.InfoProviders.SensorInfoProvider
import fps.mobile.client.utils.StringExtension.HashingMd5

internal class HardwareFingerprintProvider(
    private val context: Context
) {

    private val batteryInfoProvider: IBatteryInfoProvider = BatteryInfoProvider(context)
    private val cameraInfoProvider: ICameraInfoProvider = CameraInfoProvider(context)
    private val cpuInfoProvider: ICpuInfoProvider = CpuInfoProvider(context)
    private val memoryInfoProvider: IMemoryInfoProvider = MemoryInfoProvider(context)
    private var sensorInfoProvider: ISensorInfoProvider= SensorInfoProvider(context)

    public fun getFingerprint(): String {
        val stringBuilder = StringBuilder()
        stringBuilder.append(batteryInfoProvider.getBatteryTotalCapacity())
        stringBuilder.append(batteryInfoProvider.getBatteryHealth())

        val isCameraPresent = cameraInfoProvider.isCameraPresent()
        stringBuilder.append(isCameraPresent.toString())

        if(isCameraPresent) {
            val cameraList = cameraInfoProvider.getCameraList()
            cameraList.forEach {
                stringBuilder.append(it.cameraName).append(it.cameraType).append(it.cameraOrientation)
            }
        }

        stringBuilder.append(cpuInfoProvider.getNumberOfCores())
        stringBuilder.append(cpuInfoProvider.getAbiType())
//        stringBuilder.append(cpuInfoProvider.getCpuInfo().toString())
//        stringBuilder.append(memoryInfoProvider.getTotalRam().toString())
        stringBuilder.append(memoryInfoProvider.getExternalStorage().toString())
        stringBuilder.append(memoryInfoProvider.getInternalStorage().toString())
        val sensors = sensorInfoProvider.getSensorList()
        sensors.forEach{
            stringBuilder.append(it.sensorName).append(it.vendorName)
        }
        return stringBuilder.toString().HashingMd5()
    }
}