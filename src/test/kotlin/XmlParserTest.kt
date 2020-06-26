import no.nav.pam.sommernerds.findBedrift
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class XmlParserTest {
    @Test
    fun `verifiser godkjent bedrift`(){
        val noe = javaClass.getResource("/TestData.xml").readText()
        assertEquals(findBedrift(972094722, noe), "Godkjent med ansatte")
    }

    @Test
    fun `verifiser ukjent bedrift`(){
        val noe = javaClass.getResource("/TestData.xml").readText()
        assertEquals(findBedrift(69420, noe), "Ikke renholdsbedrift")
    }

    @Test
    fun `verifiser ulovlig bedrift`(){
        val noe = javaClass.getResource("/TestData.xml").readText()
        assertEquals(findBedrift(958942192, noe), "Ikke godkjent")
    }
}