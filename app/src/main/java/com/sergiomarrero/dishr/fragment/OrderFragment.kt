package com.sergiomarrero.dishr.fragment

import android.app.Fragment
import android.content.Intent
import android.os.Bundle
import android.support.design.widget.FloatingActionButton
import android.support.design.widget.Snackbar
import android.support.v7.app.AlertDialog
import android.support.v7.app.AppCompatActivity
import android.util.Log
import android.view.*
import android.widget.ArrayAdapter
import android.widget.ImageView
import android.widget.ListView
import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.activity.DishListActivity
import com.sergiomarrero.dishr.activity.OrderActivity
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.OrderItem
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class OrderFragment: Fragment() {

    companion object {
        val ARG_TABLE_POSITION = "ARG_TABLE_POSITION"

        fun newInstance(tablePosition: Int): OrderFragment {
            val arguments = Bundle()
            arguments.putInt(ARG_TABLE_POSITION, tablePosition)
            val fragment = OrderFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    lateinit var listView: ListView
    lateinit var addDishButton: FloatingActionButton
    lateinit var table: Table


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_order, container, false)
            listView = root.findViewById(R.id.order_list)
            addDishButton = root.findViewById(R.id.add_dish_button)

            // Get arguments from intent
            val tableIndex = arguments?.getInt(ARG_TABLE_POSITION) ?: 0
            table = Tables[tableIndex]

            setAdapter()

            // listView events
            listView.setOnItemLongClickListener { _, _, position, _ ->
                AlertDialog.Builder(activity)
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
            listView.emptyView = root.findViewById(R.id.order_list_empty)

            // addButton events
            addDishButton.setOnClickListener { _ ->
                startActivityForResult(DishListActivity.intent(activity), OrderActivity.REQUEST_DISH)
            }
        }

        Log.v("Dishr", "OrderFragment")

        return root
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OrderActivity.REQUEST_DISH && resultCode == AppCompatActivity.RESULT_OK) {
            // Get dish
            val dish = data?.getSerializableExtra(DishListActivity.EXTRA_DISH) as? Dish
            if (dish != null) {
                // Get dish notes
                val dialogView = activity.layoutInflater.inflate(R.layout.dialog_add_dish, null)
                val dishNotes = dialogView.findViewById<TextView>(R.id.dish_notes)

                AlertDialog.Builder(activity)
                        .setTitle("Añadir notas")
                        .setMessage("Introduce las notas del cliente")
                        .setView(dialogView)
                        .setPositiveButton(android.R.string.ok, { _, _ ->
                            table.order.add(dish, dishNotes.text.toString())
                            setAdapter()
                        })
                        .show()

            }

        }
    }

    override fun onCreateOptionsMenu(menu: Menu?, inflater: MenuInflater?) {
        super.onCreateOptionsMenu(menu, inflater)
        inflater?.inflate(R.menu.menu_order, menu)
    }

    override fun onOptionsItemSelected(item: MenuItem?)= when (item?.itemId) {
        R.id.order_total -> {
            calculateTotal()
            true
        }
        R.id.order_empty -> {
            cleanTable()
            true
        }
        else -> super.onOptionsItemSelected(item)
    }

    private fun setAdapter() {
        listView.adapter = object: ArrayAdapter<OrderItem>(activity, R.layout.list_view_item_order, table.order.toArray()) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
                val view = convertView ?: LayoutInflater.from(context).inflate(R.layout.list_view_item_order, parent, false)

                val orderItem = table.order[position]

                val image1 = view.findViewById<ImageView>(R.id.image1)
                val text1 = view.findViewById<TextView>(R.id.text1)
                val text2 = view.findViewById<TextView>(R.id.text2)

                image1.setImageResource(when (orderItem.dish.image) {
                    "dish_01" -> R.drawable.dish_01
                    "dish_02" -> R.drawable.dish_02
                    "dish_03" -> R.drawable.dish_03
                    "dish_04" -> R.drawable.dish_04
                    else -> R.drawable.dish_unknown
                })
                text1.text = orderItem.dish.name
                text2.text = orderItem.notes

                return view
            }
        }
    }

    private fun calculateTotal() {
        val total = table.order.total()

        AlertDialog.Builder(activity)
                .setTitle(R.string.app_name)
                .setMessage("${total} €")
                .show()
    }

    private fun cleanTable() {
        table.order.clear()
        setAdapter()

        Snackbar.make(activity.findViewById(android.R.id.content), "La mesa ha sido vacia con éxito", Snackbar.LENGTH_LONG)
                .show()
    }


    fun showTable(position: Int) {
        table = Tables[position]
        setAdapter()
    }
}