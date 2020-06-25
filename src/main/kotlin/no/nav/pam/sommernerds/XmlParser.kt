package no.nav.pam.sommernerds

import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import java.net.URL
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlProperty
import com.fasterxml.jackson.dataformat.xml.annotation.JacksonXmlRootElement


@JacksonXmlRootElement(localName = "Renholdsvirksomhet")
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

    val rsc = Renhold::class.java.getResource("/test.xml")
    println(mapper.readValue(rsc, Renhold::class.java))
}
