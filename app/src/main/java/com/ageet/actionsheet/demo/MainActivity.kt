package com.ageet.actionsheet.demo

import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.appcompat.app.AppCompatActivity
import com.ageet.actionsheet.ActionSheetDialog
import com.ageet.actionsheet.ActionSheetDialogFragment

class MainActivity : AppCompatActivity(R.layout.activity_main) {
    private val onItemClickListener = ActionSheetDialog.OnItemClickListener { position ->
        Log.d(LOG_TAG, "Select position $position")
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (savedInstanceState != null) {
            ActionSheetDialogFragment.findByTag(supportFragmentManager, FRAGMENT_TAG)?.setOnItemClickListener(onItemClickListener)
        }
        val dummy = (1..5).map { i ->
            "Item $i"
        }
        val actionSheetButton = findViewById<Button>(R.id.action_sheet_button)
        actionSheetButton.setOnClickListener {
            ActionSheetDialog(
                context = this,
                title = "Title",
                message = "Message",
                cancel = "Cancel",
                items = dummy,
                onItemClickListener = onItemClickListener,
            ).show()
        }
        val actionSheetFragmentButton = findViewById<Button>(R.id.action_sheet_fragment_button)
        actionSheetFragmentButton.setOnClickListener {
            ActionSheetDialogFragment().apply {
                title = "Title"
                message = "Message"
                cancel = "Cancel"
                items = dummy
                setOnItemClickListener(onItemClickListener)
            }.show(supportFragmentManager, FRAGMENT_TAG)
        }
    }

    companion object {
        private const val LOG_TAG: String = "MainActivity"
        private const val FRAGMENT_TAG: String = "ActionSheet"
    }
}
