package com.example.plantapp
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.data.TrefleDAO
import com.example.plantapp.model.Biljka
import com.example.plantapp.view.BiljkeAdapter
import com.example.plantapp.view.NovaBiljkaActivity
import kotlinx.coroutines.launch

@Suppress("DEPRECATION")
class MainActivity : AppCompatActivity() {
    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView
    private lateinit var pretragaET: EditText
    private lateinit var bojaSPIN: Spinner
    private lateinit var brzaPretraga: Button
    private var currentFocus: String = "Medicinski"
    private var filteredPlants: List<Biljka> = emptyList()
    private lateinit var trefleDAO: TrefleDAO
    private var initialPlants: List<Biljka> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val apiKey = "QLqAgsRfB04UZNMtWLL4lUwrNjrhc0LJPc01_TE28X4"
        val baseUrl = "https://trefle.io/api/v1"
        trefleDAO = TrefleDAO(apiKey, baseUrl)
        trefleDAO.defaultBitmap = BitmapFactory.decodeResource(resources, R.drawable.biljka) //za vracanje defaultne slike
        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        pretragaET = findViewById(R.id.pretragaET)
        bojaSPIN = findViewById(R.id.bojaSPIN)
        brzaPretraga = findViewById(R.id.brzaPretraga)
        val spinner: Spinner = findViewById(R.id.modSpinner)
        val resetButton: Button = findViewById(R.id.resetBtn)

        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        ArrayAdapter.createFromResource(
            this,
            R.array.flower_colors_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            bojaSPIN.adapter = adapter
        }

        initialPlants = PlantRepository.getAllPlants()
        filteredPlants = initialPlants
        if (initialPlants.isEmpty()) {
            Toast.makeText(this, "Nema dostupnih biljaka!", Toast.LENGTH_LONG).show()
        } else {
            biljkeAdapter = BiljkeAdapter(filteredPlants, currentFocus, { clickedBiljka ->
                filteredPlants = PlantRepository.getPlantsByFocus(currentFocus, clickedBiljka)
                biljkeAdapter.updateList(filteredPlants)
            }, trefleDAO, lifecycleScope)

            biljkeRecyclerView.layoutManager = LinearLayoutManager(this)
            biljkeRecyclerView.adapter = biljkeAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentFocus = parent.getItemAtPosition(position) as String
                Log.d("MainActivity", "Focus changed to: $currentFocus")

                biljkeAdapter.setClickEnabled(true)

                if (currentFocus == "Botanički") {
                    pretragaET.visibility = View.VISIBLE
                    bojaSPIN.visibility = View.VISIBLE
                    brzaPretraga.visibility = View.VISIBLE
                } else {
                    pretragaET.visibility = View.GONE
                    bojaSPIN.visibility = View.GONE
                    brzaPretraga.visibility = View.GONE
                }

                refreshPlantList()
                biljkeAdapter.updateFocus(currentFocus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {}
        }

        resetButton.setOnClickListener {
            biljkeAdapter.updateList(PlantRepository.getAllPlants())
        }

        brzaPretraga.setOnClickListener {
            val substr = pretragaET.text.toString().trim()
            val selectedColor = bojaSPIN.selectedItem.toString()
            if (substr.isNotEmpty() && selectedColor.isNotEmpty()) {
                lifecycleScope.launch {
                    val searchResults = trefleDAO.getPlantsWithFlowerColor(selectedColor, substr)
                    biljkeAdapter.setClickEnabled(false)
                    biljkeAdapter.updateList(searchResults)
                }
            } else {
                Toast.makeText(this, "Unesite podstring i odaberite boju cvijeta.", Toast.LENGTH_SHORT).show()
            }
        }

        val novaBiljkaBtn: Button = findViewById(R.id.novaBiljkaBtn)
        novaBiljkaBtn.setOnClickListener {
            val intent = Intent(this, NovaBiljkaActivity::class.java)
            startActivityForResult(intent, REQUEST_CODE_ADD_PLANT)
        }
    }

    override fun onResume() {
        super.onResume()
        refreshPlantList()
    }

    private fun refreshPlantList() {
        val updatedPlants = PlantRepository.getAllPlants()
        filteredPlants = when (currentFocus) {
            "Medicinski" -> updatedPlants.filter { it.medicinskeKoristi.isNotEmpty() }
            "Kuharski" -> updatedPlants  //.filter { it.jela.isNotEmpty() }
            "Botanički" -> updatedPlants
            else -> updatedPlants
        }
        biljkeAdapter.updateList(filteredPlants)
    }
    @Deprecated("Deprecated in Java")
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_ADD_PLANT && resultCode == RESULT_OK) {
            refreshPlantList()
        }
    }
    companion object {
        private const val REQUEST_CODE_ADD_PLANT = 1
    }
}
