package no.nav.pam.sommernerds
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.net.URL


fun download(link: String, path: String) {
    URL(link).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
        }
    }
}

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
    download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
}



