package no.nav.pam.sommernerds

import org.springframework.stereotype.Component
import java.net.URL
import java.nio.charset.Charset

@Component
class RenholdsregisterDownloader {

    fun download(link: String): Map<String, String> {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return parseAndMapRenholdsbedrifter(xmlAsString)
    }

    private fun parseAndMapRenholdsbedrifter(xmlString: String): Map<String, String> {
        return mapOrgnrToGodkjentStatus(parseRenholdsXML(xmlString))
    }

}
