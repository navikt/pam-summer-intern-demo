package no.nav.pam.sommernerds


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OppslagService @Autowired constructor(private val dataProvider: RenholdsregisterRepository) {

    fun lookUpOrgnummer(orgnummer: String): RestController.Statusbedrift {
        val orgnrToGodkjentStatusMap = dataProvider.getAllOrgnrToGodkjentStatusMap()
        val statusbedrift = RestController.Statusbedrift(orgnummer, orgnrToGodkjentStatusMap.get(orgnummer))
        return statusbedrift
    }

    fun getMap(): Map<String,String>{
        return dataProvider.getAllOrgnrToGodkjentStatusMap()
    }
}


