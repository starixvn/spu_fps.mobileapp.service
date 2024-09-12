package fps.mobile.client.infoProviders

import java.security.Security


data class SecurityProvider(
    val name: String,
    val info: String
)

interface ISecurityInfoProvider {
    fun getSecurityProviders(): List<SecurityProvider>
}

class SecurityInfoProvider : ISecurityInfoProvider {

    override fun getSecurityProviders(): List<SecurityProvider> {
        return Security.getProviders().map {
            SecurityProvider(it!!.name!!, it.info ?: "")
        }
    }

}