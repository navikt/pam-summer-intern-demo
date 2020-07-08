package no.nav.pam.sommernerds

import com.fasterxml.jackson.annotation.JsonProperty
import com.fasterxml.jackson.annotation.JsonPropertyOrder
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController @Autowired constructor(private val repository: RenholdsregisterRepository) {

    @GetMapping(value = ["/{orgnummer}"], produces = [MediaType.APPLICATION_JSON_VALUE])
    fun GetGodkjentStatusByOrgnummer(@PathVariable("orgnummer") nr: String): Statusbedrift? {
        val godkjenningStatus = repository.getGodkjentStatus(nr)
        return Statusbedrift(nr, godkjenningStatus)
    }

    @GetMapping("/isAlive")
    fun isAlive(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)

    /*
    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> {
        if (renholdsregisterDownloader.getRenholdXMLString() == "") {
            return ResponseEntity("NOT OK", HttpStatus.INTERNAL_SERVER_ERROR)
        }else{
            return ResponseEntity("OK", HttpStatus.OK)
        }
    }
     */

    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> {
        return ResponseEntity("OK", HttpStatus.OK)
    }

    @JsonPropertyOrder("organisasjonsnummer", "status")
    data class Statusbedrift(
            @JsonProperty("organisasjonsnummer")
            val Organisasjonsnummer: String,

            @JsonProperty("status")
            val Status: String?
    )
}