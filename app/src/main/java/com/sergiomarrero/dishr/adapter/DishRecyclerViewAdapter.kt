package com.sergiomarrero.dishr.adapter

import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes


class DishRecyclerViewAdapter(val dishes: Dishes?): RecyclerView.Adapter<DishRecyclerViewAdapter.DishViewHolder>() {

    var onClickListener: View.OnClickListener? = null

    // ViewHolder
    inner class DishViewHolder(itemView: View): RecyclerView.ViewHolder(itemView) {

        val name = itemView.findViewById<TextView>(R.id.dish_name)
        val price = itemView.findViewById<TextView>(R.id.dish_price)
        val allergens = itemView.findViewById<TextView>(R.id.dish_allergens)

        fun bindDish(dish: Dish) {
            name.text = dish.name
            price.text = "Precio: ${dish.price}€"
            dish.allergens.forEach { item ->
                allergens.append(item + " ")
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

    override fun getItemCount() = dishes?.count ?: 0
}