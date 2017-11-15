package com.sergiomarrero.dishr.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.view.View
import android.widget.ArrayAdapter
import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.fragment.DishListFragment
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class TableDetailActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TABLE = "EXTRA_TABLE"
        val REQUEST_DISH = 1

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
            // Add fake dish
            //val dish = Dish("1", "Hamburguesa gourmet", 14.95f, "dish_01", listOf("1", "2", "3"))
            //table.dishes.add(dish)
            //fragment.refreshDishList()

            // Start activity (DishListActivity)
            startActivityForResult(DishListActivity.intent(this), REQUEST_DISH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == REQUEST_DISH && resultCode == RESULT_OK) {
            // Get dish
            val dish = data?.getSerializableExtra(DishListActivity.EXTRA_DISH) as? Dish
            if (dish != null) {
                table.dishes.add(dish)

                // Get dish notes
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_dish, null)
                val dishNotes = dialogView.findViewById<TextView>(R.id.dish_notes)

                AlertDialog.Builder(this)
                        .setTitle("Añadir notas")
                        .setMessage("Introduce las notas del cliente")
                        .setView(dialogView)
                        .setPositiveButton(android.R.string.ok, { _, _ ->
                            dish.name = dishNotes.text.toString()
                            fragment.refreshDishList()
                        })
                        .setNegativeButton(android.R.string.cancel, { _, _ ->
                            fragment.refreshDishList()
                        })
                        .show()

            }

        }
    }
}
