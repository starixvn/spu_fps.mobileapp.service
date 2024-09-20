package fps.mobile.app.data

import fps.mobile.client.DeviceIdentify
import fps.mobile.client.DeviceInfo

class InfoModel(private var name: String, private var value:String) {

    fun getName():String{
        return name
    }

    fun getValue():String{
        return value
    }

}