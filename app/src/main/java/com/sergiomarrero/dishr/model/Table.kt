package com.sergiomarrero.dishr.model

import org.json.JSONObject
import java.io.Serializable


data class Table(var id: String, var name: String, var order: Order): Serializable {

    companion object {
        fun from(json: JSONObject): Table {
            val id = json.getString("id")
            val name = json.getString("name")
            return Table(id, name, Order())
        }
    }

    override fun toString() = name

}