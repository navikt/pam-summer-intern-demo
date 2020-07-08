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
class RenholdsregisterRepository @Autowired constructor(private val downloader: RenholdsregisterDownloader) {
    private val logger: Logger = LoggerFactory.getLogger(RenholdsregisterRepository::class.java)
    private var orgnrToGodkjentStatusMap = parseAndMapRenholdsbedrifter(downloader.download("https://www.arbeidstilsynet.no/opendata/renhold.xml"))

    @Retryable(
            value = [IOException::class, NullPointerException::class, IllegalArgumentException::class],
            maxAttempts = 2,
            backoff = Backoff(delay = 3000, maxDelay = 3600000, multiplier = 1.5))
    @Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo")
    // @Scheduled(fixedRate = 500000000)
    fun scheduledDL() {
        val xmlString = downloader.download("https://www.arbeidstilsynet.no/opendata/renhold.xml")
        //logger.error("Failed to download xml")
        orgnrToGodkjentStatusMap = parseAndMapRenholdsbedrifter(xmlString)
    }

    @Recover
    fun recover() {
        val retryAnnotation = RenholdsregisterRepository::class.annotations.find { it == Retryable::class } as Retryable
        val test = scheduledDL()::class.java.getAnnotation(Retryable::class.java).maxAttempts

        logger.error("Failed to download xml after tries")
    }

    fun getAllOrgnrToGodkjentStatusMap(): Map<String,String> {
        return orgnrToGodkjentStatusMap
    }

    private fun parseAndMapRenholdsbedrifter(xmlString: String): Map<String,String> {
        return mapOrgnrToGodkjentStatus(parseRenholdsXML(xmlString))
    }
}
