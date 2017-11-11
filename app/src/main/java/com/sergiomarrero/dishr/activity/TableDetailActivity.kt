package com.sergiomarrero.dishr.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Table


class TableDetailActivity : AppCompatActivity() {

    val addDishButton by lazy { findViewById<FloatingActionButton>(R.id.add_dish_button) }

    companion object {
        val EXTRA_TABLE = "EXTRA_TABLE"

        fun intent(context: Context, table: Table): Intent {
            val intent = Intent(context, TableDetailActivity::class.java)
            intent.putExtra(EXTRA_TABLE, table)
            return intent
        }
    }

    lateinit var table: Table

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_detail)

        // Get arguments from intent
        table = intent.getSerializableExtra(EXTRA_TABLE) as Table

        // Add dish
        addDishButton.setOnClickListener { v: View ->
            Snackbar.make(v, "Hemos hecho click", Snackbar.LENGTH_LONG)
                    .show()
        }
    }
}
