/*package com.example.plantapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import com.example.plantapp.model.Biljka

class BiljkeAdapter(
    private var biljke: List<Biljka>,
    private var fokus: String
) : RecyclerView.Adapter<BiljkeAdapter.BiljkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val layoutId = when (fokus) {
            "Medicinski" -> R.layout.item_biljka_medicinski
            "Kuharski" -> R.layout.item_biljka_kuharski
            "Botanički" -> R.layout.item_biljka_botanicki
            else -> R.layout.item_biljka_medicinski // Default, ako je potrebno
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BiljkaViewHolder(view)
    }


    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.bind(biljka, fokus)
    }

    override fun getItemCount() = biljke.size

    class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        // Dodajte ostale view elemente koje koristite, na primer:
        // private val korist1Item: TextView = itemView.findViewById(R.id.korist1Item)
        // ...

        fun bind(biljka: Biljka, fokus: String) {
            nazivItem.text = biljka.naziv
            // Ažurirajte ostale view elemente na osnovu objekta biljka i trenutnog fokusa
            // Na primer:
            // korist1Item.text = biljka.medicinskeKoristi[0].opis
            // ...

            // Prilagodite vidljivost i sadržaj na osnovu fokusa
            when (fokus) {
                "Medicinski" -> {
                    // Podesite view-ove za medicinski fokus
                }
                "Kuharski" -> {
                    // Podesite view-ove za kuharski fokus
                }
                "Botanički" -> {
                    // Podesite view-ove za botanički fokus
                }
            }
        }
    }

    fun updateFocus(newFocus: String) {
        fokus = newFocus
        notifyDataSetChanged()
    }
}*/



/* ovo bi trebalo da je dobra verzija
package com.example.plantapp.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import com.example.plantapp.model.Biljka

class BiljkeAdapter(
    private var biljke: List<Biljka>,
    private var fokus: String
) : RecyclerView.Adapter<BiljkeAdapter.BiljkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val layoutId = when (fokus) {
            "Medicinski" -> R.layout.item_biljka_medicinski
            "Kuharski" -> R.layout.item_biljka_kuharski
            "Botanički" -> R.layout.item_biljka_botanicki
            else -> R.layout.item_biljka_medicinski
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.bind(biljka, fokus)
    }

    override fun getItemCount() = biljke.size

    class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        // Dodajte ostale view elemente
        private val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        private val korist1Item: TextView = itemView.findViewById(R.id.korist1Item)
        private val korist2Item: TextView = itemView.findViewById(R.id.korist2Item)
        private val korist3Item: TextView = itemView.findViewById(R.id.korist3Item)
        private val profilOkusaItem: TextView = itemView.findViewById(R.id.profilOkusaItem)
        private val jelo1Item: TextView = itemView.findViewById(R.id.jelo1Item)
        private val jelo2Item: TextView = itemView.findViewById(R.id.jelo2Item)
        private val jelo3Item: TextView = itemView.findViewById(R.id.jelo3Item)
        private val porodicaItem: TextView = itemView.findViewById(R.id.porodicaItem)
        private val zemljisniTipItem: TextView = itemView.findViewById(R.id.zemljisniTipItem)
        private val klimatskiTipItem: TextView = itemView.findViewById(R.id.klimatskiTipItem)

        fun bind(biljka: Biljka, fokus: String) {
            nazivItem.text = biljka.naziv
            // Pretpostavljamo da slikaItem već sadrži sliku, prilagodite po potrebi
            // slikaItem.setImageDrawable(...) ili slikaItem.setImageResource(...)
            val imageResId = itemView.context.resources.getIdentifier(
                biljka.imageResName, "drawable", itemView.context.packageName)
            if (imageResId != 0) {
                slikaItem.setImageResource(imageResId)
            } else {
                // Postavite neku default sliku ako resurs ne postoji
                slikaItem.setImageResource(R.drawable.ic_launcher_background)
            }


            when (fokus) {
                "Medicinski" -> {
                    korist1Item.text = biljka.medicinskeKoristi.getOrNull(0)?.opis
                    korist2Item.text = biljka.medicinskeKoristi.getOrNull(1)?.opis
                    korist3Item.text = biljka.medicinskeKoristi.getOrNull(2)?.opis
                    // Prikazati ili sakriti view elemente na osnovu fokusa
                }
                "Kuharski" -> {
                    profilOkusaItem.text = biljka.profilOkusa.opis
                    jelo1Item.text = biljka.jela.getOrNull(0)
                    jelo2Item.text = biljka.jela.getOrNull(1)
                    jelo3Item.text = biljka.jela.getOrNull(2)
                    // Prikazati ili sakriti view elemente na osnovu fokusa
                }
                "Botanički" -> {
                    porodicaItem.text = biljka.porodica
                    zemljisniTipItem.text = biljka.zemljisniTipovi.getOrNull(0)?.naziv
                    klimatskiTipItem.text = biljka.klimatskiTipovi.getOrNull(0)?.opis
                    // Prikazati ili sakriti view elemente na osnovu fokusa
                }
            }
        }
    }


    fun updateFocus(newFocus: String) {
        fokus = newFocus
        notifyDataSetChanged()
    }

    fun updateList(newList: List<Biljka>) {
        biljke = newList
        notifyDataSetChanged()
    }
}  */




