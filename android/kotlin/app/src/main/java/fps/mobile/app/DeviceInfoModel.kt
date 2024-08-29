package fps.mobile.app

class DeviceInfoModel(private var propertyName: String, private var propertyValue:String) {
    fun getPropertyName():String{
        return propertyName
    }

    fun getPropertyValue():String{
        return propertyValue
    }

}