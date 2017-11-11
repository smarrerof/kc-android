package com.sergiomarrero.dishr.activity

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
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.DishRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class DishListActivity : AppCompatActivity() {

    //val listView by lazy { findViewById<ListView>(R.id.dish_list) }
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.dish_list) }

    companion object {
        fun intent(context: Context): Intent {
            val intent = Intent(context, DishListActivity::class.java)
            return intent
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)

        // Configure recyclerView
        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.itemAnimator = DefaultItemAnimator()

        // Get dishes from API
        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadDishes()
            }

            task.await()

            setAdapter()
        }

        // Handle click
        /*listView.setOnClickListener { v: View? ->

        }*/
    }

    private fun setAdapter() {
        //listView.adapter = ArrayAdapter<Dish>(this, android.R.layout.simple_list_item_1, Dishes.toArray())
        val adapter = DishRecyclerViewAdapter(Dishes)
        recyclerView.adapter = adapter
    }

    private fun downloadDishes() {
        val repository = DishRepository()
        val dishes = repository.get()
        Dishes.add(dishes)
    }
}
