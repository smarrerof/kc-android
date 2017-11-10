package com.sergiomarrero.dishr.repository

import com.sergiomarrero.dishr.common.JsonRequest
import com.sergiomarrero.dishr.model.Table
import org.json.JSONArray

class TableRepository {

    private val ENDPOINT = "table"

    fun get(): MutableList<Table> {
        val request = JsonRequest()
        val json = request.run(ENDPOINT)
        val jsonRoot = JSONArray(json)

        val tables: MutableList<Table> = mutableListOf()
        for (index in 0..jsonRoot.length() - 1) {
            val table = jsonRoot.getJSONObject(index)
            tables.add(Table.from(table))
        }
        return tables
    }
}