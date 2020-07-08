package no.nav.pam.sommernerds

import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.EnableRetry
import org.springframework.retry.annotation.Recover
import org.springframework.retry.annotation.Retryable
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Repository
import java.io.IOException
import java.net.URL
import java.nio.charset.Charset

//data class DataContainer(private val data: Map<String, String>?)

@Repository
@EnableRetry
class RenholdsregisterDownloader {
    private val logger: Logger = LoggerFactory.getLogger(RenholdsregisterDownloader::class.java)
    private var orgnrToGodkjentStatusMap = parseAndMapRenholdsbedrifter(download("https://www.arbeidstilsynet.no/opendata/renhold.xml"))
    private var renholdXMLString: String = ""


    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        renholdXMLString = xmlAsString
        return xmlAsString
    }

    @Retryable(
        value = [IOException::class, NullPointerException::class, IllegalArgumentException::class],
        maxAttempts = 2,
        backoff = Backoff(delay = 3000, maxDelay = 3600000, multiplier = 1.5))
    @Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo")
    // @Scheduled(fixedRate = 500000000)
    fun scheduledDL(link: String) {
        val xmlString = download(link)
        //logger.error("Failed to download xml")
        orgnrToGodkjentStatusMap = parseAndMapRenholdsbedrifter(xmlString)
    }

    @Recover
    fun recover() {
        logger.error("Failed to download xml after tries")
    }

    fun getAllOrgnrToGodkjentStatusMap(): Map<String,String> {
        return orgnrToGodkjentStatusMap
    }

    private fun parseAndMapRenholdsbedrifter(xmlString: String): Map<String,String> {
        return mapOrgnrToGodkjentStatus(parseRenholdsXML(xmlString))
    }

    fun getRenholdXMLString(): String {
        return renholdXMLString
    }
}
