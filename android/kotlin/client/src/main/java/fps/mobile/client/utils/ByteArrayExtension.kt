package fps.mobile.client.utils

object ByteArrayExtension {

    fun ByteArray.toHexString(): String {
        return this.joinToString("") {
            java.lang.String.format("%02x", it)
        }
    }

}
