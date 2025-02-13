package com.example.plantapp.data

import com.example.plantapp.model.Biljka

object PlantRepository {

    private var plants: List<Biljka> = PlantData.getPlants()

    // Od ovog dijela izmjena uradjena za spiralu 2

    private var plants1: MutableList<Biljka> = PlantData.getPlants().toMutableList()

    fun addPlant(newPlant: Biljka) {
        plants1.add(newPlant)  // Dodaje novu biljku u mutable listu
    }

    // Do ovog dijela izmjena uradjena za spiralu 2

    fun getAllPlants(): List<Biljka> {
        return plants1
    }

    // Ova funkcija se koristi kada nemamo referentnu biljku
    fun getPlantsByFocus(focus: String): List<Biljka> {
        return when (focus) {
            "Medicinski" -> plants.filter { it.medicinskeKoristi.isNotEmpty() }
            "Kuharski" -> plants.filter { it.jela.isNotEmpty() }
            "Botanički" -> plants
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
                plants.filter {
                    it.jela.intersect(referenceBiljka.jela).isNotEmpty() ||
                            it.profilOkusa == referenceBiljka.profilOkusa
                }
            }
            "Botanički" -> {
                if (referenceBiljka == null) return plants
                plants.filter {
                    it.porodica == referenceBiljka.porodica &&
                            it.klimatskiTipovi.intersect(referenceBiljka.klimatskiTipovi ?: emptyList()).isNotEmpty() &&
                            it.zemljisniTipovi?.intersect(referenceBiljka.zemljisniTipovi ?: emptyList())?.isNotEmpty() == true
                }
            }
            else -> plants
        }
    }
}
