package com.agenthun.foood.vo

import android.arch.persistence.room.Entity
import android.arch.persistence.room.PrimaryKey
import com.google.gson.annotations.SerializedName

/**
 * @project Foood
 * @authors agenthun
 * @date    2018/9/9 18:22.
 */
@Entity(tableName = "recipes")
data class Recipe(
        @PrimaryKey
        @SerializedName("id")
        val id: Long,
        @SerializedName("title")
        val title: String = "",
        @SerializedName("description")
        val description: String = ""
)
