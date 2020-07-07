package no.nav.pam.sommernerds

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.ComponentScan
import org.springframework.scheduling.annotation.EnableScheduling

@SpringBootApplication
//@ComponentScan(basePackages = arrayOf("no.nav.pam.sommernerds"))
class Application

fun main(args: Array<String>) {
    runApplication<Application>(*args)
}