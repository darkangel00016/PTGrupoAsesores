package com.lerny.ptgrupoasesores.settings

class GeneralSettings {

    companion object {

        private val environment: SiteEnvironment = SiteEnvironment.PROD
        private val domain: HashMap<SiteEnvironment, String> = hashMapOf(
            SiteEnvironment.PROD to "https://00672285.us-south.apigw.appdomain.cloud/demo-gapsi/",
        )

        fun getDomain(): String {
            return domain[environment]!!
        }

        fun getDefaultHeaders (): HashMap<String, String> {
            return hashMapOf(
                "X-IBM-Client-Id" to "adb8204d-d574-4394-8c1a-53226a40876e"
            )
        }
    }

}

enum class SiteEnvironment{
    PROD,
}