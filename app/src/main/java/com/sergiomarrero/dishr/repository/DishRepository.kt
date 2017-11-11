package com.sergiomarrero.dishr.repository

import com.sergiomarrero.dishr.common.JsonRequest
import com.sergiomarrero.dishr.model.Dish
import org.json.JSONArray

class DishRepository {

    private val ENDPOINT = "dish"

    fun get(): MutableList<Dish> {
        val request = JsonRequest()
        val json = request.run(ENDPOINT)
        val jsonRoot = JSONArray(json)

        val dishes: MutableList<Dish> = mutableListOf()
        for (index in 0..jsonRoot.length() - 1) {
            val table = jsonRoot.getJSONObject(index)
            dishes.add(Dish.from(table))
        }
        return dishes
    }
}