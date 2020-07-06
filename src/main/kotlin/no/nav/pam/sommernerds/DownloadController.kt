package no.nav.pam.sommernerds
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.BufferedReader
import java.io.File
import java.io.FileOutputStream
import java.lang.StringBuilder
import java.net.URL
import java.nio.charset.Charset

/*
fun download(link: String) {
    val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
    println(xmlAsString)
}
 */

/*
@Component
class MyClass {
    var logger: Logger = LoggerFactory.getLogger(MyClass::class.java)

    @Scheduled(fixedRate = 5000)
    fun foo() {
        logger.error("hei :)")
    }
}
*/




fun main(args: Array<String>) {
    val file = File("src/main/resources/renhold2.xml").readText(Charset.forName("UTF-8"))
    println(file)
}





