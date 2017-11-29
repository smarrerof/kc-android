package com.sergiomarrero.dishr.activity

import android.app.Fragment
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.FrameLayout
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.fragment.OrderFragment
import com.sergiomarrero.dishr.fragment.TableListFragment
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.TableRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class MainActivity : AppCompatActivity(), TableListFragment.OnTableSelectedListener {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        Log.v("Dishr", "MainActivity")

        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadTables()
            }

            task.await()

            if (findViewById<FrameLayout>(R.id.fragment_container) == null) {
                if (findViewById<View>(R.id.table_list_fragment) != null) {
                    if (fragmentManager.findFragmentById(R.id.table_list_fragment) == null) {
                        val fragment = TableListFragment.newInstance()
                        fragmentManager.beginTransaction()
                                .add(R.id.table_list_fragment, fragment)
                                .commit()
                    }
                }

                if (findViewById<View>(R.id.order_fragment) != null) {
                    if (fragmentManager.findFragmentById(R.id.order_fragment) == null) {
                        val fragment = OrderFragment.newInstance(0)
                        fragmentManager.beginTransaction()
                                .add(R.id.order_fragment, fragment)
                                .commit()
                    }
                }
            } else {
                if (savedInstanceState == null) {
                    val fragment = TableListFragment.newInstance()
                    fragmentManager.beginTransaction()
                            .add(R.id.fragment_container, fragment)
                            .commit()
                }
            }
        }

    }

    override fun onTableSelected(position: Int) {
        Log.v("Dishr", "MainActivity.onTableSelected")
        // Show order activity
        val orderFragment = fragmentManager.findFragmentById(R.id.order_fragment) as? OrderFragment
        if (orderFragment == null) {
            val fragment = OrderFragment.newInstance(position)
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        } else {
            orderFragment.showTable(position)
        }
    }


    private fun downloadTables() {
        Log.v("Dishr", "running downloadTables")
        val repository = TableRepository()
        val tables = repository.get()

        // TODO Improve this. Every time that the activity is created we are downloading the table list.
        Tables.clear()
        Tables.add(tables)
    }
}
