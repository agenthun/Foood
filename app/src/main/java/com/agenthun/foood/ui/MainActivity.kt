package com.agenthun.foood.ui

import android.os.Bundle
import android.support.design.widget.BottomSheetBehavior
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.agenthun.foood.R
import kotlinx.android.synthetic.main.activity_main.*

/**
 * @project Foood
 * @authors agenthun
 * @date    2018/9/8 20:32.
 */
class MainActivity : AppCompatActivity() {
    private lateinit var bottomDrawerBehavior: BottomSheetBehavior<View>
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        bottomDrawerBehavior = BottomSheetBehavior.from(bottomDrawer)
        bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN

        bar.setNavigationIcon(R.drawable.ic_restaurant_menu_white_24dp)
        bar.replaceMenu(R.menu.menu_main)
        bar.setNavigationOnClickListener { bottomDrawerBehavior.state = BottomSheetBehavior.STATE_EXPANDED }
        bar.setOnMenuItemClickListener {
            return@setOnMenuItemClickListener when (it.itemId) {
                R.id.edit -> true
                else -> false
            }
        }
    }

    override fun onBackPressed() {
        if (bottomDrawerBehavior.state != BottomSheetBehavior.STATE_HIDDEN) {
            bottomDrawerBehavior.state = BottomSheetBehavior.STATE_HIDDEN
        } else {
            super.onBackPressed()
        }
    }
}
