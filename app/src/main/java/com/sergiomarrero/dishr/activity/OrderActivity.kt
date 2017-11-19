package com.sergiomarrero.dishr.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.v7.app.AlertDialog
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.OrderItem
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class OrderActivity : AppCompatActivity() {

    companion object {
        val EXTRA_TABLE = "EXTRA_TABLE"
        val REQUEST_DISH = 1

        fun intent(context: Context, position: Int): Intent {
            val intent = Intent(context, OrderActivity::class.java)
            intent.putExtra(EXTRA_TABLE, position)
            return intent
        }
    }

    val addDishButton by lazy { findViewById<FloatingActionButton>(R.id.add_dish_button) }
    val listView by lazy { findViewById<ListView>(R.id.order_list) }
    lateinit var table: Table

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order)

        // Get arguments from intent
        val position = intent.getIntExtra(OrderActivity.EXTRA_TABLE, 0)
        table = Tables[position]

        setAdapter()


        // listView events
        listView.setOnItemLongClickListener { parent, view, position, id ->
            AlertDialog.Builder(this)
                    .setTitle("Borrar")
                    .setMessage("¿Está seguro que desea borrar este elemento?")

                    .setPositiveButton(android.R.string.ok, { _, _ ->
                        table.order.remove(position)
                        setAdapter()
                    })
                    .setNegativeButton(android.R.string.cancel, { _, _ ->

                    })
                    .show()
            true
        }

        // addButton events
        addDishButton.setOnClickListener { v: View ->
            /*val dish = Dish("1", "Hamburguesa gourmet", 14.95f, "dish_01", listOf("1", "2", "3"))
            table.order.add(dish, "")
            setAdapter()*/

            startActivityForResult(DishListActivity.intent(this), OrderActivity.REQUEST_DISH)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OrderActivity.REQUEST_DISH && resultCode == RESULT_OK) {
            // Get dish
            val dish = data?.getSerializableExtra(DishListActivity.EXTRA_DISH) as? Dish
            if (dish != null) {
                // Get dish notes
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_dish, null)
                val dishNotes = dialogView.findViewById<TextView>(R.id.dish_notes)

                AlertDialog.Builder(this)
                        .setTitle("Añadir notas")
                        .setMessage("Introduce las notas del cliente")
                        .setView(dialogView)
                        .setPositiveButton(android.R.string.ok, { _, _ ->
                            table.order.add(dish, dishNotes.text.toString())
                            setAdapter()
                        })
                        .setNegativeButton(android.R.string.cancel, { _, _ ->
                            table.order.add(dish, "")
                            setAdapter()
                        })
                        .show()

            }

        }
    }

    private fun setAdapter() {
        //listView.adapter = ArrayAdapter<OrderItem>(this, android.R.layout.simple_list_item_1, table.order.toArray())
        listView.adapter = object: ArrayAdapter<OrderItem>(this, R.layout.list_view_item_order, table.order.toArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                var view = convertView
                if (view == null) {
                    view = LayoutInflater.from(context).inflate(R.layout.list_view_item_order, parent, false)
                }

                val orderItem = table.order[position]

                val image1 = view!!.findViewById<ImageView>(R.id.image1)
                val text1 = view!!.findViewById<TextView>(R.id.text1)
                val text2 = view!!.findViewById<TextView>(R.id.text2)

                image1.setImageResource(when (orderItem.dish.image) {
                    "dish_01" -> R.drawable.dish_01
                    "dish_02" -> R.drawable.dish_02
                    "dish_03" -> R.drawable.dish_03
                    "dish_04" -> R.drawable.dish_04
                    else -> R.drawable.dish_unknown
                })
                text1.text = orderItem.dish.name
                text2.text = orderItem.notes

                return view!!
            }
        }
    }
}