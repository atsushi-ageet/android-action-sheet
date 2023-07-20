package com.ageet.actionsheet

import android.content.Context
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AlertDialog

class ActionSheetDialog(
    context: Context,
    private val titleText: String = "",
    private val cancelText: String = "",
    private val items: List<String> = emptyList(),
    private var onItemClickListener: OnItemClickListener? = null,
) : AlertDialog(context, R.style.actionsheet_ThemeOverlay_App_BottomSheetDialog) {
    fun interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private val view: ViewGroup by lazy { findViewById(R.id.actionsheet_content)!! }
    private val itemsContainer: ViewGroup by lazy { view.findViewById(R.id.actionsheet_items_container) }
    private val title: TextView by lazy { view.findViewById(R.id.actionsheet_title) }
    private val titleDivider: View by lazy { view.findViewById(R.id.actionsheet_title_divider) }
    private val cancel: TextView by lazy { view.findViewById(R.id.actionsheet_cancel) }
    private val list: ListView by lazy { view.findViewById(R.id.actionsheet_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actionsheet_content)
        itemsContainer.clipToOutline = true
        if (titleText.isEmpty()) {
            title.visibility = View.GONE
            titleDivider.visibility = View.GONE
        } else {
            title.text = titleText
        }
        if (cancelText.isEmpty()) {
            cancel.visibility = View.GONE
        } else {
            cancel.text = cancelText
            cancel.setOnClickListener {
                dismiss()
            }
        }
        list.adapter = ArrayAdapter(view.context, R.layout.actionsheet_item, R.id.text, items)
        list.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener?.onItemClick(position)
            dismiss()
        }
        window?.attributes?.apply {
            width = WindowManager.LayoutParams.MATCH_PARENT
            height = WindowManager.LayoutParams.WRAP_CONTENT
            gravity = Gravity.BOTTOM
            dimAmount = 0.5f
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }
}