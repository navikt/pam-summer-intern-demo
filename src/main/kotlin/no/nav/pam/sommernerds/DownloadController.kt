package no.nav.pam.sommernerds


import org.springframework.scheduling.annotation.Scheduled
import java.io.File
import java.io.FileOutputStream
import java.net.URL


@Scheduled(cron = "0 0 5 * * *", zone = "Europe/Oslo" )
fun download(link: String, path: String) {
    URL(link).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
        }
    }
}



/*fun main(args: Array<String>) {
    download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
}*/
