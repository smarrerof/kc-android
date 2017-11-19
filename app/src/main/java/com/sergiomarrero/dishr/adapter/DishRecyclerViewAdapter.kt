package com.sergiomarrero.dishr.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout

import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Dish


class DishRecyclerViewAdapter(val dishes: List<Dish>?): RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    // ViewHolder
    inner class DishViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val image = itemView.findViewById<ImageView>(R.id.dish_image)
        val name = itemView.findViewById<TextView>(R.id.dish_name)
        val price = itemView.findViewById<TextView>(R.id.dish_price)
        val allergens = itemView.findViewById<LinearLayout>(R.id.dish_allergens)
        val description = itemView.findViewById<TextView>(R.id.dish_description)

        fun bindDish(dish: Dish) {
            image.setImageResource(when (dish.image) {
                "dish_01" -> R.drawable.dish_01
                "dish_02" -> R.drawable.dish_02
                "dish_03" -> R.drawable.dish_03
                "dish_04" -> R.drawable.dish_04
                else -> R.drawable.dish_unknown
            })
            name.text = dish.name
            price.text = "${dish.price}€"
            /*dish.allergens?.forEach { item ->
                allergens.append(item + " ")
            }*/
            dish.allergens?.forEach { item ->
                val imageView = ImageView(itemView.context)
                imageView.setImageResource(when (item) {
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
                imageView.maxWidth = 72
                imageView.maxHeight = 72
                imageView.adjustViewBounds = true
                imageView.setPadding(0,0,8,4)
                allergens.addView(imageView)
            }
            description.text = dish.description
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup?, viewType: Int): DishViewHolder {
        val view = LayoutInflater.from(parent?.context).inflate(R.layout.card_dish, parent, false)
        view.setOnClickListener(onClickListener)
        return DishViewHolder(view)
    }

    override fun onBindViewHolder(holder: DishViewHolder?, position: Int) {
        if (dishes != null) {
            holder?.bindDish(dishes[position])
        }
    }

    override fun getItemCount() = dishes?.size ?: 0
}