package com.sergiomarrero.dishr.model

import java.io.Serializable


class OrderItem(var dish: Dish, var notes: String): Serializable {

    override fun toString() = dish.name
}