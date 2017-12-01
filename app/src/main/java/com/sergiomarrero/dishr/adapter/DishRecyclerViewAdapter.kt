package com.sergiomarrero.dishr.adapter

import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.GridLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.ViewTreeObserver
import android.widget.ImageView

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
        val allergens = itemView.findViewById<RecyclerView>(R.id.dish_allergens)
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

            allergens.layoutManager = GridLayoutManager(itemView.context, 4)
            allergens.itemAnimator = DefaultItemAnimator()
            allergens.adapter = AllergenRecyclerViewAdapter(dish)

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