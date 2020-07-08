package no.nav.pam.sommernerds

import io.mockk.mockk
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean

@TestConfiguration
class RenholdsregisterDownloaderTestConfig {
    @Bean
    fun renholdsregisterDownloaderTest() = mockk<RenholdsregisterDownloader>()
}