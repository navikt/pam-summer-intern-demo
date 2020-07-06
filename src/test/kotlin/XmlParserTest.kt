import no.nav.pam.sommernerds.DataContainer
import no.nav.pam.sommernerds.DownloadRenhold
import no.nav.pam.sommernerds.OppslagService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes= arrayOf(OppslagService::class, DownloadRenhold::class))
class XmlParserTest {
    @Autowired
    var oppslagService: OppslagService? = null


    @Test
    fun `sjekk at en godkjent bedrift returnerer at den er godkjent`(){
        assertEquals(oppslagService?.lookUpOrgnummer("943001820"), "Godkjent med ansatte")
    }

    /*
    @Test
    fun `sjekk at en ulovlig bedrift returnerer "ikke godkjent"`(){
        assertEquals(findBedrift("928945792", "/renholdTestData.xml"), "Ikke godkjent")
    }
    
    @Test
    fun `sjekk at bedrift som ikke finnes i registeret returnerer at den ikke er renholdsbedrift`(){
        assertEquals(findBedrift("74094722", "/renholdTestData.xml"), "Ikke renholdsbedrift")
    }


    //TODO test download

    //TODO integrasjons test

    //TODO test isReady & isAlive

    //TODO test xml2dict

     */
}
