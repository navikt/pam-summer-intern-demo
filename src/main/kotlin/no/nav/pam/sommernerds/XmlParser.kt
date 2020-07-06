package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.AnnotationConfigApplicationContext
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component
import java.io.File
import java.io.FileOutputStream
import java.net.URL
import java.nio.charset.Charset
import javax.xml.crypto.Data

/*
@Configuration
class XmlParser(map: MutableMap<String, String>) {
    val dataContainer: DataContainer
        @Bean
        get() = DataContainer(xmlToDict("/renhold.xml"))
}

 */

data class DataContainer(var dictionary: MutableMap<String, String>?)

@Component
class DownloadRenhold {
    var count: Int = 0
    var logger: Logger = LoggerFactory.getLogger(DownloadRenhold::class.java)
    var _dataContainer: DataContainer? = null
    var dataContainer = _dataContainer
        get() = _dataContainer

    fun download(link: String): String {
        val xmlAsString = URL(link).readText(Charset.forName("UTF-8"))
        return xmlAsString
    }

    @Scheduled(fixedRate = 10000)
    fun scheduledDL() {
        //download("https://www.arbeidstilsynet.no/opendata/renhold.xml", "src/main/resources/renhold.xml")
        if (count == 0) {
            val xmlString1 = File("src/main/resources/renhold2.xml").readText(Charset.forName("UTF-8"))
            _dataContainer = DataContainer(xmlToDict(xmlString1))
            count = 1
            logger.error("0")
        }
        else {
            val xmlString2 = download("https://www.arbeidstilsynet.no/opendata/renhold.xml")
            _dataContainer = DataContainer(xmlToDict(xmlString2))
            count = 0
            logger.error("1")
        }
    }

}




@JacksonXmlRootElement(localName = "ArrayOfRenholdsvirksomhet")
data class rot(
    @JacksonXmlProperty(isAttribute = false, localName = "Renholdsvirksomhet")
    val bedrifter: List<Renhold>
)

data class Renhold(
    @JacksonXmlProperty(isAttribute = false, localName = "Organisasjonsnummer")
    val orgnr: String,

    @JacksonXmlProperty(isAttribute = false, localName = "Status")
    val status: String,

    @JacksonXmlProperty(isAttribute = false, localName = "Underavdelinger")
    val underavdeling: Underavdeling?

)

data class Underavdeling(
    @JacksonXmlProperty(isAttribute = false, localName = "Avdeling")
    val avdelinger: List<Avdeling>
)

data class Avdeling(
    @JacksonXmlProperty(isAttribute = false, localName = "Organisasjonsnummer")
    val avdorgnr: String

)

fun findBedrift(orgnummer: String, path: String): String {
    val mapper = XmlMapper(
        JacksonXmlModule().apply {
            setDefaultUseWrapper(false)
        }
    ).registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val rsc = rot::class.java.getResource(path)
    val test = mapper.readValue(rsc, rot::class.java).bedrifter
    for (bedrift in test) {
        if (bedrift.orgnr == orgnummer) {
            return bedrift.status
        } else {
            bedrift.underavdeling?.avdelinger?.forEach() {
                if (it.avdorgnr == orgnummer) {
                    return bedrift.status
                }
            }
        }
    }
    return "Ikke renholdsbedrift"
}

fun xmlToDict(xmlString: String): MutableMap<String, String>? {

    val mapper = XmlMapper(
            JacksonXmlModule().apply {
                setDefaultUseWrapper(false)
            }
    ).registerKotlinModule()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val renholdsBedrifter = mapper.readValue(xmlString, rot::class.java).bedrifter
    val bedriftMap = mutableMapOf<String, String>()
    for (bedrift in renholdsBedrifter) {
        val bedriftStatus = bedrift.status
        bedriftMap[bedrift.orgnr] = bedriftStatus
        bedrift.underavdeling?.avdelinger?.forEach() {
            bedriftMap[it.avdorgnr] = bedriftStatus
        }
    }
    return bedriftMap
}


/*fun main(args: Array<String>) {
    *//*
    println(findBedrift("952283200", "/renhold.xml"))

    println(findBedrift("972094722", "/renhold.xml"))
     *//*
    val dict = xmlToDict("/renhold.xml")
    println(dict["952283200"])
}*/
