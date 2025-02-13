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


    override fun getItemViewType(position: Int): Int {
        return when (fokus) {
            "Medicinski" -> 0
            "Kuharski" -> 1
            "Botanički" -> 2
            else -> -1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BiljkaViewHolder {
        val layoutId = when (viewType) {
            0 -> R.layout.item_biljka_medicinski
            1 -> R.layout.item_biljka_kuharski
            2 -> R.layout.item_biljka_botanicki
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

        @SuppressLint("SetTextI18n", "DiscouragedApi")
        fun bind(biljka: Biljka, fokus: String) {
            nazivItem.text = biljka.naziv
            val imageResId = itemView.context.resources.getIdentifier(
                biljka.imageResName,
                "drawable",
                itemView.context.packageName
            )
            if (imageResId != 0) {
                slikaItem.setImageResource(imageResId)
            }

            when (fokus) {
                "Medicinski" -> {
                    korist1Item?.text = biljka.medicinskeKoristi.getOrNull(0)?.opis ?: ""
                    korist2Item?.text = biljka.medicinskeKoristi.getOrNull(1)?.opis ?: ""
                    korist3Item?.text = biljka.medicinskeKoristi.getOrNull(2)?.opis ?: ""
                    upozorenjeItem?.text = biljka.medicinskoUpozorenje
                }

                "Kuharski" -> {
                    profilOkusaItem?.text = biljka.profilOkusa.opis
                    jelo1Item?.text = biljka.jela.getOrNull(0) ?: ""
                    jelo2Item?.text = biljka.jela.getOrNull(1) ?: ""
                    jelo3Item?.text = biljka.jela.getOrNull(2) ?: ""
                }

                "Botanički" -> {
                    porodicaItem?.text = biljka.porodica
                    zemljisniTipItem?.text = biljka.zemljisniTipovi.getOrNull(0)?.naziv ?: ""
                    klimatskiTipItem?.text = biljka.klimatskiTipovi.getOrNull(0)?.opis ?: ""
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
        if (fokus != newFocus) {
            fokus = newFocus
            notifyDataSetChanged()  //osiguravanje ispravnog layout-a
        }

    }
}

