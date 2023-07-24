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
    private val title: String? = null,
    private val message: String? = null,
    private val cancel: String? = null,
    private val items: List<String> = emptyList(),
    private var onItemClickListener: OnItemClickListener? = null,
) : AlertDialog(context, R.style.actionsheet_ThemeOverlay_App_BottomSheetDialog) {
    fun interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private val view: ViewGroup by lazy { findViewById(R.id.actionsheet_content)!! }
    private val itemsContainerView: ViewGroup by lazy { view.findViewById(R.id.actionsheet_items_container) }
    private val titleView: TextView by lazy { view.findViewById(R.id.actionsheet_title) }
    private val messageView: TextView by lazy { view.findViewById(R.id.actionsheet_message) }
    private val titleDividerView: View by lazy { view.findViewById(R.id.actionsheet_title_divider) }
    private val cancelView: TextView by lazy { view.findViewById(R.id.actionsheet_cancel) }
    private val itemsView: ListView by lazy { view.findViewById(R.id.actionsheet_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actionsheet_content)
        itemsContainerView.clipToOutline = true
        titleView.text = title.orEmpty()
        if (title == null) {
            titleView.visibility = View.GONE
        }
        messageView.text = message.orEmpty()
        if (message == null) {
            messageView.visibility = View.GONE
        }
        if (title == null && message == null) {
            titleDividerView.visibility = View.GONE
        }
        cancelView.text = cancel.orEmpty()
        if (cancel == null) {
            cancelView.visibility = View.GONE
        } else {
            cancelView.setOnClickListener {
                dismiss()
            }
        }
        itemsView.adapter = ArrayAdapter(view.context, R.layout.actionsheet_item, R.id.text, items)
        itemsView.setOnItemClickListener { _, _, position, _ ->
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