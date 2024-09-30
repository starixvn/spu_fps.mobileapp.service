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

    private var securityProviders: List<SecurityProvider>? = null

    override fun getSecurityProviders(): List<SecurityProvider> {
        if(securityProviders == null)
            securityProviders = securityProviders()

        return securityProviders!!
    }

    private fun securityProviders(): List<SecurityProvider> {
        return Security.getProviders().map {
            SecurityProvider(it!!.name!!, it.info ?: "")
        }
    }
}