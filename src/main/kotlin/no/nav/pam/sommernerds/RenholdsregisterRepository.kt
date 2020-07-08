package no.nav.pam.sommernerds

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import java.io.IOException


@Repository
@EnableRetry
class RenholdsregisterRepository {
    private var orgnrToGodkjentStatusMap: Map<String, String> = emptyMap()

    fun updateOrgnrToGodkjentStatusMap(orgnrToGodkjentStatusMap: Map<String, String>) {
        this.orgnrToGodkjentStatusMap = orgnrToGodkjentStatusMap
    }

    fun getGodkjentStatus(orgnr: String) = orgnrToGodkjentStatusMap[orgnr]
}
