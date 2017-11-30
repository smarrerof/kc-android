package com.sergiomarrero.dishr.activity

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import com.sergiomarrero.dishr.R
import com.sergiomarrero.dishr.model.Dishes
import com.sergiomarrero.dishr.model.Tables
import com.sergiomarrero.dishr.repository.DishRepository
import com.sergiomarrero.dishr.repository.TableRepository
import kotlinx.coroutines.experimental.Deferred
import kotlinx.coroutines.experimental.android.UI
import kotlinx.coroutines.experimental.async
import org.jetbrains.anko.coroutines.experimental.bg


class SplashScreenActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_splash_screen)

        val context = this

        async(UI) {
            val task: Deferred<Unit> = bg {
                downloadTables()
                downloadDishes()
            }

            task.await()

            finish()
            startActivity(MainActivity.intent(context))
        }
    }

    private fun downloadTables() {
        val repository = TableRepository()
        val tables = repository.get()
        Tables.add(tables)
    }

    private fun downloadDishes() {
        val repository = DishRepository()
        val dishes = repository.get()
        Dishes.add(dishes)
    }
}
