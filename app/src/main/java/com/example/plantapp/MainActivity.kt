package com.example.plantapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Button
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.model.Biljka
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView
    private var currentFocus: String = "Medicinski"
    private var filteredPlants: List<Biljka> = emptyList()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
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

        val initialPlants = PlantRepository.getAllPlants()
        filteredPlants = initialPlants
        if (initialPlants.isEmpty()) {
            Toast.makeText(this, "Nema dostupnih biljaka!", Toast.LENGTH_LONG).show()
        }
        else {
            biljkeAdapter = BiljkeAdapter(filteredPlants, currentFocus) { clickedBiljka ->
                filteredPlants = PlantRepository.getPlantsByFocus(currentFocus, clickedBiljka)
                biljkeAdapter.updateList(filteredPlants)
            }

            biljkeRecyclerView.layoutManager = LinearLayoutManager(this)
            biljkeRecyclerView.adapter = biljkeAdapter
        }

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentFocus = parent.getItemAtPosition(position) as String

                //Azuriranje fokusa- lista biljaka ostaje ista, ali će se prikazati s novim layoutom
                biljkeAdapter.updateFocus(currentFocus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
            }
        }

        resetButton.setOnClickListener {
            biljkeAdapter.updateList(PlantRepository.getAllPlants())

        }
    }
}

