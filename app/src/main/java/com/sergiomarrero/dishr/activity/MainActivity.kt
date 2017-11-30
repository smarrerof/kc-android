package com.sergiomarrero.dishr.activity

import android.app.Fragment
import android.content.Context
import android.content.Intent
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


class MainActivity: AppCompatActivity(), TableListFragment.OnTableSelectedListener {

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

        Log.v("Dishr", "MainActivity")

        if (findViewById<FrameLayout>(R.id.fragment_container) != null) {
            //if (savedInstanceState == null) {
            if (fragmentManager.findFragmentById(R.id.fragment_container) == null) {
                val fragment = TableListFragment.newInstance()
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, fragment)
                        .commit()
            }
            //}
        }


    }

    override fun onTableSelected(position: Int) {
        Log.v("Dishr", "MainActivity.onTableSelected")

        val orderFragment = fragmentManager.findFragmentById(R.id.order_fragment) as OrderFragment
        if (orderFragment != null) {
            orderFragment.showTable(position)
        } else {
            val fragment = OrderFragment.newInstance(position)
            fragmentManager.beginTransaction()
                    .replace(R.id.fragment_container, fragment)
                    .addToBackStack(null)
                    .commit()
        }
    }
}
