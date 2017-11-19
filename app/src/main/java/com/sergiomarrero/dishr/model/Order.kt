package com.sergiomarrero.dishr.model

import java.io.Serializable


class Order(): Serializable {
    private var items = mutableListOf<OrderItem>()

    val count
        get() = items.size


    operator fun get(i: Int) = items[i]


    fun toArray() = items.toTypedArray()
    fun toList() = items.toList()

    fun add(element: OrderItem) {
        items.add(element)
    }

    fun add(mutableList: MutableList<OrderItem>) {
        items.addAll(mutableList)
    }

    fun add(dish: Dish, notes: String) {
        items.add(OrderItem(dish, notes))
    }

    fun remove(position: Int) {
        items.removeAt(position)
    }
}