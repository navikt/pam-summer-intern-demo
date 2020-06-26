package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URL

@JacksonXmlRootElement(localName = "ArrayOfRenholdsvirksomhet")
data class rot(
    @JacksonXmlProperty(isAttribute = false, localName = "Renholdsvirksomhet")
    val bedrifter: List<Renhold>
)

data class Renhold(
    @JacksonXmlProperty(isAttribute = false, localName = "Organisasjonsnummer")
    val orgnr: Int,

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
    val avdorgnr: Int

)

fun findBedrift(orgnummer: Int, xml: String): String {
    val mapper = XmlMapper(
        JacksonXmlModule().apply {
            setDefaultUseWrapper(false)
        }
    ).registerKotlinModule()
        .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
        .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    val url = URL("https://www.arbeidstilsynet.no/opendata/renhold.xml")
    // val test = mapper.readValue(url, rot::class.java).bedrifter.filter { it.orgnr == orgnummer }
    val test = mapper.readValue(xml, rot::class.java).bedrifter
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

fun main(args: Array<String>) {
    println(findBedrift(952283200, "/test2.xml"))
}