/* oooovajj je radio
package com.example.plantapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import com.example.plantapp.model.Biljka

class BiljkeAdapter(
    private var biljke: List<Biljka>,
    private var fokus: String,
    private val onBiljkaClick: (Biljka) -> Unit
) : RecyclerView.Adapter<BiljkeAdapter.BiljkaViewHolder>() {

   /* override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val layoutId = when (fokus) {
            "Medicinski" -> R.layout.item_biljka_medicinski
            "Kuharski" -> R.layout.item_biljka_kuharski
            "Botanički" -> R.layout.item_biljka_botanicki
            else -> R.layout.item_biljka_medicinski
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BiljkaViewHolder(view)
    }*/
   override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
       val layoutId = when (fokus) {
           "Medicinski" -> R.layout.item_biljka_medicinski
           "Kuharski" -> R.layout.item_biljka_kuharski
           "Botanički" -> R.layout.item_biljka_botanicki
           else -> R.layout.item_biljka_medicinski // Ovo može biti default layout ako fokus nije poznat
       }
       val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
       return BiljkaViewHolder(view)
   }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.bind(biljka, fokus)
    }

    override fun getItemCount() = biljke.size

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        private val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBiljkaClick(biljke[position])
                }
            }
        }

        fun bind(biljka: Biljka, fokus: String) {
            nazivItem.text = biljka.naziv
            // Postavite sliku za biljku ako je dostupna
            val imageResId = itemView.context.resources.getIdentifier(biljka.imageResName, "drawable", itemView.context.packageName)
            if (imageResId != 0) {
                slikaItem.setImageResource(imageResId)
            }

            // Implementacija za prikaz detalja u zavisnosti od fokusa
            // ...
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Biljka>) {
        biljke = newList
        notifyDataSetChanged()
    }
@SuppressLint("NotifyDataSetChanged")
fun updateFocus(newFocus: String) {
    fokus = newFocus
    // Ovdje ažuriramo layout tako što obavještavamo RecyclerView da su se promijenili svi itemi
    notifyItemRangeChanged(0, biljke.size)
}

    /*@SuppressLint("NotifyDataSetChanged")
    fun updateFocus(newFocus: String) {
        fokus = newFocus
        notifyDataSetChanged()
    }*/
}
dovde granica za ovaj sto radi
*/
package com.example.plantapp.view

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.plantapp.R
import com.example.plantapp.model.Biljka

