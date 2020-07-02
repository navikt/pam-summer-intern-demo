package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import java.io.File
import java.io.FileOutputStream
import java.net.URL

@Configuration
class XmlParser(map: MutableMap<String, String>) {
    val dataContainer: DataContainer
        @Bean
        get() = DataContainer(xmlToDict("/renhold.xml"))
}

data class DataContainer(val dictionary: MutableMap<String, String>)


fun downloadFromURL(link: String, path: String) {
    URL(link).openStream().use { input ->
        FileOutputStream(File(path)).use { output ->
            input.copyTo(output)
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

fun xmlToDict(path: String): MutableMap<String, String> {
    val mapper = XmlMapper(
            JacksonXmlModule().apply {
                setDefaultUseWrapper(false)
            }
    ).registerKotlinModule()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val rsc = rot::class.java.getResource(path)
    val renholdsBedrifter = mapper.readValue(rsc, rot::class.java).bedrifter
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
