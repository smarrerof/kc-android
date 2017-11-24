package com.sergiomarrero.dishr.model

import java.io.Serializable


object Dishes: Serializable {
    private var dishes = mutableListOf<Dish>()

    val count
        get() = dishes.size


    operator fun get(i: Int) = dishes[i]


    fun toArray() = dishes.toTypedArray()
    fun toList() = dishes.toList()

    fun add(element: Dish) {
        dishes.add(element)
    }

    fun add(mutableList: MutableList<Dish>) {
        dishes.addAll(mutableList)
    }

    fun clear() {
        dishes = mutableListOf<Dish>()
    }
}