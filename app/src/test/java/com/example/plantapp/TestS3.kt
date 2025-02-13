package com.example.plantapp
import com.example.plantapp.data.TrefleDAO
import com.example.plantapp.model.Biljka
import com.example.plantapp.model.KlimatskiTip
import com.example.plantapp.model.MedicinskaKorist
import com.example.plantapp.model.ProfilOkusaBiljke
import com.example.plantapp.model.Zemljiste
import kotlinx.coroutines.runBlocking
import org.assertj.core.api.JUnitSoftAssertions
import org.junit.Assert.assertEquals

import org.junit.Assert.assertTrue
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class TestS3 {
    @get:Rule
    var softAssert = JUnitSoftAssertions()

    private val trefleDAO = TrefleDAO("test_api_key", "http://trefle.io/api/v1")
    @Test
    fun fixBosiljakTest() = runBlocking{
        var fixed = TrefleDAO("QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4").fixData(Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Netacno (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(
                MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.REGULACIJAPROBAVE
            ),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA),
            imageResName = "biljka"
        ))
        softAssert.assertThat(fixed.naziv).withFailMessage("T1.1 - naziv should contain \"Ocium basilicum\"").contains("Ocimum basilicum")
        softAssert.assertThat(fixed.porodica).withFailMessage("T1.2 - porodica should contain \"Lamiaceae\"").contains("Lamiaceae")
        softAssert.assertThat(fixed.medicinskoUpozorenje).withFailMessage("T1.3 - upozorenje should contain \"NIJE JESTIVO\"").contains("NIJE JESTIVO")
        softAssert.assertThat(fixed.klimatskiTipovi).withFailMessage("T1.4 - klimatskiTipovi should contain \"Umjerena\"").contains(KlimatskiTip.UMJERENA)
        softAssert.assertAll()

    }

    @Test
    fun fixEpipactisHelleborine()= runBlocking {
        var fixed = TrefleDAO("QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4").fixData(    Biljka(
            naziv = "Kruscika (Epipactis helleborine)",
            porodica = "Netacno (netacno)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(
                MedicinskaKorist.SMIRENJE,
                MedicinskaKorist.REGULACIJAPROBAVE
            ),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA),
            imageResName = "bosiljak"
        ))
        softAssert.assertThat(fixed.naziv).withFailMessage("T2.1 - naziv should contain \"Epipactis helleborine\"").contains("Epipactis helleborine")
        softAssert.assertThat(fixed.medicinskoUpozorenje).withFailMessage("T2.2 - upozorenje should contain \"NIJE JESTIVO\"").contains("NIJE JESTIVO")
        softAssert.assertThat(fixed.klimatskiTipovi).withFailMessage("T2.3 - klimatskiTipovi should contain \"Planinska\"").contains(KlimatskiTip.PLANINSKA)
        softAssert.assertAll()
    }

    @Test
    fun getFlowerRosaPurple()= runBlocking {
        var plants = TrefleDAO("QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4").getPlantsWithFlowerColor("purple","rosa")
        assertTrue("T3.1 - should contain \"Rosa pendulina\"",plants.find { biljka -> biljka.naziv.contains("Rosa pendulina",ignoreCase = true) }!=null)
    }

    @Test
    fun getFlowerRampionBlue()= runBlocking {
        var plants = TrefleDAO("QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4").getPlantsWithFlowerColor("blue","rampion")
        assertTrue("T4.1 - should contain \"Phyteuma spicatum\"",plants.find { biljka -> biljka.naziv.contains("Phyteuma spicatum",ignoreCase = true) }!=null)
    }
}



