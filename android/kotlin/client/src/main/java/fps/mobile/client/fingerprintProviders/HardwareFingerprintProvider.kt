package fps.mobile.client.fingerprintProviders

import android.content.Context
import fps.mobile.client.infoProviders.BatteryInfoProvider
import fps.mobile.client.infoProviders.CameraInfoProvider
import fps.mobile.client.infoProviders.CpuInfo
import fps.mobile.client.infoProviders.CpuInfoProvider
import fps.mobile.client.infoProviders.DisplayInfoProvider
import fps.mobile.client.infoProviders.GpuInfoProvider
import fps.mobile.client.infoProviders.IBatteryInfoProvider
import fps.mobile.client.infoProviders.ICameraInfoProvider
import fps.mobile.client.infoProviders.ICpuInfoProvider
import fps.mobile.client.infoProviders.IDisplayInfoProvider
import fps.mobile.client.infoProviders.IGpuInfoProvider
import fps.mobile.client.infoProviders.IMemoryInfoProvider
import fps.mobile.client.infoProviders.IOsBuildInfoProvider
import fps.mobile.client.infoProviders.ISensorInfoProvider
import fps.mobile.client.infoProviders.MemoryInfoProvider
import fps.mobile.client.infoProviders.OsBuildInfoProvider
import fps.mobile.client.infoProviders.SensorInfoProvider
import fps.mobile.client.utils.StringExtension.hashingMd5

private val CPUINFO_IGNORED_COMMON_PROPS = setOf(
    "processor",
)

private val CPUINFO_IGNORED_PER_PROC_PROPS = setOf(
    "bogomips",
    "cpu mhz",
)

class HardwareFingerprintProvider(
    context: Context
): IFingerprintProvider {

    private val batteryInfoProvider: IBatteryInfoProvider = BatteryInfoProvider(context)
    private val cameraInfoProvider: ICameraInfoProvider = CameraInfoProvider(context)
    private val cpuInfoProvider: ICpuInfoProvider = CpuInfoProvider()
    private val gpuInfoProvider: IGpuInfoProvider = GpuInfoProvider(context)
    private val memoryInfoProvider: IMemoryInfoProvider = MemoryInfoProvider(context)
    private var sensorInfoProvider: ISensorInfoProvider = SensorInfoProvider(context)
    private val displayInfoProvider: IDisplayInfoProvider = DisplayInfoProvider(context)
    private val osBuildInfoProvider: IOsBuildInfoProvider = OsBuildInfoProvider()

    override fun getFingerprint(): String {
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

        stringBuilder.append(gpuInfoProvider.getGlesVersion())

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
        return stringBuilder.toString().hashingMd5()
    }
}