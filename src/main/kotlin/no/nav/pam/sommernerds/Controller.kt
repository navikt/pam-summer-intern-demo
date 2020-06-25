package no.nav.pam.sommernerds

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class testController {

    @GetMapping(value = ["/hello"]) // @RequestMapping(value="/",method=RequestMethod.GET)
    fun hello(): String {

        return "Hello World!!"
    }
}
