package no.nav.pam.sommernerds
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.net.URL
import java.nio.charset.Charset

@Component
class DownloadRenhold {
    var count: Int = 0
    var logger: Logger = LoggerFactory.getLogger(DownloadRenhold::class.java)
    var _dataContainer: DataContainer? = null
    var dataContainer = _dataContainer
        get() = _dataContainer

    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return xmlAsString
    }

    @Scheduled(fixedRate = 10000)
    fun scheduledDL() {
        //download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
        if (count == 0) {
            val xmlString1 = File("src/main/resources/renhold2.xml").readText(Charset.forName("UTF-8"))
            _dataContainer = DataContainer(xmlToDict(xmlString1))
            count = 1
            logger.error("0")
        }
        else {
            val xmlString2 = download("https://www.arbeidstilsynet.no/opendata/renhold.xml")
            _dataContainer = DataContainer(xmlToDict(xmlString2))
            count = 0
            logger.error("1")
        }
    }

}




