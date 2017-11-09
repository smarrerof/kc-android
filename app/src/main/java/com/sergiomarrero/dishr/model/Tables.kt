package com.sergiomarrero.dishr.model

object Tables {
    private var tables = mutableListOf<Table>(
            Table("1", "Mesa 1"),
            Table("2", "Mesa 2"),
            Table("3", "Mesa 3"),
            Table("4", "Mesa 4"),
            Table("5", "Mesa 5")
    )

    val count
        get() = tables.size

    fun toArray() = tables.toTypedArray()

    operator fun get(i: Int) = tables[i]
}