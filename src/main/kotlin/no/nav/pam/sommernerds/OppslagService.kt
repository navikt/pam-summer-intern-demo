package no.nav.pam.sommernerds


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OppslagService @Autowired constructor(private val dataProvider: RenholdsregisterDownloader) {

    fun lookUpOrgnummer(orgnummer: String): Statusbedrift {
        val orgnrToGodkjentStatusMap = dataProvider.getAllOrgnrToGodkjentStatusMap()
        val statusbedrift = Statusbedrift(orgnummer, orgnrToGodkjentStatusMap.get(orgnummer))
        return statusbedrift
    }
}


