package com.example.plantapp.data

import com.example.plantapp.R
import com.example.plantapp.model.Biljka
import com.example.plantapp.model.KlimatskiTip
import com.example.plantapp.model.ProfilOkusaBiljke
import com.example.plantapp.model.Zemljiste
import android.content.res.Resources
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.util.Log
import com.example.plantapp.MainActivity
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL
import java.util.regex.Pattern

class TrefleDAO(private val apiKey: String, private val baseUrl: String = "http://trefle.io/api/v1") {

    private val api_key:String = "QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4"
    private val BASE_URL:String = "http://trefle.io/api/v1"

    var defaultBitmap: Bitmap? = null

    suspend fun getImage(biljka: Biljka): Bitmap {
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv = extractLatin(biljka.naziv ?: "").toLowerCase()
                val urlString = "$BASE_URL/plants/search?token=$api_key&q=${latinskiNaziv}"
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
                                val plantObject = dataArray.getJSONObject(0)
                                val imageUrl = plantObject.getString("image_url")
                                val bitmapUrlConnection = URL(imageUrl).openConnection() as HttpURLConnection
                                bitmapUrlConnection.connect()
                                if (bitmapUrlConnection.responseCode == HttpURLConnection.HTTP_OK) {
                                    val bitmapInputStream: InputStream = bitmapUrlConnection.inputStream
                                    return@withContext BitmapFactory.decodeStream(bitmapInputStream)
                                }
                            }
                        }
                    }
                }
                return@withContext defaultBitmap ?: throw IllegalStateException("Default bitmap not set")
            } catch (e: Exception) {
                e.printStackTrace()
                return@withContext defaultBitmap ?: throw IllegalStateException("Default bitmap not set")
            }
        }
    }

    suspend fun fixData(biljka: Biljka): Biljka {
        return withContext(Dispatchers.IO) {
            try {
                val latinskiNaziv = biljka.naziv?.let { extractLatin(it) }
                val baseUrl = "http://trefle.io/api/v1/species/"
                val urlS = "$baseUrl$latinskiNaziv?token=$api_key"
                val url = URL(urlS)
                (url.openConnection() as? HttpURLConnection)?.run {
                    requestMethod = "GET"
                    connect()
                    if (responseCode == HttpURLConnection.HTTP_OK) {
                        inputStream.use { inputStream ->
                            val result = inputStream.bufferedReader().use { it.readText() }
                            val jsonObject = JSONObject(result)
                            val species = jsonObject.getJSONObject("data")
                            if (species.length() > 0) {
                                val familyName = species.optString("family")
                                if(familyName!=biljka.porodica){
                                    biljka.porodica=familyName
                                }

                                val edible = species.optBoolean("edible")
                                if (!edible) {
                                    biljka.jela = emptyList()
                                    if (!biljka.medicinskoUpozorenje!!.contains("NIJE JESTIVO")) {
                                        biljka.medicinskoUpozorenje += " NIJE JESTIVO"
                                    }
                                }

                                val toxicity = species.optJSONObject("specifications")?.optString("toxicity")
                                if (toxicity != "null" && toxicity != "none") {
                                    if (!biljka.medicinskoUpozorenje!!.contains("TOKSIČNO")) {
                                        biljka.medicinskoUpozorenje += " TOKSIČNO"
                                    }
                                }

                                // Provjera tipa zemljišta na osnovu soliTexture
                                val soilTextures = species.optInt("soil_textures")
                                val validSoilTypes = mutableListOf<Zemljiste>()
                                if (soilTextures==9) { validSoilTypes.add(Zemljiste.SLJUNOVITO) }
                                if (soilTextures==10) { validSoilTypes.add(Zemljiste.KRECNJACKO) }
                                if (soilTextures==1 || soilTextures == 2) { validSoilTypes.add(Zemljiste.GLINENO) }
                                if (soilTextures==3 || soilTextures == 4) { validSoilTypes.add(Zemljiste.PJESKOVITO) }
                                if (soilTextures==5 || soilTextures == 6) { validSoilTypes.add(Zemljiste.ILOVACA) }
                                if (soilTextures==7 || soilTextures == 8) { validSoilTypes.add(Zemljiste.CRNICA) }
                                biljka.zemljisniTipovi=validSoilTypes

                                // Provjera klimatskog tipa na osnovu light i humidity
                                val light = species.optJSONObject("growth")?.optInt("light")
                                val humidity = species.optJSONObject("growth")?.optInt("atmospheric_humidity")
                                val validClimateTypes = mutableListOf<KlimatskiTip>()
                                if (light in 6..9 && humidity in 1..5) { validClimateTypes.add(KlimatskiTip.SREDOZEMNA) }
                                if (light in 8..10 && humidity in 7..10) { validClimateTypes.add(KlimatskiTip.TROPSKA) }
                                if (light in 6..9 && humidity in 5..8) { validClimateTypes.add(KlimatskiTip.SUBTROPSKA) }
                                if (light in 4..7 && humidity in 3..7) { validClimateTypes.add(KlimatskiTip.UMJERENA) }
                                if (light in 7..9 && humidity in 1..2) { validClimateTypes.add(KlimatskiTip.SUHA) }
                                if (light in 0..5 && humidity in 3..7) { validClimateTypes.add(KlimatskiTip.PLANINSKA) }
                                biljka.klimatskiTipovi=validClimateTypes
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
        return withContext(Dispatchers.IO) {
            val listaBiljaka = mutableListOf<Biljka>()
            try {
                val searchUrl = "$BASE_URL/plants/search?token=$api_key&q=$substr".let { URL(it) }
                searchUrl.openConnection().also { connection ->
                    (connection as? HttpURLConnection)?.apply {
                        requestMethod = "GET"
                        connect()
                        if (responseCode == HttpURLConnection.HTTP_OK) {
                            inputStream.bufferedReader().use { reader ->
                                JSONObject(reader.readText()).getJSONArray("data").let { results ->
                                    for (i in 0 until results.length()) {
                                        results.getJSONObject(i).let { plant ->
                                            val speciesUrl = "$BASE_URL/species/${plant.getInt("id")}?token=$api_key".let { URL(it) }
                                            speciesUrl.processPlant(flowerColor, listaBiljaka, plant.getString("scientific_name"), plant.getString("family"))
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            } catch (e: Exception) {
                e.printStackTrace()
            }
            listaBiljaka
        }
    }
    private fun URL.processPlant(flowerColor: String, plants: MutableList<Biljka>, name: String, family: String) {
        openConnection().also { connection ->
            (connection as? HttpURLConnection)?.apply {
                requestMethod = "GET"
                connect()
                if (responseCode == HttpURLConnection.HTTP_OK) {
                    inputStream.bufferedReader().use { reader ->
                        JSONObject(reader.readText()).optJSONObject("data")?.optJSONObject("flower")?.optJSONArray("color")?.let { colors ->
                            if (colors.toString().contains(flowerColor, ignoreCase = true)) {
                                plants.add(Biljka(name, family, "", emptyList(), ProfilOkusaBiljke.GORKO, emptyList(), emptyList(), null, ""))
                            }
                        }
                    }
                }
            }
        }
    }

    private fun extractLatin(fullName: String): String {
        val regex = Regex("\\(([^)]+)\\)")
        val spoji = regex.find(fullName)
        val latinName = spoji?.groups?.get(1)?.value ?: fullName
        return latinName.toLowerCase().replace(" ", "-")
    }

}
