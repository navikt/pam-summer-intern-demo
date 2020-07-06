package no.nav.pam.sommernerds
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.net.URL
import java.nio.charset.Charset

@Component
class DownloadRenhold {
    var logger: Logger = LoggerFactory.getLogger(DownloadRenhold::class.java)
    var _dataContainer: DataContainer? = null
    var dataContainer = _dataContainer
        get() = _dataContainer

    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return xmlAsString
    }


    @Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo")
    fun scheduledDL() {
        val xmlString = download("https://www.arbeidstilsynet.no/opendata/renhold.xml")
        _dataContainer = DataContainer(xmlToDict(xmlString))
    }

}




