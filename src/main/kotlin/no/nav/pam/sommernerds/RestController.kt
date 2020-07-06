package no.nav.pam.sommernerds

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class RestController {

    @Autowired
    var dataProvider: DownloadRenhold? = null

    @GetMapping(value = ["/{orgnummer}"])
    fun GetByOrgnummer(@PathVariable("orgnummer") nr: String): String? {
        val dict = dataProvider?.dataContainer?.data
        return dict?.get(nr)
    }

    @GetMapping("/isAlive")
    fun isAlive(): ResponseEntity<String> {
        val dict = dataProvider?.dataContainer?.data
        dict?.let{
            return ResponseEntity("OK", HttpStatus.OK)
        }
        return ResponseEntity("Internal server error", HttpStatus.INTERNAL_SERVER_ERROR)
    }

    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)
}
