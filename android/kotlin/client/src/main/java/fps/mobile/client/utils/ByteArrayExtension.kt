package fps.mobile.client.utils

internal object ByteArrayExtension {

    fun ByteArray.toHexString(): String {
        return this.joinToString("") {
            java.lang.String.format("%02x", it)
        }
    }

}
