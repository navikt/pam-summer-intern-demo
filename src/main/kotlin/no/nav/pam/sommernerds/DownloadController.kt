package no.nav.pam.sommernerds
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Component
class DownloadRenhold {
    var count: Int = 0
    var logger: Logger = LoggerFactory.getLogger(DownloadRenhold::class.java)
    var _dataContainer: DataContainer? = null
    var dataContainer = _dataContainer
        get() = _dataContainer


    fun download(link: String, path: String) {
        URL(link).openStream().use { input ->
            FileOutputStream(File(path)).use { output ->
                input.copyTo(output)
            }
        }
    }


    @Scheduled(fixedRate = 10000)
    fun scheduledDL() {
        if (count == 0) {
            _dataContainer = DataContainer(xmlToDict("/renhold.xml"))
            count = 1
            logger.error("0")
        }
        else {
            _dataContainer = DataContainer(xmlToDict("/renhold2.xml"))
            count = 0
            logger.error("1")
        }
    }

}



