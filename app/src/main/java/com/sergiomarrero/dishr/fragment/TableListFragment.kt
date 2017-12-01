package com.sergiomarrero.dishr.fragment

import android.app.Activity
import android.app.Fragment
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables


class TableListFragment: Fragment() {

    companion object {
        fun newInstance() = TableListFragment()
    }

    lateinit var root: View
    private var onTableSelectedListener: OnTableSelectedListener? = null
    lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_table_list, container, false)
            listView = root.findViewById<ListView>(R.id.table_list)

            setAdapter()

            // Handle click
            listView.setOnItemClickListener { _, _, position, _ ->
                onTableSelectedListener?.onTableSelected(position)
            }
        }

        return root
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        commonAttach(context)
    }

    @Suppress("OverridingDeprecatedMethod", "DEPRECATION")
    override fun onAttach(activity: Activity?) {
        super.onAttach(activity)
        commonAttach(activity)
    }

    override fun onDetach() {
        super.onDetach()
        onTableSelectedListener = null
    }

    private fun setAdapter() {
        listView.adapter = ArrayAdapter<Table>(activity, android.R.layout.simple_list_item_1, Tables.toArray())
    }

    private fun commonAttach(listener: Any?) {
        if (listener is OnTableSelectedListener) {
            onTableSelectedListener = listener
        }
    }


    interface OnTableSelectedListener {
        fun onTableSelected(position: Int)
    }

}