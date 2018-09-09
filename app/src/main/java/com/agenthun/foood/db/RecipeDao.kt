package com.agenthun.foood.db

import android.arch.paging.DataSource
import android.arch.persistence.room.Dao
import android.arch.persistence.room.Insert
import android.arch.persistence.room.OnConflictStrategy
import android.arch.persistence.room.Query
import com.agenthun.foood.vo.Recipe

/**
 * @project Foood
 * @authors agenthun
 * @date    2018/9/9 18:26.
 */
@Dao
interface RecipeDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    fun add(recipe: Recipe)

    @Query("SELECT * FROM recipes")
    fun getRecipes(): DataSource.Factory<Int, Recipe>
}