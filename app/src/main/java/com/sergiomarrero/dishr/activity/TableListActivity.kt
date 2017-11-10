package com.sergiomarrero.dishr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.common.JsonRequest
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.TableRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg
import org.json.JSONArray
import org.json.JSONObject


class TableListActivity : AppCompatActivity() {

    val listView by lazy { findViewById<ListView>(R.id.table_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        //setAdapter()

        // Get tables from API
        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadTables()
            }

            task.await()

            setAdapter()
        }

        // Handle click
        listView.setOnItemClickListener { _, _, position, _ ->
            // Get selected table
            val selectedTable = Tables[position]
            // Move to table activity
            val intent = TableDetailActivity.intent(this, selectedTable)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        listView.adapter = ArrayAdapter<Table>(this, android.R.layout.simple_list_item_1, Tables.toArray())
    }

    private fun downloadTables() {
        val repository = TableRepository()
        val tables = repository.get()
        Tables.add(tables)
    }
}
