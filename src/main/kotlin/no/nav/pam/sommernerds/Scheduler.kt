package no.nav.pam.sommernerds


import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service
import java.io.IOException

@Component
@EnableRetry
class Scheduler @Autowired constructor(private val repository: RenholdsregisterRepository,
                                       private val downloader: RenholdsregisterDownloader) {

    private val logger: Logger = LoggerFactory.getLogger(RenholdsregisterRepository::class.java)

    @Retryable(
            value = [IOException::class, NullPointerException::class, IllegalArgumentException::class],
            maxAttempts = 2,
            backoff = Backoff(delay = 3000, maxDelay = 3600000, multiplier = 1.5))
    @Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo")
    fun scheduledDL(link: String) {
        val orgnrToGodkjentStatusMap = downloader.download(link)
        repository.updateOrgnrToGodkjentStatusMap(orgnrToGodkjentStatusMap)
    }

    @Recover
    fun recover() {
        logger.error("Failed to download renhold.xml: Download timed out")
    }
}


