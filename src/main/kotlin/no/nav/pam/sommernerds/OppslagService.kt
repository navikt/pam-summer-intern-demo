package no.nav.pam.sommernerds

import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.dataformat.xml.JacksonXmlModule
import com.fasterxml.jackson.dataformat.xml.XmlMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component

@Component
class OppslagService {
    @Autowired
    var dataProvider: DownloadRenhold? = null

    fun lookUpOrgnummer(orgnummer: String): String? {
        val dict = dataProvider?.dataContainer?.data
        return dict?.get(orgnummer)
    }
}


