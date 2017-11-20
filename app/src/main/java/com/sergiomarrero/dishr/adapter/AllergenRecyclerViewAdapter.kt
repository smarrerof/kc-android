package com.sergiomarrero.dishr.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.common.Allergens
import com.sergiomarrero.dishr.model.Dish


class AllergenRecyclerViewAdapter(val dish: Dish): RecyclerView.Adapter<AllergenRecyclerViewAdapter.AllergenViewHolder>(){

    // ViewHolder
    inner class AllergenViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {
        val image = itemView.findViewById<ImageView>(R.id.allergen_image)

        fun bindAllergen(allergen: String) {
            if (dish.allergens.contains(allergen)) {
                image.setImageResource(when (allergen) {
                    "soja" -> R.drawable.allergen_01_soja_on
                    "pescado" -> R.drawable.allergen_02_pescado_on
                    "mostaza" -> R.drawable.allergen_03_mostaza_on
                    "moluscos" -> R.drawable.allergen_04_moluscos_on
                    "lacteos" -> R.drawable.allergen_05_lacteos_on
                    "huevos" -> R.drawable.allergen_06_huevos_on
                    "sesamo" -> R.drawable.allergen_07_sesamo_on
                    "gluten" -> R.drawable.allergen_08_gluten_on
                    "frutos" -> R.drawable.allergen_09_frutos_de_cascara_on
                    "sulfitos" -> R.drawable.allergen_10_sulfitos_on
                    "crustaceos" -> R.drawable.allergen_11_crustaceos_on
                    "cacahuetes" -> R.drawable.allergen_12_cacahuetes_on
                    "apio" -> R.drawable.allergen_13_apio_on
                    "altramuces" -> R.drawable.allergen_14_altramuces_on
                    else -> R.drawable.allergen_01_soja_on
                })
            } else {
                image.setImageResource(when (allergen) {
                    "soja" -> R.drawable.allergen_01_soja_off
                    "pescado" -> R.drawable.allergen_02_pescado_off
                    "mostaza" -> R.drawable.allergen_03_mostaza_off
                    "moluscos" -> R.drawable.allergen_04_moluscos_off
                    "lacteos" -> R.drawable.allergen_05_lacteos_off
                    "huevos" -> R.drawable.allergen_06_huevos_off
                    "sesamo" -> R.drawable.allergen_07_sesamo_off
                    "gluten" -> R.drawable.allergen_08_gluten_off
                    "frutos" -> R.drawable.allergen_09_frutos_de_cascara_off
                    "sulfitos" -> R.drawable.allergen_10_sulfitos_off
                    "crustaceos" -> R.drawable.allergen_11_crustaceos_off
                    "cacahuetes" -> R.drawable.allergen_12_cacahuetes_off
                    "apio" -> R.drawable.allergen_13_apio_off
                    "altramuces" -> R.drawable.allergen_14_altramuces_off
                    else -> R.drawable.allergen_01_soja_off
                })
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): AllergenRecyclerViewAdapter.AllergenViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.recycler_view_item_allergen, parent, false)
        return AllergenViewHolder(view)
    }

    override fun onBindViewHolder(holder: AllergenRecyclerViewAdapter.AllergenViewHolder?, position: Int) {
        val allergen = Allergens.values()[position].name
        holder?.bindAllergen(allergen)
    }

    override fun getItemCount() = enumValues<Allergens>().size
}