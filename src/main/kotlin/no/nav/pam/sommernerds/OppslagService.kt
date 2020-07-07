package no.nav.pam.sommernerds

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean
import org.springframework.stereotype.Service

@Service
@ConditionalOnBean(DownloadRenhold::class)
class OppslagService @Autowired constructor(internal val dataProvider: DownloadRenhold) {

    fun lookUpOrgnummer(orgnummer: String): String? {
        val dict = dataProvider.dataContainer.data
        return dict?.get(orgnummer)
    }
}


