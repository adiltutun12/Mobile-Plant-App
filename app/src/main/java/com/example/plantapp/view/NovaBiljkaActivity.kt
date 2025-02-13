
package com.example.plantapp.view

import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.os.Bundle
import android.provider.MediaStore
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isEmpty
import com.example.plantapp.R
import com.example.plantapp.data.PlantRepository
import com.example.plantapp.model.Biljka
import com.example.plantapp.model.KlimatskiTip
import com.example.plantapp.model.MedicinskaKorist
import com.example.plantapp.model.ProfilOkusaBiljke
import com.example.plantapp.model.Zemljiste

class NovaBiljkaActivity : AppCompatActivity() {

    private lateinit var nazivET: EditText
    private lateinit var porodicaET: EditText
    private lateinit var upozorenjeET: EditText
    private lateinit var dodajBiljkuBtn: Button
    private lateinit var jeloET: EditText
    private lateinit var jelaListView: ListView
    private lateinit var medicinskaKoristLV: ListView
    private lateinit var klimatskiTipLV: ListView
    private lateinit var zemljisniTipLV: ListView
    private lateinit var profilOkusaLV: ListView
    private lateinit var jelaAdapter: ArrayAdapter<String>
    private lateinit var dodajJeloBtn: Button
    private lateinit var uslikajBiljkuBtn: Button
    private lateinit var slikaImageView: ImageView
    private var jelaList: MutableList<String> = mutableListOf()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_nova_biljka)

        initializeViews()
        setupButtonListeners()
    }

    private fun initializeViews() {
        nazivET = findViewById(R.id.nazivET)
        porodicaET = findViewById(R.id.porodicaET)
        upozorenjeET = findViewById(R.id.medicinskoUpozorenjeET)
        jeloET = findViewById(R.id.jeloET)
        dodajBiljkuBtn = findViewById(R.id.dodajBiljkuBtn)
        dodajJeloBtn = findViewById(R.id.dodajJeloBtn)
        jelaListView = findViewById(R.id.jelaLV)
        medicinskaKoristLV = findViewById(R.id.medicinskaKoristLV)
        klimatskiTipLV = findViewById(R.id.klimatskiTipLV)
        zemljisniTipLV = findViewById(R.id.zemljisniTipLV)
        profilOkusaLV = findViewById(R.id.profilOkusaLV)
        uslikajBiljkuBtn = findViewById(R.id.uslikajBiljkuBtn)
        slikaImageView = findViewById(R.id.slikaIV)

        jelaListView = findViewById(R.id.jelaLV)
        jelaAdapter = ArrayAdapter(this, android.R.layout.simple_list_item_1, jelaList)
        jelaListView.adapter = jelaAdapter

        //Listener za ListView koji omogućava izmjenu i brisanje
        jelaListView.setOnItemClickListener { _, _, position, _ ->
            jeloET.setText(jelaList[position])
            dodajJeloBtn.text = getString(R.string.izmijeni_jelo)
            jelaListView.clearChoices()
            jelaListView.setItemChecked(position, false)
        }

        setListViewAdapters()
    }

    private fun setListViewAdapters() {
        medicinskaKoristLV.adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_multiple_choice,
            MedicinskaKorist.values().map { it.opis })
        medicinskaKoristLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        klimatskiTipLV.adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_multiple_choice,
            KlimatskiTip.values().map { it.opis })
        klimatskiTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        zemljisniTipLV.adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_multiple_choice,
            Zemljiste.values().map { it.naziv })
        zemljisniTipLV.choiceMode = ListView.CHOICE_MODE_MULTIPLE

        profilOkusaLV.adapter = ArrayAdapter(
            this, android.R.layout.simple_list_item_single_choice,
            ProfilOkusaBiljke.values().map { it.opis })
        profilOkusaLV.choiceMode = ListView.CHOICE_MODE_SINGLE
    }

    private fun setupButtonListeners() {
        dodajJeloBtn.setOnClickListener {
            handleJelo()
        }
        dodajBiljkuBtn.setOnClickListener {
            if (validateFields()) {
                addNewPlant()
            }
        }
        uslikajBiljkuBtn.setOnClickListener {
            openCamera()
        }
    }

    private fun openCamera() {
        if (ContextCompat.checkSelfPermission(this, android.Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.CAMERA), REQUEST_CAMERA_PERMISSION)
        } else {
            startCamera()
        }
    }
    private fun startCamera() {
        val takePictureIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        if (takePictureIntent.resolveActivity(packageManager) != null) {
            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE)
        } else {
            Toast.makeText(this, "Kamera nije dostupna", Toast.LENGTH_SHORT).show()
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CAMERA_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                startCamera()
            } else {
                Toast.makeText(this, "Dozvola za kameru odbijena", Toast.LENGTH_SHORT).show()
            }
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            val imageBitmap = data?.extras?.get("data") as Bitmap
            slikaImageView.setImageBitmap(imageBitmap)
        }
    }

    companion object {
        private const val REQUEST_IMAGE_CAPTURE = 1
        private const val REQUEST_CAMERA_PERMISSION = 101
    }
    private fun validateFields(): Boolean {
        var isValid = true

        //Validacija za EditText polja
        if (nazivET.text.isBlank() || nazivET.text.length !in 2..20) {
            nazivET.error = "Dužina teksta mora biti između 2 i 20 znakova"
            isValid = false
        }
        if (porodicaET.text.isBlank() || porodicaET.text.length !in 2..20) {
            porodicaET.error = "Dužina teksta mora biti između 2 i 20 znakova"
            isValid = false
        }
        if (upozorenjeET.text.isBlank()) {
            upozorenjeET.error = "Unesite medicinsko upozorenje"
            isValid = false
        }

        // Provjera da li postoji bar jedno jelo
        if (jelaList.isEmpty()) {
            Toast.makeText(this, "Dodajte bar jedno jelo", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        //Provjera za ListView sa višestrukim izborom
        if (!isAnyItemChecked(medicinskaKoristLV) || !isAnyItemChecked(klimatskiTipLV) || !isAnyItemChecked(zemljisniTipLV)) {
            Toast.makeText(this, "Odaberite barem jednu opciju u svakoj listi", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        //Provjera za ListView sa jednostrukim izborom
        if (profilOkusaLV.checkedItemPosition == AdapterView.INVALID_POSITION) {
            Toast.makeText(this, "Odaberite profil okusa", Toast.LENGTH_SHORT).show()
            isValid = false
        }

        return isValid
    }

    //Funkcija koja provjerava da li je bilo sta oznaceno
    private fun isAnyItemChecked(listView: ListView): Boolean {
        val checkedItems = listView.checkedItemPositions
        for (i in 0 until listView.count) {
            if (checkedItems.get(i)) {
                return true
            }
        }
        return false
    }

    private fun getSelectedProfilOkusa(): ProfilOkusaBiljke? {
        val selectedPosition = profilOkusaLV.checkedItemPosition
        return if (selectedPosition != AdapterView.INVALID_POSITION) {
            ProfilOkusaBiljke.values()[selectedPosition]
        } else {
            null //Kada nema odabira
        }
    }


    private fun addNewPlant() {
        if (validateFields()) {
            val profilOkusa = getSelectedProfilOkusa()
            if (profilOkusa == null) {
                Toast.makeText(this, "Morate odabrati profil okusa", Toast.LENGTH_SHORT).show()
                return
            }


            val defaultImageResName = "biljka"

            val newPlant = Biljka(
                naziv = nazivET.text.toString(),
                porodica = porodicaET.text.toString(),
                medicinskoUpozorenje = upozorenjeET.text.toString(),
                jela = jelaList.toList(),
                medicinskeKoristi = getSelectedItems(medicinskaKoristLV, MedicinskaKorist.values()),
                klimatskiTipovi = getSelectedItems(klimatskiTipLV, KlimatskiTip.values()),
                zemljisniTipovi = getSelectedItems(zemljisniTipLV, Zemljiste.values()),
                profilOkusa = profilOkusa,
                imageResName = defaultImageResName
            )
            PlantRepository.addPlant(newPlant)
            finish() // Zatvara aktivnost i vraća na prethodni ekran
        }
    }


    private fun <T> getSelectedItems(listView: ListView, items: Array<T>): List<T> =
        listView.checkedItemPositions.let { positions ->
            items.filterIndexed { index, _ -> positions.get(index) }
        }

    private fun handleJelo() {
        // Listener za ListView koji omogućava izmjenu i brisanje
        jelaListView.setOnItemClickListener { _, _, position, _ ->
            jeloET.setText(jelaList[position])
            dodajJeloBtn.text = getString(R.string.izmijeni_jelo)

            //Postavljanje dugmeta za update jela
            setupUpdateJeloButton(position)
        }

        setupAddJeloButton()
    }

    private fun setupAddJeloButton() {
        dodajJeloBtn.setOnClickListener {
            val jelo = jeloET.text.toString().trim()
            if (jelo.isNotEmpty() && jelaList.indexOfFirst { it.equals(jelo, ignoreCase = true) } == -1) {
                jelaList.add(jelo)  // Dodavanje novog jela
                jelaAdapter.notifyDataSetChanged()  // Osvežavanje ListView-a
                jeloET.text.clear()  // Čišćenje EditText-a nakon dodavanja
            } else if (jelo.isNotEmpty()) {
                Toast.makeText(this, "Jelo već postoji.", Toast.LENGTH_SHORT).show()
            }
            // Resetovanje teksta na dugmetu
            dodajJeloBtn.text = getString(R.string.dodaj_jelo)
        }
    }

   private fun setupUpdateJeloButton(position: Int) {
       dodajJeloBtn.setOnClickListener {
           val updatedJelo = jeloET.text.toString().trim()

           if (updatedJelo.isEmpty()) {
               //Ako je jelo prazno obrisi ga
               jelaList.removeAt(position)
               jelaAdapter.notifyDataSetChanged()
               jeloET.text.clear()
               dodajJeloBtn.text = getString(R.string.dodaj_jelo)
               setupAddJeloButton()
               Toast.makeText(this, "Jelo je obrisano.", Toast.LENGTH_SHORT).show()
           } else {
               val existingIndex = jelaList.indexOfFirst { it.equals(updatedJelo, ignoreCase = true) }
               if (existingIndex == -1 || existingIndex == position) {
                   //Azuriranje postojeceg jela ako nije duplikat ili isto jelo
                   jelaList[position] = updatedJelo
                   jelaAdapter.notifyDataSetChanged()
                   jeloET.text.clear()
                   dodajJeloBtn.text = getString(R.string.dodaj_jelo)
                   setupAddJeloButton()
               } else {
                   Toast.makeText(this, "Jelo sa tim imenom već postoji.", Toast.LENGTH_SHORT).show()
               }
           }
       }
   }


}

