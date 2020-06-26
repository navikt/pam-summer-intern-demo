package no.nav.pam.sommernerds

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URL
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement


@JacksonXmlRootElement(localName = "ArrayOfRenholdsvirksomhet")
data class rot(
        @JacksonXmlProperty(isAttribute = false, localName = "Renholdsvirksomhet")
        var bedrifter: List<Renhold>
)


data class Renhold(
        @JacksonXmlProperty(isAttribute = false, localName = "Organisasjonsnummer")
        var orgnr: Int?,

        @JacksonXmlProperty(isAttribute = false, localName = "Status")
        var status: String?
)


fun main(args: Array<String>) {
    val mapper = XmlMapper(JacksonXmlModule().apply {
        setDefaultUseWrapper(false)
    }).registerKotlinModule()
            .configure(MapperFeature.ACCEPT_CASE_INSENSITIVE_PROPERTIES, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)

    //val rsc = Renhold::class.java.getResource("/test2.xml")
    val url = URL("https://www.arbeidstilsynet.no/opendata/renhold.xml")
    val test = mapper.readValue(url, rot::class.java).bedrifter.filter{it.orgnr == 943001820}
    print(test)
}
