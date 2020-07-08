package no.nav.pam.sommernerds

import io.mockk.every
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Import
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.test.context.junit.jupiter.SpringExtension
import kotlin.test.assertNull


@Import(RenholdsregisterDownloaderTestConfig::class)
@ExtendWith(SpringExtension::class)
class getGodkjentStatusTest @Autowired constructor(private val mockRenholdsregisterDownloader: RenholdsregisterDownloader,
                                                            private val renholdsregisterRepository: RenholdsregisterRepository,
                                                            private val scheduler: Scheduler) {


    @Test
    fun `sjekk at en godkjent bedrift returnerer at den er godkjent`(){
        every { mockRenholdsregisterDownloader.download("1") } returns mapOf("983068824" to "Godkjent med ansatte")
        scheduler.scheduledDL("1")
        assertEquals(renholdsregisterRepository.getGodkjentStatus("983068824"), "Godkjent med ansatte")
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
class SchedulerTest @Autowired constructor(private val mockRenholdsregisterDownloader: RenholdsregisterDownloader,
private val renholdsregisterRepository: RenholdsregisterRepository, private val scheduler: Scheduler) {
    @Test
    fun `sjekk at orgnrToGodkjentStatusMap oppdateres ved nedlasting av ny eller oppdatert fil`() {
        every { mockRenholdsregisterDownloader.download("1") } returns mapOf("983068824" to "Godkjent med ansatte")
        every { mockRenholdsregisterDownloader.download("2") } returns mapOf("943001820" to "Godkjent med ansatte")

        scheduler.scheduledDL("1")
        assertEquals(renholdsregisterRepository.getGodkjentStatus("983068824"), "Godkjent med ansatte")

        scheduler.scheduledDL("2")
        assertEquals(renholdsregisterRepository.getGodkjentStatus("943001820"), "Godkjent med ansatte")
        assertNull(renholdsregisterRepository.getGodkjentStatus("983068824"))
    }

}
