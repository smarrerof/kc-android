package com.sergiomarrero.dishr.model

import java.io.Serializable


object Tables: Serializable {
    private var tables = mutableListOf<Table>()

    val count
        get() = tables.size


    operator fun get(i: Int) = tables[i]


    fun toArray() = tables.toTypedArray()

    fun add(element: Table) {
        tables.add(element)
    }

    fun add(mutableList: MutableList<Table>) {
        tables.addAll(mutableList)
    }

    fun clear() {
        tables = mutableListOf<Table>()
    }
}