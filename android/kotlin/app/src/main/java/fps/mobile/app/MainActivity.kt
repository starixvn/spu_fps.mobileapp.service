package fps.mobile.app

import android.app.ActivityManager
import android.bluetooth.BluetoothClass.Device
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.hardware.Sensor
import android.hardware.SensorManager
import android.os.Build
import android.os.Bundle
import android.util.DisplayMetrics
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.GridView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.viewpager2.widget.ViewPager2
import com.google.android.material.tabs.TabLayout
import com.google.android.material.tabs.TabLayoutMediator
import fps.mobile.client.DeviceIdentify
import java.util.Locale
import java.util.TimeZone

class MainActivity : AppCompatActivity() {
    /*private lateinit var txtName: EditText
    private lateinit var btnSubmit: Button
    private lateinit var tvResult: TextView
    private lateinit var gvDeviceInfo: GridView*/

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        //txtName = findViewById(R.id.txtInputText)
        //btnSubmit = findViewById(R.id.btnSubmit)
        //tvResult = findViewById(R.id.txtResult)
       // gvDeviceInfo = findViewById(R.id.gvDeviceInfo)

        val tabLayout = findViewById<TabLayout>(R.id.tabLayout)
        val viewPager = findViewById<ViewPager2>(R.id.viewPager)

        // Set up the ViewPager with the adapter
        val adapter = ViewPagerAdapter(this)
        viewPager.adapter = adapter
// Attach TabLayout with ViewPager2
        TabLayoutMediator(tabLayout, viewPager) { tab, position ->
            tab.text = when (position) {
                0 -> "Device ID"
                1 -> "FingerPrint"
                else -> "Device ID"
            }
        }.attach()

        //btnSubmit.setOnClickListener(this)

    }

    /*override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmit->{
                tvResult.text = "Hello ${txtName.text}"

               *//* val deviceInfoList = DeviceIdentify.getDeviceInfo(this)
                val newDeviceInfoList: List<DeviceInfoModel> = deviceInfoList.map {
                    DeviceInfoModel(it.name, it.value)
                }
                val adapter = PropertyAdapter(this, newDeviceInfoList )
                gvDeviceInfo.adapter = adapter*//*
            }
        }
    }*/


}