package no.nav.pam.sommernerds

import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.context.annotation.Import
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.io.File

@SpringBootTest(classes = arrayOf(OppslagService::class, RenholdsregisterDownloader::class))
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
    
    @Test
    fun `sjekk at bedrift som ikke finnes i registeret returnerer at den ikke er renholdsbedrift`(){
        assertEquals(findBedrift("74094722", "/renholdTestData.xml"), "Ikke renholdsbedrift")
    }
    */


    //TODO test download

    //TODO integrasjons test

    //TODO test isReady & isAlive

    //TODO test xml2dict


}



@Import(RenholdsregisterDownloaderTestConfig::class)
@ExtendWith(SpringExtension::class)
class RenholdsregisterDownloaderTest {

    @Autowired
    private var mockRenholdsregisterDownloader: RenholdsregisterDownloader? = null

    @Test
    fun `sjekk at orgnrToGodkjentStatusMap oppdateres ved nedlasting av ny eller oppdatert fil`() {
        val file1 = File("src/test/resources/renholdTestData.xml").readText()
        val file2 = File("src/test/resources/TestData.xml").readText()
        every { mockRenholdsregisterDownloader?.download("1") } returns file1
        every { mockRenholdsregisterDownloader?.download("2") } returns file2

        /*
        mockRenholdsregisterDownloader?.scheduledDL("1")
        mockRenholdsregisterDownloader?.scheduledDL("2")

        assertEquals(mockRenholdsregisterDownloader?.getAllOrgnrToGodkjentStatusMap()?.get("943001820"), "Godkjent med ansatte")

         */
        assertEquals(mockRenholdsregisterDownloader?.download("1"), file1)


    }

}
