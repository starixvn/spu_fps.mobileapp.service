package fps.mobile.client.FingerprintProviders

import android.content.Context
import fps.mobile.client.InfoProviders.BatteryInfoProvider
import fps.mobile.client.InfoProviders.CameraInfoProvider
import fps.mobile.client.InfoProviders.CpuInfo
import fps.mobile.client.InfoProviders.CpuInfoProvider
import fps.mobile.client.InfoProviders.DisplayInfoProvider
import fps.mobile.client.InfoProviders.ICpuInfoProvider
import fps.mobile.client.InfoProviders.IBatteryInfoProvider
import fps.mobile.client.InfoProviders.ICameraInfoProvider
import fps.mobile.client.InfoProviders.IDisplayInfoProvider
import fps.mobile.client.InfoProviders.IMemoryInfoProvider
import fps.mobile.client.InfoProviders.IOsBuildInfoProvider
import fps.mobile.client.InfoProviders.ISensorInfoProvider
import fps.mobile.client.InfoProviders.MemoryInfoProvider
import fps.mobile.client.InfoProviders.OsBuildInfoProvider
import fps.mobile.client.InfoProviders.SensorInfoProvider
import fps.mobile.client.utils.StringExtension.HashingMd5

private val CPUINFO_IGNORED_COMMON_PROPS = setOf(
    "processor",
)

private val CPUINFO_IGNORED_PER_PROC_PROPS = setOf(
    "bogomips",
    "cpu mhz",
)

internal class HardwareFingerprintProvider(
    private val context: Context
) {

    private val batteryInfoProvider: IBatteryInfoProvider = BatteryInfoProvider(context)
    private val cameraInfoProvider: ICameraInfoProvider = CameraInfoProvider(context)
    private val cpuInfoProvider: ICpuInfoProvider = CpuInfoProvider(context)
    private val memoryInfoProvider: IMemoryInfoProvider = MemoryInfoProvider(context)
    private var sensorInfoProvider: ISensorInfoProvider = SensorInfoProvider(context)
    private val displayInfoProvider: IDisplayInfoProvider = DisplayInfoProvider(context)
    private val osBuildInfoProvider: IOsBuildInfoProvider = OsBuildInfoProvider()

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

        val rawCpuInfo = cpuInfoProvider.getCpuInfo()
        val cpuInfo = CpuInfo(
            commonInfo = rawCpuInfo.commonInfo.filter { it.first.lowercase() !in CPUINFO_IGNORED_COMMON_PROPS },
            perProcessorInfo = rawCpuInfo.perProcessorInfo.map { procInfo -> procInfo.filter { it.first.lowercase() !in CPUINFO_IGNORED_PER_PROC_PROPS } }
        )

        stringBuilder.append(cpuInfo.commonInfo.toString())
        stringBuilder.append(cpuInfo.perProcessorInfo.toString())

        stringBuilder.append(memoryInfoProvider.getTotalRamInMB().toString())

        stringBuilder.append(memoryInfoProvider.getExternalStorage().toString())
        stringBuilder.append(memoryInfoProvider.getInternalStorage().toString())
        val sensors = sensorInfoProvider.getSensorList()
        sensors.forEach{
            stringBuilder.append(it.sensorName).append(it.vendorName)
        }

        val displayInfo = displayInfoProvider.getDisplayInfo()
        stringBuilder.append(displayInfo.heightPixel).append(displayInfo.widthPixel).append(displayInfo.densityDpi)

        stringBuilder.append(osBuildInfoProvider.getManufacturerName())
        stringBuilder.append(osBuildInfoProvider.getModelName())
        return stringBuilder.toString().HashingMd5()
    }
}