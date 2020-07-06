package no.nav.pam.sommernerds

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.DependsOn
import org.springframework.stereotype.Component
import org.springframework.stereotype.Service

class OppslagService {
    @Autowired
    var dataProvider: DownloadRenhold? = null

    fun lookUpOrgnummer(orgnummer: String): String? {
        val dict = dataProvider?.dataContainer?.data
        return dict?.get(orgnummer)
    }
}


