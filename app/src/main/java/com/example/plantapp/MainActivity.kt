// MainActivity.kt
/*package com.example.plantapp
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
//import com.example.plantapp.adapter.BiljkeAdapter
import com.example.plantapp.model.Biljka
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView
    private val biljkeList = listOf<Biljka>() // Ovdje bi se učitali pravi podaci, možda iz nekog repository-ja

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.modSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        // Postavite defaultni fokus, možda iz SharedPreferences ili neke druge inicijalne postavke
        val initialFocus = "Medicinski"
        biljkeAdapter = BiljkeAdapter(biljkeList, initialFocus)
        biljkeRecyclerView.adapter = biljkeAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                // Ovdje ne treba da se pravi novi adapter svaki put, samo ažuriramo postojeći
                biljkeAdapter.updateFocus(selectedFocus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Kod ako ništa nije odabrano
            }
        }
    }
}*/


/*
package com.example.plantapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val spinner: Spinner = findViewById(R.id.modSpinner)
        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        val initialFocus = "Medicinski"
        biljkeAdapter = BiljkeAdapter(PlantRepository.getAllPlants(), initialFocus)
        biljkeRecyclerView.adapter = biljkeAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(selectedFocus)
                biljkeAdapter.updateList(filteredPlants)
                biljkeAdapter.updateFocus(selectedFocus)
            }


            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ako ništa nije odabrano, ne radite ništa ili postavite defaultni fokus
            }
        }
    }
}*/

/*
package com.example.plantapp

import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        val spinner: Spinner = findViewById(R.id.modSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val initialFocus = "Medicinski"
        biljkeAdapter = BiljkeAdapter(PlantRepository.getAllPlants(), initialFocus) { biljka ->
            // Ovdje implementirajte šta se desi kada se klikne na biljku
            // Na primjer, prikažite Toast sa nazivom biljke
            Toast.makeText(this, "Kliknuli ste na biljku: ${biljka.naziv}", Toast.LENGTH_LONG).show()
            // Možete ovdje implementirati otvaranje nove aktivnosti ili fragmenta za prikaz detalja biljke
        }

        biljkeRecyclerView.adapter = biljkeAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(selectedFocus)
                biljkeAdapter.updateList(filteredPlants)
                biljkeAdapter.updateFocus(selectedFocus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ako ništa nije odabrano, možete postaviti defaultni fokus ili ne raditi ništa
            }
        }
    }
}
*/



/*  OVO JE BIO ISPRAVAn
package com.example.plantapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        val spinner: Spinner = findViewById(R.id.modSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val initialPlants = PlantRepository.getAllPlants()
        Log.d("MainActivity", "Ukupno biljaka: ${initialPlants.size}")
        if (initialPlants.isEmpty()) {
            Toast.makeText(this, "Nema dostupnih biljaka!", Toast.LENGTH_LONG).show()
        }

        val initialFocus = "Medicinski"
        biljkeAdapter = BiljkeAdapter(initialPlants, initialFocus) { biljka ->
            Toast.makeText(this, "Kliknuli ste na biljku: ${biljka.naziv}", Toast.LENGTH_LONG).show()
        }

        biljkeRecyclerView.layoutManager = LinearLayoutManager(this)
        biljkeRecyclerView.adapter = biljkeAdapter

       /* spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(selectedFocus)
                Log.d("MainActivity", "Filtrirano biljaka za $selectedFocus: ${filteredPlants.size}")
                biljkeAdapter.updateList(filteredPlants)
                biljkeAdapter.updateFocus(selectedFocus)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ako ništa nije odabrano, možete postaviti defaultni fokus ili ne raditi ništa
            }
        }
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(selectedFocus)
                biljkeAdapter.updateFocus(selectedFocus)
                biljkeAdapter.updateList(filteredPlants)
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ako ništa nije odabrano, ne radite ništa ili postavite defaultni fokus
            }
        }*/
        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                val selectedFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(selectedFocus)

                // Kreirajte novi adapter sa ažuriranim fokusom i listom
                biljkeAdapter = BiljkeAdapter(filteredPlants, selectedFocus) { biljka ->
                    Toast.makeText(this@MainActivity, "Kliknuli ste na biljku: ${biljka.naziv}", Toast.LENGTH_LONG).show()
                }
                biljkeRecyclerView.adapter = biljkeAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Implementacija kada ništa nije odabrano
            }
        }


    }
}*/

package com.example.plantapp

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Spinner
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.view.BiljkeAdapter

class MainActivity : AppCompatActivity() {

    private lateinit var biljkeAdapter: BiljkeAdapter
    private lateinit var biljkeRecyclerView: RecyclerView
    private var currentFocus: String = "Medicinski"

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        biljkeRecyclerView = findViewById(R.id.biljkeRV)
        val spinner: Spinner = findViewById(R.id.modSpinner)

        ArrayAdapter.createFromResource(
            this,
            R.array.modovi_array,
            android.R.layout.simple_spinner_item
        ).also { adapter ->
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
            spinner.adapter = adapter
        }

        val initialPlants = PlantRepository.getAllPlants()
        Log.d("MainActivity", "Ukupno biljaka: ${initialPlants.size}")
        if (initialPlants.isEmpty()) {
            Toast.makeText(this, "Nema dostupnih biljaka!", Toast.LENGTH_LONG).show()
        }

        // Postavljanje početnog fokusa
        currentFocus = "Medicinski"

        // Kreiranje adaptera sa početnim listama
        biljkeAdapter = BiljkeAdapter(initialPlants, currentFocus) { clickedBiljka ->
            // Filtriranje i ažuriranje liste nakon klika na biljku
            val filteredPlants = PlantRepository.getPlantsByFocus(currentFocus, clickedBiljka)
            biljkeAdapter.updateList(filteredPlants)
        }

        biljkeRecyclerView.layoutManager = LinearLayoutManager(this)
        biljkeRecyclerView.adapter = biljkeAdapter

        spinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View, position: Int, id: Long) {
                currentFocus = parent.getItemAtPosition(position) as String
                val filteredPlants = PlantRepository.getPlantsByFocus(currentFocus)

                // Ponovno kreiranje adaptera sa filtriranim listama
                biljkeAdapter = BiljkeAdapter(filteredPlants, currentFocus) { biljka ->
                    // Filtriranje i ažuriranje liste nakon klika na biljku
                    val focusedPlants = PlantRepository.getPlantsByFocus(currentFocus, biljka)
                    biljkeAdapter.updateList(focusedPlants)
                    Toast.makeText(this@MainActivity, "Kliknuli ste na biljku: ${biljka.naziv}", Toast.LENGTH_LONG).show()
                }
                biljkeRecyclerView.adapter = biljkeAdapter
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                // Ako ništa nije odabrano, ne radite ništa ili postavite defaultni fokus
            }
        }
    }
}
