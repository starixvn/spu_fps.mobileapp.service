package fps.mobile.client.infoProviders

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.BatteryManager

internal interface IBatteryInfoProvider {
    fun getBatteryHealth(): String?
    fun getBatteryTotalCapacity(): String
    fun getBatteryLevel(): String
}

private const val POWER_PROFILE_CLASS_NAME = "com.android.internal.os.PowerProfile"
private const val BATTERY_CAPACITY_METHOD_NAME = "getBatteryCapacity"

internal class BatteryInfoProvider(
    private val context: Context
): IBatteryInfoProvider {

    override fun getBatteryHealth(): String {
        val intent = context
            .registerReceiver(null, IntentFilter(Intent.ACTION_BATTERY_CHANGED))!!

        val health = intent.getIntExtra(BatteryManager.EXTRA_HEALTH, -1)

        return batteryHealthStringDescription(health)
    }

    @SuppressLint("PrivateApi")
    override fun getBatteryTotalCapacity(): String {
        val mPowerProfile = Class.forName(POWER_PROFILE_CLASS_NAME)
            .getConstructor(Context::class.java)
            .newInstance(context)
        val batteryCapacity = Class
            .forName(POWER_PROFILE_CLASS_NAME)
            .getMethod(BATTERY_CAPACITY_METHOD_NAME)
            .invoke(mPowerProfile) as Double
        return batteryCapacity.toString()
    }

    override fun getBatteryLevel(): String {
        val intentFilter = IntentFilter(Intent.ACTION_BATTERY_CHANGED)
        val batteryStatus = context.registerReceiver(null, intentFilter)
        val level = batteryStatus?.getIntExtra(BatteryManager.EXTRA_LEVEL, -1) ?: -1
        val scale = batteryStatus?.getIntExtra(BatteryManager.EXTRA_SCALE, -1) ?: -1
        if (level != -1 && scale != -1) {
            return (level.toFloat() / scale.toFloat() * 100).toInt().toString()
        }

        return "unknown"
    }

    private fun batteryHealthStringDescription(batteryHealth: Int) = when(batteryHealth) {
        BatteryManager.BATTERY_HEALTH_GOOD -> "good"
        BatteryManager.BATTERY_HEALTH_OVERHEAT -> "overheat"
        BatteryManager.BATTERY_HEALTH_COLD -> "cold"
        BatteryManager.BATTERY_HEALTH_DEAD -> "dead"
        BatteryManager.BATTERY_HEALTH_OVER_VOLTAGE -> "over voltage"
        BatteryManager.BATTERY_HEALTH_UNSPECIFIED_FAILURE -> "unspecified failure"
        else -> "unknown"
    }
}