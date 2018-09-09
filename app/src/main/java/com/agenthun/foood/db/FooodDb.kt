package com.agenthun.foood.db

import android.arch.persistence.room.Database
import android.arch.persistence.room.Room
import android.arch.persistence.room.RoomDatabase
import android.content.Context
import com.agenthun.foood.vo.Recipe

/**
 * @project Foood
 * @authors agenthun
 * @date    2018/9/8 20:57.
 */
@Database(
        entities = [Recipe::class],
        version = 1,
        exportSchema = false
)
abstract class FooodDb : RoomDatabase() {
    companion object {
        private const val DB_NAME = "foood.db"

        @Volatile
        private var INSTANCE: FooodDb? = null

        fun getInstance(context: Context): FooodDb =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: buildDatabase(context).also { INSTANCE = it }
                }

        private fun buildDatabase(context: Context) =
                Room.databaseBuilder(context.applicationContext,
                        FooodDb::class.java, DB_NAME)
                        .build()
    }

    abstract fun recipeDao(): RecipeDao
}