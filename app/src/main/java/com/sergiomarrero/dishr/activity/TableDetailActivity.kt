package com.sergiomarrero.dishr.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.view.View
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.fragment.DishListFragment
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class TableDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TABLE = "EXTRA_TABLE"

        fun intent(context: Context, position: Int): Intent {
            val intent = Intent(context, TableDetailActivity::class.java)
            intent.putExtra(EXTRA_TABLE, position)
            return intent
        }
    }

    val addDishButton by lazy { findViewById<FloatingActionButton>(R.id.add_dish_button) }
    lateinit var table: Table
    lateinit var fragment: DishListFragment

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_table_detail)

        // Get arguments from intent
        val position = intent.getIntExtra(EXTRA_TABLE, 0)
        table = Tables[position]

        // Load fragment with table information
        if (fragmentManager.findFragmentById(R.id.fragment_table_detail) == null) {
            fragment = DishListFragment.newInstance(table.dishes)
            fragmentManager.beginTransaction()
                    .add(R.id.fragment_table_detail, fragment)
                    .commit()
        }

        // Add dish
        addDishButton.setOnClickListener { v: View ->
            Snackbar.make(v, "Hemos hecho click", Snackbar.LENGTH_LONG)
                    .show()

            // Add fake dish
            val dish = Dish("1", "Hamburguesa gourmet", 14.95f, "dish_01", listOf("1", "2", "3"))
            table.dishes.add(dish)
            fragment.refreshDishList()
        }
    }
}
