import no.nav.pam.sommernerds.findBedrift
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class XmlParserTest {
    @Test
    fun `verifiser godkjent bedrift`() {
        assertEquals(findBedrift(972094722, "/renhold.xml"), "Godkjent med ansatte")
    }

    @Test
    fun `verifiser ukjent bedrift`() {
        assertEquals(findBedrift(69420, "/renhold.xml"), "Ikke renholdsbedrift")
    }

    @Test
    fun `verifiser ulovlig bedrift`() {
        assertEquals(findBedrift(958942192, "/renhold.xml"), "Ikke godkjent")
    }

    @Test
    fun `verifiser godkjent uten ansatte bedrift`() {
        assertEquals(findBedrift(979952562, "/renhold.xml"), "Godkjent uten ansatte")
    }

    @Test
    fun `verifiser HMS-kort bedrift`() {
        assertEquals(findBedrift(996168484, "/renhold.xml"), "Under HMS-kortbestilling")
    }

    @Test
    fun `verifiser under behandling bedrift`() {
        assertEquals(findBedrift(993096202, "/renhold.xml"), "Under behandling")
    }

    @Test
    fun `math`() {
        assertEquals(2, 2)
    }
}
