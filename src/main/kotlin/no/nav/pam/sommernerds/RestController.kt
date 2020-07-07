package no.nav.pam.sommernerds


import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController @Autowired constructor(private val oppslagService: OppslagService) {

    @GetMapping(value = ["/{orgnummer}"], produces = [ MediaType.APPLICATION_JSON_VALUE])
    fun GetByOrgnummer(@PathVariable("orgnummer") nr: String): Statusbedrift? {
        return oppslagService.lookUpOrgnummer(nr)
    }

    @GetMapping("/isAlive")
    fun isAlive(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)

    /*
    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> {
        val dict = oppslagService?.dataProvider?.dataContainer?.data
        dict?.let{
            return ResponseEntity("OK", HttpStatus.OK)
        }
        return ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
    }
     */

    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)

}
