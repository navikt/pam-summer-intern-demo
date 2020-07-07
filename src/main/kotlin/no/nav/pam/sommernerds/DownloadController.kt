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

data class DataContainer(var data: MutableMap<String, String>?)

@Repository
@EnableRetry
class DownloadRenhold {
    private val logger: Logger = LoggerFactory.getLogger(DownloadRenhold::class.java)
    private var _dataContainer: DataContainer = DataContainer(xmlToDict(download("https://www.arbeidstilsynet.no/opendata/renhold.xml")))
    val dataContainer = _dataContainer

    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return xmlAsString
    }

    @Retryable(
        value = [IOException::class, NullPointerException::class, IllegalArgumentException::class],
        maxAttempts = 2,
        backoff = Backoff(delay = 3000, maxDelay = 3600000, multiplier = 1.5)
    )
    @Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo")
    // @Scheduled(fixedRate = 500000000)
    fun scheduledDL() {
        val xmlString = download("https://www.arbeidstilsynet.no/opendata/renhold.xml")
        logger.error("Failed to download xml")
        _dataContainer = DataContainer(xmlToDict(xmlString))
    }

    @Recover
    fun recover() {
        val retryAnnotation = DownloadRenhold::class.annotations.find { it == Retryable::class } as Retryable
        val test = scheduledDL()::class.java.getAnnotation(Retryable::class.java).maxAttempts

        logger.error("Failed to download xml after tries")
    }
}
