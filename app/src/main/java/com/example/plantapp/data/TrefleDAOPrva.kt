package com.example.plantapp.data

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
//import android.net.http.HttpException
import android.util.Log
import android.widget.Toast
import com.example.plantapp.R
import com.example.plantapp.RetrofitBuilder
import com.example.plantapp.model.Biljka
import com.example.plantapp.model.KlimatskiTip
import com.example.plantapp.model.ProfilOkusaBiljke
import com.example.plantapp.model.Zemljiste
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern
//ne dohvaca mi vrijednost humidity i atmospherisc ispravno pa nisam na ovaj nacin ostavio
class TrefleDAOPrva(private val context: Context) {

    private val trefleApiService = RetrofitBuilder.trefleApiService
    private val token = "QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4"
    private val defaultBitmap: Bitmap by lazy { BitmapFactory.decodeResource(context.resources, R.drawable.biljka) }

    //Slika ova funkcionalnost mi radi komplet
    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(IO) {
            try {
                val latinskiNaziv = extractLatinName(biljka.naziv)
                Log.d("TrefleDAO", "Latinski naziv: $latinskiNaziv")
                val response = trefleApiService.searchPlants(latinskiNaziv, 1, token)
                if (response.isSuccessful) {
                    response.body()?.data?.firstOrNull()?.let { plant ->
                        val imageUrl = plant.image_url
                        Log.d("TrefleDAO", "Image URL: $imageUrl")
                        if (!imageUrl.isNullOrEmpty()) {
                            val url = URL(imageUrl)
                            val bitmap = BitmapFactory.decodeStream(url.openStream())
                            return@withContext bitmap
                        }
                    }
                } else {
                    Log.e(
                        "API Error",
                        "Failed to fetch: ${response.errorBody()?.string() ?: response.code()}"
                    )
                }
            } catch (e: Exception) {
                e.printStackTrace()
                Log.e("Exception", e.toString())
            }
            return@withContext defaultBitmap
        }
    }

    private fun extractLatinName(naziv: String): String {
        val pattern = Pattern.compile("\\(([^)]+)\\)")
        val matcher = pattern.matcher(naziv)
        return if (matcher.find()) {
            matcher.group(1) ?: naziv
        } else {
            naziv
        }
    }



    suspend fun fixData(biljka: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv = extractLatinName(biljka.naziv)
                val urlString =
                    "https://trefle.io/api/v1/species?filter[scientific_name]=$latinskiNaziv&token=$token"
                val url = URL(urlString)

                (url.openConnection() as? HttpURLConnection)?.run {
                    requestMethod = "GET"
                    connect()

                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.use { inputStream ->
                            val result = inputStream.bufferedReader().use { it.readText() }
                            val jsonObject = JSONObject(result)
                            val dataArray = jsonObject.getJSONArray("data")

                            if (dataArray.length() > 0) {
                                val plant = dataArray.getJSONObject(0)
                                val familyName = plant.optString("family")
                                val mainSpecies = plant.optJSONObject("main_species")
                                val growth = mainSpecies?.optJSONObject("growth")

                                val light = growth?.optInt("light")
                                val atmosphericHumidity = growth?.optInt("atmospheric_humidity")

                                Log.d("fixData", "Light: $light, Atmospheric Humidity: $atmosphericHumidity")


                                val correctedFamily =
                                    familyName.takeIf { it.isNotEmpty() } ?: biljka.porodica

                                var correctedJela = biljka.jela
                                var correctedUpozorenje = biljka.medicinskoUpozorenje

                                val edible = plant.optBoolean("edible")
                                if (!edible) {
                                    correctedJela = emptyList()
                                    if (!correctedUpozorenje.contains("NIJE JESTIVO")) {
                                        correctedUpozorenje += " NIJE JESTIVO"
                                    }
                                }

                                val toxicity = plant.optJSONObject("main_species")
                                    ?.optJSONObject("specifications")?.optString("toxicity")
                                if (toxicity != "none" && toxicity != null) {
                                    if (!correctedUpozorenje.contains("TOKSIČNO")) {
                                        correctedUpozorenje += " TOKSIČNO"
                                    }
                                }

                                val soilTextures =
                                    plant.optJSONObject("main_species")?.optJSONObject("growth")
                                        ?.optJSONArray("soil_texture")
                                val validSoilTypes = soilTextures?.let { array ->
                                    List(array.length()) { index ->
                                        when (array.getInt(index)) {
                                            9 -> Zemljiste.SLJUNOVITO
                                            10 -> Zemljiste.KRECNJACKO
                                            1, 2 -> Zemljiste.GLINENO
                                            3, 4 -> Zemljiste.PJESKOVITO
                                            5, 6 -> Zemljiste.ILOVACA
                                            7, 8 -> Zemljiste.CRNICA
                                            else -> null
                                        }
                                    }.filterNotNull()
                                } ?: emptyList()

                                val correctedZemljisniTipovi =
                                    biljka.zemljisniTipovi?.filter { zemljiste ->
                                        validSoilTypes.contains(zemljiste)
                                    }?.toMutableList() ?: mutableListOf()
                                validSoilTypes.forEach {
                                    if (!correctedZemljisniTipovi.contains(it)) {
                                        correctedZemljisniTipovi.add(it)
                                    }
                                }


                                Log.d("fixData", "Light: $light, Atmospheric Humidity: $atmosphericHumidity")

                                val validClimateTypes = when {
                                    light in 6..9 && atmosphericHumidity in 1..5 -> listOf(
                                        KlimatskiTip.SREDOZEMNA
                                    )
                                    light in 8..10 && atmosphericHumidity in 7..10 -> listOf(
                                        KlimatskiTip.TROPSKA
                                    )
                                    light in 6..9 && atmosphericHumidity in 5..8 -> listOf(
                                        KlimatskiTip.SUBTROPSKA
                                    )
                                    light in 4..7 && atmosphericHumidity in 3..7 -> listOf(
                                        KlimatskiTip.UMJERENA
                                    )
                                    light in 7..9 && atmosphericHumidity in 1..2 -> listOf(
                                        KlimatskiTip.SUHA
                                    )
                                    light in 0..5 && atmosphericHumidity in 3..7 -> listOf(
                                        KlimatskiTip.PLANINSKA
                                    )

                                    else -> emptyList()
                                }

                                val correctedKlimatskiTipovi = biljka.klimatskiTipovi.filter { klimatskiTip -> validClimateTypes.contains(klimatskiTip) }.toMutableList() ?: mutableListOf()
                                validClimateTypes.forEach { if (!correctedKlimatskiTipovi.contains(it)) { correctedKlimatskiTipovi.add(it) } }

                                Log.d("fixData", "Ispravljena biljka: $biljka") // Dodajte ovo


                                return@withContext Biljka(
                                    naziv = biljka.naziv,
                                    porodica = correctedFamily,
                                    medicinskoUpozorenje = correctedUpozorenje,
                                    medicinskeKoristi = biljka.medicinskeKoristi,
                                    profilOkusa = biljka.profilOkusa,
                                    jela = correctedJela,
                                    klimatskiTipovi = correctedKlimatskiTipovi,
                                    zemljisniTipovi = correctedZemljisniTipovi,
                                    imageResName = biljka.imageResName
                                )
                            }
                        }
                    }
                }
                return@withContext biljka
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext biljka
            }
        }
    }


    suspend fun getPlantsWithFlowerColor(flowerColor: String, substr: String): List<Biljka> {
        withContext(Dispatchers.Main) {
            Toast.makeText(context, "Pretraga biljaka po boji cvijeta i podstringu...", Toast.LENGTH_SHORT).show()
        }
        return withContext(Dispatchers.IO) {
            try {
                val response = trefleApiService.getPlantsByFlowerColorAndSubstr(token, flowerColor, "")
                if (response.isSuccessful) {
                    Log.d("API Success", "Response: ${response.body()?.toString()}")
                    response.body()?.data?.filter { plant ->
                        plant.common_name!!.contains(substr, ignoreCase = true)
                    }?.map { plant ->
                        Biljka(
                            naziv = plant.common_name ?: "",
                            porodica = plant.scientific_name ?: "",
                            medicinskoUpozorenje = "",
                            medicinskeKoristi = emptyList(),
                            profilOkusa = ProfilOkusaBiljke.GORKO,
                            jela = emptyList(),
                            klimatskiTipovi = emptyList(),
                            zemljisniTipovi = null,
                            imageResName = ""
                        )
                    } ?: emptyList()
                } else {
                    Log.e("API Error", "Failed to fetch: ${response.errorBody()?.string() ?: response.code()}")
                    emptyList()
                }
            } catch (e: retrofit2.HttpException) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "HTTP greška: ${e.code()}", Toast.LENGTH_LONG).show()
                }
                Log.e("HttpException", e.toString())
                emptyList()
            } catch (e: Exception) {
                withContext(Dispatchers.Main) {
                    Toast.makeText(context, "Došlo je do greške: ${e.message}", Toast.LENGTH_LONG).show()
                }
                Log.e("Exception", e.toString())
                emptyList()
            }
        }
    }

}
private fun <E> List<E>.add(it: E) { TODO("Not yet implemented") }
