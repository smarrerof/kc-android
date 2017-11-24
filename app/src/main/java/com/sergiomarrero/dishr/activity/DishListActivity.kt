package com.sergiomarrero.dishr.activity

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.View
import android.widget.ViewSwitcher
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.adapter.DishRecyclerViewAdapter
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.repository.DishRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class DishListActivity : AppCompatActivity() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        VIEW(1)
    }

    companion object {
        val EXTRA_DISH = "EXTRA_DISH"

        fun intent(context: Context): Intent {
            val intent = Intent(context, DishListActivity::class.java)
            return intent
        }
    }

    val viewSwitcher by lazy { findViewById<ViewSwitcher>(R.id.dish_list_view_switcher) }
    val recyclerView by lazy { findViewById<RecyclerView>(R.id.dish_list) }
    lateinit var dishes: List<Dish>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dish_list)

        viewSwitcher.displayedChild = TableListActivity.VIEW_INDEX.LOADING.index

        // Get dishes from API
        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadDishes()
            }

            task.await()

            // Get dishes locally
            dishes = Dishes.toList()

            // Configure recyclerView
            recyclerView.layoutManager = LinearLayoutManager(this@DishListActivity)
            recyclerView.itemAnimator = DefaultItemAnimator()
            setAdapter()
            viewSwitcher.displayedChild = TableListActivity.VIEW_INDEX.VIEW.index
        }
    }


    private fun downloadDishes() {
        val repository = DishRepository()
        val dishes = repository.get()

        // TODO Improve this. Every time that the activity is created we are downloading the dish list.
        Dishes.clear()
        Dishes.add(dishes)
    }


    private fun setAdapter() {
        // Set the adapter
        val adapter = DishRecyclerViewAdapter(dishes)
        recyclerView.adapter = adapter

        // Handle click
        adapter.onClickListener = View.OnClickListener { v: View? ->
            // Get selected dish
            val position = recyclerView.getChildAdapterPosition(v)
            val dish = dishes[position]

            // Return selected dish
            val intent = Intent()
            intent.putExtra(EXTRA_DISH, dish)

            setResult(Activity.RESULT_OK, intent)

            finish()
        }
    }
}
