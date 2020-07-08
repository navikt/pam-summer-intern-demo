import io.mockk.mockk
import io.mockk.mockkStatic
import no.nav.pam.sommernerds.RenholdsregisterRepository
import no.nav.pam.sommernerds.OppslagService
import no.nav.pam.sommernerds.RenholdsregisterDownloader
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest(classes = arrayOf(OppslagService::class, RenholdsregisterRepository::class, RenholdsregisterDownloader::class))
class XmlParserTest @Autowired constructor(private val oppslagService: OppslagService) {

    @Test
    fun `sjekk at en godkjent bedrift returnerer at den er godkjent`(){
        assertEquals(oppslagService.lookUpOrgnummer("943001820").Status, "Godkjent med ansatte")
    }


    /*
    @Test
    fun `sjekk at en ulovlig bedrift returnerer "ikke godkjent"`(){
        assertEquals(findBedrift("928945792", "/renholdTestData.xml"), "Ikke godkjent")
    }

    */

    @Test
    fun `sjekk at bedrift som ikke finnes i registeret returnerer at den ikke er renholdsbedrift`(){
        assertEquals(oppslagService.lookUpOrgnummer("943001").Status,null)
    }


    //TODO test downloadUpdate

    //TODO orgnr som ikke finnes

    //TODO test isReady & isAlive



}
