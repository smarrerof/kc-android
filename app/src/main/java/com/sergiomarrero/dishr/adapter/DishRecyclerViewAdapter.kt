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

        val name = itemView.findViewById<TextView>(R.id.dish_name)
        val image = itemView.findViewById<ImageView>(R.id.dish_image)
        val price = itemView.findViewById<TextView>(R.id.dish_price)
        //val allergens = itemView.findViewById<TextView>(R.id.dish_allergens)
        val allergens = itemView.findViewById<LinearLayout>(R.id.dish_allergens)

        fun bindDish(dish: Dish) {
            name.text = dish.name
            image.setImageResource(when (dish.image) {
                "dish_01" -> R.drawable.dish_01
                "dish_02" -> R.drawable.dish_02
                "dish_03" -> R.drawable.dish_03
                "dish_04" -> R.drawable.dish_04
                else -> R.drawable.dish_unknown
            })
            price.text = "Precio: ${dish.price}€"
            /*dish.allergens?.forEach { item ->
                allergens.append(item + " ")
            }*/
            dish.allergens?.forEach { item ->
                val imageView = ImageView(itemView.context)
                imageView.setImageResource(when (item) {
                    "soja" -> R.drawable.allergen_01_soja
                    "pescado" -> R.drawable.allergen_02_pescado
                    "mostaza" -> R.drawable.allergen_03_mostaza
                    "moluscos" -> R.drawable.allergen_04_moluscos
                    "lacteos" -> R.drawable.allergen_05_lacteos
                    "huevos" -> R.drawable.allergen_06_huevos
                    "sesamo" -> R.drawable.allergen_07_sesamo
                    "gluten" -> R.drawable.allergen_08_gluten
                    "frutos" -> R.drawable.allergen_09_frutos_de_cascara
                    "sulfitos" -> R.drawable.allergen_10_sulfitos
                    "crustaceos" -> R.drawable.allergen_11_crustaceos
                    "cacahuetes" -> R.drawable.allergen_12_cacahuetes
                    "apio" -> R.drawable.allergen_13_apio
                    "altramuces" -> R.drawable.allergen_14_altramuces
                    else -> R.drawable.allergen_01_soja
                })
                image.maxWidth = 16
                image.maxHeight = 16
                image.adjustViewBounds = true
                allergens.addView(imageView)
            }
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