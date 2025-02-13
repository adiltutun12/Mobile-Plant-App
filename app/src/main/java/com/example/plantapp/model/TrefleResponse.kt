package com.example.plantapp.model
data class TrefleResponse(
    val data: List<Plant>
)
data class Plant(
    val id: Int,
    val common_name: String?,
    val slug: String?,
    val scientific_name: String?,
    val year: Int?,
    val bibliography: String?,
    val author: String?,
    val status: String?,
    val rank: String?,
    val family_common_name: String?,
    val family: String?,
    val genus_id: Int?,
    val genus: String?,
    val image_url: String?,
    val duration: List<String>?,
    val edible_part: List<String>?,
    val edible: Boolean?,
    val vegetable: Boolean?,
    val observations: String?,
    val common_names: Map<String, String>?,
    val distribution: Map<String, String>?,
    val synonyms: List<String>?,
    val sources: List<Source>?,
    val main_species: MainSpecies?,
    val specifications: Specifications?,
    val growth: Growth? //Ugniježdeni model za podatke o rastu
)
data class MainSpecies(
    val specifications: Specifications?,
    val growth: Growth? // Dodano
)
data class Specifications(
    val toxicity: String?
)
data class Growth(
    val light: Int?,
    val atmospheric_humidity: Int?,
    val soil_texture: List<Int>?
)
data class Synonym(
    val id: Int,
    val name: String,
    val author: String
)
data class Source(
    val id: String,
    val name: String,
    val citation: String,
    val url: String,
    val last_update: String
)
