package com.sergiomarrero.dishr.activity

import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.app.AlertDialog
import android.widget.FrameLayout
import android.widget.TextView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.fragment.OrderFragment
import com.sergiomarrero.dishr.fragment.TableListFragment
import com.sergiomarrero.dishr.model.Dish


class MainActivity: AppCompatActivity(), TableListFragment.OnTableSelectedListener, OrderFragment.OnAddDishListener {

    companion object {
        fun intent(context: Context): Intent {
            val intent = Intent(context, MainActivity::class.java)
            intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {
            if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
                val fragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit()
            }
        }
    }

    override fun onTableSelected(position: Int) {
        if (findViewById<FrameLayout>(R.id.order_fragment) != null) {
            val orderFragment = fragmentManager.findFragmentById(R.id.order_fragment) as OrderFragment
            orderFragment.showTable(position)
        } else {
            val fragment = OrderFragment.newInstance(position)
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }

    override fun onAddDish() {
        startActivityForResult(DishListActivity.intent(this), OrderFragment.REQUEST_DISH)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == OrderFragment.REQUEST_DISH && resultCode == AppCompatActivity.RESULT_OK) {
            // Get dish
            val dish = data?.getSerializableExtra(DishListActivity.EXTRA_DISH) as? Dish
            if (dish != null) {
                // Get dish notes
                val dialogView = layoutInflater.inflate(R.layout.dialog_add_dish, null)
                val dishNotes = dialogView.findViewById<TextView>(R.id.dish_notes)

                AlertDialog.Builder(this)
                        .setTitle(getString(R.string.add_notes_title))
                        .setMessage(getString(R.string.add_notes_text))
                        .setView(dialogView)
                        .setPositiveButton(android.R.string.ok, { _, _ ->
                            updateTable(dish, dishNotes.text.toString())
                        })
                        .show()
            }
        }
    }


    private fun updateTable(dish: Dish, notes: String) {
        if (findViewById<FrameLayout>(R.id.order_fragment) != null) {
            val orderFragment = fragmentManager.findFragmentById(R.id.order_fragment) as? OrderFragment
            orderFragment?.updateTable(dish, notes)
        } else {
            val orderFragment = fragmentManager.findFragmentById(R.id.fragment_container) as? OrderFragment
            orderFragment?.updateTable(dish, notes)
        }
    }
}
