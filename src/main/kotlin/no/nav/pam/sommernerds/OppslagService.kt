package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service
import java.io.File

@Service
@ConditionalOnBean(DownloadRenhold::class)
class OppslagService @Autowired constructor(private val dataProvider: DownloadRenhold) {

    fun lookUpOrgnummer(orgnummer: String): Statusbedrift {
        val dict = dataProvider.dataContainer.data
        val statusbedrift = Statusbedrift(orgnummer,dict?.get(orgnummer))
        return statusbedrift

    }
}

data class Statusbedrift(val Organisasjonsnummer: String, val Status: String?)


