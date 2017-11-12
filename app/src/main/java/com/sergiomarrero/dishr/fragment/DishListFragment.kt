package com.sergiomarrero.dishr.fragment

import android.app.Fragment
import android.os.Bundle
import android.support.v7.widget.DefaultItemAnimator
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.adapter.DishRecyclerViewAdapter
import com.sergiomarrero.dishr.model.Dish
import com.sergiomarrero.dishr.model.Dishes
import java.io.Serializable


class DishListFragment: Fragment() {

    companion object {
        val ARG_DISHES = "ARG_DISHES"

        fun newInstance(dishes: List<Dish>): DishListFragment {
            val arguments = Bundle()
            arguments.putSerializable(ARG_DISHES, dishes as Serializable)

            val fragment = DishListFragment()
            fragment.arguments = arguments

            return fragment
        }
    }

    lateinit var root: View
    lateinit var recyclerView: RecyclerView
    lateinit var dishes: List<Dish>

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {
        super.onCreateView(inflater, container, savedInstanceState)

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_dish_list, container, false)

            // Get dishes from arguments
            dishes = arguments?.getSerializable(ARG_DISHES) as List<Dish>

            // Configure recyclerView
            recyclerView = root.findViewById<RecyclerView>(R.id.dish_list)
            recyclerView.layoutManager = LinearLayoutManager(activity)
            recyclerView.itemAnimator = DefaultItemAnimator()
            setAdapter()
        }

        return root
    }

    fun refreshDishList() {
        setAdapter()
    }

    private fun setAdapter() {
        recyclerView.adapter = DishRecyclerViewAdapter(dishes)
    }

}