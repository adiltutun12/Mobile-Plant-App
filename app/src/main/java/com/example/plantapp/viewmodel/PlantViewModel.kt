package com.example.plantapp.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.plantapp.model.Biljka

class PlantViewModel : ViewModel() {
    private val _plants = MutableLiveData<List<Biljka>>().apply { value = emptyList() }
    val plants: LiveData<List<Biljka>> = _plants

    fun loadPlants() {
        // Ovdje umjesto "emptyList()" trebate učitati stvarne podatke, npr. iz baze podataka ili API-a
        _plants.value = emptyList()
    }

    // Ovdje dodajte metode za filtriranje biljaka po modu (medicinski, kuharski, botanički)
}
