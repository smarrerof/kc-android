package com.sergiomarrero.dishr.model

import org.json.JSONObject
import java.io.Serializable


class Dish(var id: String, var name: String, var price: Float, var image: String, var allergens: List<String>, val description: String): Serializable {

    companion object {
        fun from(json: JSONObject): Dish {
            val id = json.getString("id")
            val name = json.getString("name")
            val price = json.getDouble("price").toFloat()
            val image = json.getString("image")
            val description = json.getString("description")

            val allergens: MutableList<String> = mutableListOf()
            if (json.has("allergens")) {
            var allergensArray = json.getJSONArray("allergens")
                for (index in 0..allergensArray.length() - 1) {
                    allergens.add(allergensArray[index].toString())
                }
            }

            return Dish(id, name, price, image, allergens, description)
        }
    }

    override fun toString() = name
}