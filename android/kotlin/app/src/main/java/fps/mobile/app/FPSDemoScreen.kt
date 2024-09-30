package fps.mobile.app

enum class FPSDemoScreen(
    val title: String,
    val route: String) {

    Start(title = "", route = "start"),
    Main(title = "FPS Device Identifier", route = "main"),
    FingerprintDetail(title = "Fingerprint", route = "fingerprint/{fingerprint}"),
    DeviceInfo(title = "Device Information", route = "deviceInfo"),
    DataPackage(title = "Data Package", route = "dataPackage")
}
