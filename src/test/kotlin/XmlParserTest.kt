import no.nav.pam.sommernerds.findBedrift
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class XmlParserTest {
    @Test
    fun `sjekk at en godkjent bedrift returnerer at den er godkjent`(){
        assertEquals(findBedrift("974694722", "/renholdTestData.xml"), "Godkjent med ansatte")
    }

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
}
