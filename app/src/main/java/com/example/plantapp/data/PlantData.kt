package com.example.plantapp.data

import com.example.plantapp.model.Biljka
import com.example.plantapp.model.KlimatskiTip
import com.example.plantapp.model.MedicinskaKorist
import com.example.plantapp.model.ProfilOkusaBiljke
import com.example.plantapp.model.Zemljiste
object PlantData {
    private val biljke = listOf(
        Biljka(
            naziv = "Bosiljak (Ocimum basilicum)",
            porodica = "Lamiaceae (usnate)",
            medicinskoUpozorenje = "Može iritati kožu osjetljivu na sunce. Preporučuje se oprezna upotreba pri korištenju ulja bosiljka.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salata od paradajza", "Punjene tikvice"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA),
            imageResName = "biljka"

        ),
        Biljka(
            naziv = "Nana (Mentha spicata)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.MENTA,
            jela = listOf("Jogurt sa voćem", "Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.GLINENO, Zemljiste.CRNICA),
            imageResName = "biljka"

        ),
        Biljka(
            naziv = "Kamilica (Matricaria chamomilla)",
            porodica = "Asteraceae (glavočike)",
            medicinskoUpozorenje = "Može uzrokovati alergijske reakcije kod osjetljivih osoba.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Čaj od kamilice"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO),
            imageResName = "biljka"


        ),
        Biljka(
            naziv = "Ružmarin (Rosmarinus officinalis)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Treba ga koristiti umjereno i konsultovati se sa ljekarom pri dugotrajnoj upotrebi ili upotrebi u većim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Pečeno pile", "Grah","Gulaš"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.SLJUNOVITO, Zemljiste.KRECNJACKO),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Lavanda (Lavandula angustifolia)",
            porodica = "Lamiaceae (metvice)",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice, dojilje i djecu mlađu od 3 godine. Također, treba izbjegavati kontakt lavanda ulja sa očima.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.AROMATICNO,
            jela = listOf("Jogurt sa voćem"),
            klimatskiTipovi = listOf(KlimatskiTip.SREDOZEMNA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO),
            imageResName = "biljka"

        ),
        Biljka(
            naziv = "Kopriva (Urtica dioica)",
            porodica = "Urticaceae",
            medicinskoUpozorenje = "Može izazvati iritaciju kože zbog žara.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTIVBOLOVA, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Čaj od koprive", "Kopriva u salatama"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.ILOVACA),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Ribizla (Ribes rubrum)",
            porodica = "Grossulariaceae",
            medicinskoUpozorenje = "Nema posebnih upozorenja.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPRITISKA),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("Ribizla u džemu", "Ribizla u desertima"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.CRNICA),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Šipurak (Rosa canina)",
            porodica = "Rosaceae",
            medicinskoUpozorenje = "Nema posebnih upozorenja.",
            medicinskeKoristi = listOf(MedicinskaKorist.PODRSKAIMUNITETU, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.SLATKI,
            jela = listOf("Čaj od šipurka", "Šipurak u džemu"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUHA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.SLJUNOVITO),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Neven (Calendula officinalis)",
            porodica = "Asteraceae",
            medicinskoUpozorenje = "Može izazvati alergijsku reakciju kod osoba osjetljivih na biljke iz porodice Asteraceae.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.SMIRENJE),
            profilOkusa = ProfilOkusaBiljke.BEZUKUSNO,
            jela = listOf("Salate", "Neven u kozmetičkim proizvodima"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.PJESKOVITO),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Pelin (Artemisia absinthium)",
            porodica = "Asteraceae",
            medicinskoUpozorenje = "Može izazvati neurološke poremećaje i probavne smetnje ako se uzima u velikim količinama.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Korišten u proizvodnji pelinkovca"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.SUBTROPSKA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.KRECNJACKO),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Veremot (Verbena officinalis)",
            porodica = "Verbenaceae",
            medicinskoUpozorenje = "Može izazvati povraćanje ili druge gastrointestinalne smetnje ako se uzima u velikim dozama.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTIVBOLOVA),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Čaj od veremota"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.ILOVACA),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Kunica (Achillea millefolium)",
            porodica = "Asteraceae",
            medicinskoUpozorenje = "Nije preporučljivo za trudnice i osobe koje uzimaju lijekove za razrjeđivanje krvi.",
            medicinskeKoristi = listOf(MedicinskaKorist.PROTUUPALNO, MedicinskaKorist.REGULACIJAPROBAVE),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Čaj od kunice", "Začin za jela"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA, KlimatskiTip.PLANINSKA),
            zemljisniTipovi = listOf(Zemljiste.ILOVACA, Zemljiste.PJESKOVITO),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Matičnjak (Melissa officinalis)",
            porodica = "Lamiaceae",
            medicinskoUpozorenje = "Nema posebnih upozorenja.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PODRSKAIMUNITETU),
            profilOkusa = ProfilOkusaBiljke.CITRUSNI,
            jela = listOf("Čaj od matičnjaka", "Salate"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.CRNICA, Zemljiste.ILOVACA),
            imageResName = "biljka"
        ),
        Biljka(
            naziv = "Kantarion (Hypericum perforatum)",
            porodica = "Hypericaceae",
            medicinskoUpozorenje = "Može izazvati fotosenzitivnost, nije preporučljivo uzimati s drugim lijekovima bez konzultacije s liječnikom.",
            medicinskeKoristi = listOf(MedicinskaKorist.SMIRENJE, MedicinskaKorist.PROTUUPALNO),
            profilOkusa = ProfilOkusaBiljke.GORKO,
            jela = listOf("Čaj od kantariona", "Kantarionovo ulje"),
            klimatskiTipovi = listOf(KlimatskiTip.UMJERENA),
            zemljisniTipovi = listOf(Zemljiste.PJESKOVITO, Zemljiste.ILOVACA),
            imageResName = "biljka"
        )
    )

    fun getPlants(): List<Biljka> = biljke

}



