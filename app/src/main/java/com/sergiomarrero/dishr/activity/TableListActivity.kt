package com.sergiomarrero.dishr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.ListView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class TableListActivity : AppCompatActivity() {

    val listView by lazy { findViewById<ListView>(R.id.table_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        setAdapter()

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


}
