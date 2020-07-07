package no.nav.pam.sommernerds

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class OppslagService @Autowired constructor(private val dataProvider: DownloadRenhold) {

    fun lookUpOrgnummer(orgnummer: String): Statusbedrift {
        val dict = dataProvider.dataContainer.data
        val statusbedrift = Statusbedrift(orgnummer, dict?.get(orgnummer))
        return statusbedrift
    }
}

@JsonPropertyOrder("organisasjonsnummer", "status")
data class Statusbedrift(
        @JsonProperty("organisasjonsnummer")
        val Organisasjonsnummer: String,

        @JsonProperty("status")
        val Status: String?
)
