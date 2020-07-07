package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

@JacksonXmlRootElement(localName = "ArrayOfRenholdsvirksomhet")
data class Rot(
    @JacksonXmlProperty(isAttribute = false, localName = "Renholdsvirksomhet")
    val bedrifter: List<Renholdsbedrift>
)

data class Renholdsbedrift(
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

fun parseRenholdsXML(xmlString: String): List<Renholdsbedrift> {

    val mapper = XmlMapper(
        JacksonXmlModule().apply {
            setDefaultUseWrapper(false)
        }
    ).registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
    val renholdsBedrifter = mapper.readValue(xmlString, Rot::class.java).bedrifter

    return renholdsBedrifter
}

fun mapOrgnrToGodkjentStatus(renholdsBedrifter: List<Renholdsbedrift>): Map<String,String>{

    val orgnrToGodkjentStatusMap = mutableMapOf<String, String>()
    for (bedrift in renholdsBedrifter) {
        val bedriftStatus = bedrift.status
        orgnrToGodkjentStatusMap[bedrift.orgnr] = bedriftStatus
        bedrift.underavdeling?.avdelinger?.forEach() {
            orgnrToGodkjentStatusMap[it.avdorgnr] = bedriftStatus
        }
    }
    return orgnrToGodkjentStatusMap
}
