package fps.mobile.client.utils

object ByteArrayExtension {

    public fun ByteArray.toHexString(): String {
        return this.joinToString("") {
            java.lang.String.format("%02x", it)
        }
    }

}
