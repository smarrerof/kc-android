package com.sergiomarrero.dishr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.ViewSwitcher
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.TableRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class TableListActivity : AppCompatActivity() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        VIEW(1)
    }

    val viewSwitcher by lazy { findViewById<ViewSwitcher>(R.id.table_list_view_switcher) }
    val listView by lazy { findViewById<ListView>(R.id.table_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_list)

        viewSwitcher.displayedChild = VIEW_INDEX.LOADING.index

        // Get tables from API
        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadTables()
            }

            task.await()

            setAdapter()
            viewSwitcher.displayedChild = VIEW_INDEX.VIEW.index
        }

        // Handle click
        listView.setOnItemClickListener { _, _, position, _ ->
            // Move to order activity
            val intent = OrderActivity.intent(this, position)
            startActivity(intent)
        }
    }

    private fun setAdapter() {
        listView.adapter = ArrayAdapter<Table>(this, android.R.layout.simple_list_item_1, Tables.toArray())
    }

    private fun downloadTables() {
        val repository = TableRepository()
        val tables = repository.get()

        // TODO Improve this. Every time that the activity is created we are downloading the table list.
        Tables.clear()
        Tables.add(tables)
    }
}
