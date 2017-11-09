package com.sergiomarrero.dishr.model

import java.io.Serializable

data class Table(var id: String, var name: String): Serializable {
    override fun toString() = name
}