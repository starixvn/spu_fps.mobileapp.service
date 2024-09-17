package fps.mobile.client.fingerprintProviders

internal interface IFingerprintProvider {
    fun getFingerprint(): String
}