class BiljkeAdapter(
    private var biljke: List<Biljka>,
    private var fokus: String,
    private val onBiljkaClick: (Biljka) -> Unit
) : RecyclerView.Adapter<BiljkeAdapter.BiljkaViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val layoutId = when (fokus) {
            "Medicinski" -> R.layout.item_biljka_medicinski
            "Kuharski" -> R.layout.item_biljka_kuharski
            "Botanički" -> R.layout.item_biljka_botanicki
            else -> R.layout.item_biljka_medicinski
        }
        val view = LayoutInflater.from(parent.context).inflate(layoutId, parent, false)
        return BiljkaViewHolder(view)
    }

    override fun onBindViewHolder(holder: BiljkaViewHolder, position: Int) {
        val biljka = biljke[position]
        holder.bind(biljka, fokus)
    }

    override fun getItemCount() = biljke.size

    inner class BiljkaViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val nazivItem: TextView = itemView.findViewById(R.id.nazivItem)
        private val slikaItem: ImageView = itemView.findViewById(R.id.slikaItem)
        private val korist1Item: TextView? = itemView.findViewById(R.id.korist1Item)
        private val korist2Item: TextView? = itemView.findViewById(R.id.korist2Item)
        private val korist3Item: TextView? = itemView.findViewById(R.id.korist3Item)
        private val upozorenjeItem: TextView? = itemView.findViewById(R.id.upozorenjeItem)
        private val profilOkusaItem: TextView? = itemView.findViewById(R.id.profilOkusaItem)
        private val jelo1Item: TextView? = itemView.findViewById(R.id.jelo1Item)
        private val jelo2Item: TextView? = itemView.findViewById(R.id.jelo2Item)
        private val jelo3Item: TextView? = itemView.findViewById(R.id.jelo3Item)
        private val porodicaItem: TextView? = itemView.findViewById(R.id.porodicaItem)
        private val zemljisniTipItem: TextView? = itemView.findViewById(R.id.zemljisniTipItem)
        private val klimatskiTipItem: TextView? = itemView.findViewById(R.id.klimatskiTipItem)

        init {
            itemView.setOnClickListener {
                val position = adapterPosition
                if (position != RecyclerView.NO_POSITION) {
                    onBiljkaClick(biljke[position])
                }
            }
        }

        @SuppressLint("SetTextI18n")
        fun bind(biljka: Biljka, fokus: String) {
            nazivItem.text = biljka.naziv
            val imageResId = itemView.context.resources.getIdentifier(biljka.imageResName, "drawable", itemView.context.packageName)
            if (imageResId != 0) {
                slikaItem.setImageResource(imageResId)
            }

            when (fokus) {
                "Medicinski" -> {
                    korist1Item?.text = biljka.medicinskeKoristi.getOrNull(0)?.name ?: ""
                    korist2Item?.text = biljka.medicinskeKoristi.getOrNull(1)?.name ?: ""
                    korist3Item?.text = biljka.medicinskeKoristi.getOrNull(2)?.name ?: ""
                    upozorenjeItem?.text = biljka.medicinskoUpozorenje
                }
                "Kuharski" -> {
                    profilOkusaItem?.text = biljka.profilOkusa.name
                    jelo1Item?.text = biljka.jela.getOrNull(0) ?: ""
                    jelo2Item?.text = biljka.jela.getOrNull(1) ?: ""
                    jelo3Item?.text = biljka.jela.getOrNull(2) ?: ""
                }
                "Botanički" -> {
                    porodicaItem?.text = biljka.porodica
                    zemljisniTipItem?.text = biljka.zemljisniTipovi.getOrNull(0)?.name ?: ""
                    klimatskiTipItem?.text = biljka.klimatskiTipovi.getOrNull(0)?.name ?: ""
                }
            }
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateList(newList: List<Biljka>) {
        biljke = newList
        notifyDataSetChanged()
    }

    @SuppressLint("NotifyDataSetChanged")
    fun updateFocus(newFocus: String) {
        fokus = newFocus
        notifyItemRangeChanged(0, biljke.size)
    }
}

