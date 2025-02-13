package com.example.plantapp.data

import com.example.plantapp.model.Biljka


/*      import com.example.plantapp.model.Biljka

object PlantRepository {

    private var plants: List<Biljka> = listOf() // Ovo će držati listu biljaka

    init {
        // Inicijalizirajte listu biljaka, ovo će se pokrenuti prilikom prvog pristupa objektu PlantRepository
        plants = loadPlants()
    }

    private fun loadPlants() : List<Biljka>{
        // Učitajte biljke ovdje, na primjer iz lokalne baze podataka ili predefinirane liste
        return listOf(
            // Vaše biljke
        )
    }

    fun getAllPlants() : List<Biljka>{
        return plants
    }

    // Dodajte dodatne metode za filtriranje i dohvat biljaka ako su potrebne
}




import com.example.plantapp.model.Biljka
import com.example.plantapp.data.PlantData.biljke // Pretpostavimo da imate listu biljaka definisanu u PlantData

object PlantRepository {

    // Ovdje pretpostavljamo da su biljke već učitane u PlantData
    private var plants: List<Biljka> = biljke

    fun getAllPlants(): List<Biljka> {
        return plants
    }

    fun getPlantsByFocus(focus: String): List<Biljka> {
        return when (focus) {
            "Medicinski" -> plants.filter { it.medicinskeKoristi.isNotEmpty() }
            "Kuharski" -> plants.filter { it.jela.isNotEmpty() }
            "Botanički" -> plants // Dodajte logiku za filtriranje po botaničkim karakteristikama
            else -> plants
        }
    }

    // Možete dodati dodatne metode ako je potrebno
}
*/



/*

object PlantRepository {

    private var plants: List<Biljka> = PlantData.getPlants()

    fun getAllPlants(): List<Biljka> {
        return plants
    }

    /*fun getPlantsByFocus(focus: String): List<Biljka> {
        return when (focus) {
            "Medicinski" -> plants.filter { it.medicinskeKoristi.isNotEmpty() }
            "Kuharski" -> plants.filter { it.jela.isNotEmpty() }
            "Botanički" -> plants // Ovdje implementirajte dodatnu logiku filtriranja ako je potrebno
            else -> plants
        }
    }*/

    fun getPlantsByFocus(focus: String, referenceBiljka: Biljka?): List<Biljka> {
        return when (focus) {
            "Medicinski" -> {
                if (referenceBiljka == null) return plants
                plants.filter { biljka ->
                    biljka.medicinskeKoristi.intersect(referenceBiljka.medicinskeKoristi).isNotEmpty()
                }
            }
            "Kuharski" -> {
                if (referenceBiljka == null) return plants
                plants.filter { biljka ->
                    biljka.jela.intersect(referenceBiljka.jela).isNotEmpty() ||
                            biljka.profilOkusa == referenceBiljka.profilOkusa
                }
            }
            "Botanički" -> {
                if (referenceBiljka == null) return plants
                plants.filter { biljka ->
                    biljka.porodica == referenceBiljka.porodica &&
                            biljka.klimatskiTipovi.intersect(referenceBiljka.klimatskiTipovi).isNotEmpty() &&
                            biljka.zemljisniTipovi.intersect(referenceBiljka.zemljisniTipovi).isNotEmpty()
                }
            }
            else -> plants
        }
    }

    fun getPlantsByFocus(focus: String): List<Biljka> {

    }

}
*/




object PlantRepository {

    private var plants: List<Biljka> = PlantData.getPlants()

    fun getAllPlants(): List<Biljka> {
        return plants
    }

    // Ova funkcija se koristi kada nemamo referentnu biljku
    fun getPlantsByFocus(focus: String): List<Biljka> {
        return when (focus) {
            "Medicinski" -> plants.filter { it.medicinskeKoristi.isNotEmpty() }
            "Kuharski" -> plants.filter { it.jela.isNotEmpty() }
            "Botanički" -> plants // Implementirajte dodatnu logiku filtriranja ako je potrebno
            else -> plants
        }
    }

    // Ova funkcija omogućava filtriranje biljaka na osnovu referentne biljke
    fun getPlantsByFocus(focus: String, referenceBiljka: Biljka?): List<Biljka> {
        return when (focus) {
            "Medicinski" -> {
                if (referenceBiljka == null) return plants
                plants.filter { it.medicinskeKoristi.intersect(referenceBiljka.medicinskeKoristi).isNotEmpty() }
            }
            "Kuharski" -> {
                if (referenceBiljka == null) return plants
                plants.filter { it.jela.intersect(referenceBiljka.jela).isNotEmpty() || it.profilOkusa == referenceBiljka.profilOkusa }
            }
            "Botanički" -> {
                if (referenceBiljka == null) return plants
                plants.filter { it.porodica == referenceBiljka.porodica && it.klimatskiTipovi.intersect(referenceBiljka.klimatskiTipovi).isNotEmpty() && it.zemljisniTipovi.intersect(referenceBiljka.zemljisniTipovi).isNotEmpty() }
            }
            else -> plants
        }
    }
}



