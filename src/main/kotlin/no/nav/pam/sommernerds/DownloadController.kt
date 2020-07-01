package no.nav.pam.sommernerds

import org.apache.http.client.methods.HttpGet
import org.apache.http.impl.client.HttpClientBuilder
import org.springframework.core.io.ByteArrayResource
import org.springframework.core.io.InputStreamResource
import org.springframework.http.HttpHeaders
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.io.File
import java.io.FileOutputStream
import java.io.InputStream
import java.net.URL
import java.net.http.HttpClient
import java.nio.file.Files
import java.nio.file.Path
import javax.annotation.Resource


fun download(link: String, path: String) {
    URL(link).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
        }
    }
}

/*@RequestMapping(path = ["/{download}"],  method = [RequestMethod.GET])
fun downloadSpring(path : Path) : ResponseEntity<Resource> {
    val url = "https://www.arbeidstilsynet.no/opendata/renhold.xml";
    val httpClient = HttpClientBuilder.create().build();
    val request = HttpGet(url)

    val response = httpClient.execute(request);
    if (response.statusLine.statusCode == 200){
        val resource = ByteArrayResource(Files.readAllBytes(path))
        return ResponseEntity.ok()
                .headers(headers)
                .contentLength(file.length())
                .contentType(MediaType.parseMediaType("hahaha"))
                .body(response.entity.content)
    }else{
        return ResponseEntity.notFound();
    }

}*/


fun main(args: Array<String>) {
    download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
}
