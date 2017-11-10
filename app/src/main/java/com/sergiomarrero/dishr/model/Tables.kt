package com.sergiomarrero.dishr.model

object Tables {
    private var tables = mutableListOf<Table>()

    val count
        get() = tables.size


    operator fun get(i: Int) = tables[i]


    fun toArray() = tables.toTypedArray()

    fun add(table: Table) {
        tables.add(table)
    }
}