package com.sergiomarrero.dishr.model

import org.json.JSONObject

class Dish(var id: String, var name: String, var price: Float, var thumbImage: String, var image: String, var allergens: List<String>) {

    companion object {
        fun from(json: JSONObject): Dish {
            val id = json.getString("id")
            val name = json.getString("name")
            val price = json.getDouble("price").toFloat()
            val thumbImage = json.getString("thumb_image")
            val image = json.getString("image")

            var allergensArray = json.getJSONArray("allergens")
            val allergens: MutableList<String> = mutableListOf()
            for (index in 0..allergensArray.length() - 1) {
                allergens.add(allergensArray[index].toString())
            }

            return Dish(id, name, price, thumbImage, image, allergens)
        }
    }

    override fun toString() = name
}