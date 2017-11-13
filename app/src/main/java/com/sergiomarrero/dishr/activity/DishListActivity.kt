package com.sergiomarrero.dishr.activity

import android.app.Activity
import android.app.Instrumentation
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ArrayAdapter
import android.widget.ListView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.adapter.DishRecyclerViewAdapter
import com.sergiomarrero.dishr.fragment.DishListFragment
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.DishRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class DishListActivity : AppCompatActivity(), DishListFragment.OnDishSelectedListener {

    companion object {
        val EXTRA_DISH = "EXTRA_DISH"

        fun intent(context: Context): Intent {
            val intent = Intent(context, DishListActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)

        // Get dishes from API
        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadDishes()
            }

            task.await()

            if (fragmentManager.findFragmentById(R.id.fragment_dish_list) == null) {
                val fragment = DishListFragment.newInstance(Dishes.toList())
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_dish_list, fragment)
                        .commit()
            }
        }
    }

    override fun onDishSelected(dish: Dish?) {
        val intent = Intent()
        intent.putExtra(EXTRA_DISH, dish)

        setResult(Activity.RESULT_OK, intent)

        finish()
    }

    private fun downloadDishes() {
        val repository = DishRepository()
        val dishes = repository.get()
        Dishes.add(dishes)
    }
}
