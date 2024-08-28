package fps.mobile.app

import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import fps.mobile.client.DeviceIdentify

class MainActivity : AppCompatActivity(), View.OnClickListener {
    private lateinit var txtName: EditText
    private lateinit var btnSubmit: Button
    private lateinit var tvResult: TextView
    private lateinit var tvDeviceId: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_main)
        ViewCompat.setOnApplyWindowInsetsListener(findViewById(R.id.main)) { v, insets ->
            val systemBars = insets.getInsets(WindowInsetsCompat.Type.systemBars())
            v.setPadding(systemBars.left, systemBars.top, systemBars.right, systemBars.bottom)
            insets
        }

        txtName = findViewById(R.id.txtInputText)
        btnSubmit = findViewById(R.id.btnSubmit)
        tvResult = findViewById(R.id.txtResult)
        tvDeviceId = findViewById(R.id.txtDeviceId)

        btnSubmit.setOnClickListener(this)


    }

    override fun onClick(v: View?) {
        when(v?.id){
            R.id.btnSubmit->{
                val androidId = DeviceIdentify.getAndroidId(this)
                tvResult.text = "Hello ${txtName.text}"
                tvDeviceId.text = "Your device Id is ${androidId}"
            }
        }
    }
}