package no.nav.pam.sommernerds

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

fun main(args: Array<String>) {
    download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
}
