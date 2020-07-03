package no.nav.pam.sommernerds

import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RestController

@RestController
class Controller {

    @GetMapping(value = ["/{orgnummer}"]) // @RequestMapping(value="/",method=RequestMethod.GET)
    fun hello(@PathVariable("orgnummer") nr: String): String? {
        var actx = AnnotationConfigApplicationContext(XmlParser::class.java)
        var dataContainer = actx.getBean(DataContainer::class.java)
        return dataContainer.dictionary[nr]
    }

    @GetMapping("/isAlive")
    fun isAlive(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)

    @GetMapping("/isReady")
    fun isReady(): ResponseEntity<String> =
            ResponseEntity("OK", HttpStatus.OK)
}
