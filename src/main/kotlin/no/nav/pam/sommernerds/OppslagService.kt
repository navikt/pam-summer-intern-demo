package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule

/*
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

 */