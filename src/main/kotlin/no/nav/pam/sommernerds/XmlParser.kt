package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

data class DataContainer(var dictionary: MutableMap<String, String>?)

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