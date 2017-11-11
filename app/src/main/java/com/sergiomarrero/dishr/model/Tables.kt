package com.sergiomarrero.dishr.model

object Tables {
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
}