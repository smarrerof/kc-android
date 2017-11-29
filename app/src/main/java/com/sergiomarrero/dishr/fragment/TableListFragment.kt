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
import android.widget.ViewSwitcher
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.activity.OrderActivity
import com.sergiomarrero.dishr.activity.TableListActivity
import com.sergiomarrero.dishr.model.Table
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.TableRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class TableListFragment: Fragment() {

    enum class VIEW_INDEX(val index: Int) {
        LOADING(0),
        VIEW(1)
    }

    companion object {
        fun newInstance() = TableListFragment()
    }

    lateinit var root: View
    private var onTableSelectedListener: OnTableSelectedListener? = null
    lateinit var viewSwitcher: ViewSwitcher
    lateinit var listView: ListView


    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View {

        if (inflater != null) {
            root = inflater.inflate(R.layout.fragment_table_list, container, false)
            viewSwitcher = root.findViewById<ViewSwitcher>(R.id.table_list_view_switcher)
            listView = root.findViewById<ListView>(R.id.table_list)

            viewSwitcher.displayedChild = TableListActivity.VIEW_INDEX.LOADING.index

            // Get tables from API
            /*async(UI) {
                val task: Deferred<Unit> = bg {
                    downloadTables()
                }

                task.await()

                setAdapter()
                viewSwitcher.displayedChild = TableListActivity.VIEW_INDEX.VIEW.index
            }*/

            setAdapter()
            viewSwitcher.displayedChild = TableListActivity.VIEW_INDEX.VIEW.index

            // Handle click
            listView.setOnItemClickListener { _, _, position, _ ->
                Log.v("Dishr", "TableListFragment.listView.setOnItemClickListener")
                onTableSelectedListener?.onTableSelected(position)
            }
        }

        Log.v("Dishr", "TableListFragment")

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

    private fun downloadTables() {
        val repository = TableRepository()
        val tables = repository.get()

        // TODO Improve this. Every time that the activity is created we are downloading the table list.
        Tables.clear()
        Tables.add(tables)
    }


    interface OnTableSelectedListener {
        fun onTableSelected(position: Int)
    }

}