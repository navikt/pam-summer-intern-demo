package no.nav.pam.sommernerds

import org.springframework.stereotype.Component
import java.net.URL
import java.nio.charset.Charset

@Component
class RenholdsregisterDownloader {

    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return xmlAsString
    }

}
