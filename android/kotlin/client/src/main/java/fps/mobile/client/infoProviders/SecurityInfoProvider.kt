package fps.mobile.client.infoProviders

import java.security.Security


internal data class SecurityProvider(
    val name: String,
    val info: String
)

internal interface ISecurityInfoProvider {
    fun getSecurityProviders(): List<SecurityProvider>
}

internal class SecurityInfoProvider : ISecurityInfoProvider {

    override fun getSecurityProviders(): List<SecurityProvider> {
        return Security.getProviders().map {
            SecurityProvider(it!!.name!!, it.info ?: "")
        }
    }

}