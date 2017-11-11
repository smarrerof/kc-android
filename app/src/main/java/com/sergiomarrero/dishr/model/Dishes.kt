package com.sergiomarrero.dishr.model

object Dishes {
    private var dishes = mutableListOf<Dish>()

    val count
        get() = dishes.size


    operator fun get(i: Int) = dishes[i]


    fun toArray() = dishes.toTypedArray()

    fun add(element: Dish) {
        dishes.add(element)
    }

    fun add(mutableList: MutableList<Dish>) {
        dishes.addAll(mutableList)
    }
}