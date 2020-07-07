package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.util.JSONPObject
import org.json.JSONObject
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service
import java.io.File

@Service
@ConditionalOnBean(DownloadRenhold::class)
class OppslagService {
    @Autowired
    var dataProvider: DownloadRenhold? = null

    fun lookUpOrgnummer(orgnummer: String): Statusbedrift? {
        val dict = dataProvider?.dataContainer?.data
        val statusbedrift = Statusbedrift(orgnummer,dict?.get(orgnummer))
        return statusbedrift

        //dict?.get(orgnummer)
    }
}

data class Statusbedrift(val Organisasjonsnummer: String, val Status: String?)


