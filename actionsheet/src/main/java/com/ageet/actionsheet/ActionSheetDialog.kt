package com.ageet.actionsheet

import android.content.Context
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import android.widget.TextView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetDialog

class ActionSheetDialog(
    context: Context,
    private val titleText: String = "",
    private val cancelText: String = "",
    private val items: List<String> = emptyList(),
    private var onItemClickListener: OnItemClickListener? = null,
) : BottomSheetDialog(context, R.style.actionsheet_ThemeOverlay_App_BottomSheetDialog) {
    fun interface OnItemClickListener {
        fun onItemClick(position: Int)
    }

    private val view: View by lazy { findViewById(R.id.actionsheet_content)!! }
    private val title: TextView by lazy { view.findViewById(R.id.actionsheet_title) }
    private val titleDivider: View by lazy { view.findViewById(R.id.actionsheet_title_divider) }
    private val cancel: TextView by lazy { view.findViewById(R.id.actionsheet_cancel) }
    private val list: ListView by lazy { view.findViewById(R.id.actionsheet_list) }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.actionsheet_content)
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
        list.adapter = object : ArrayAdapter<String>(view.context, R.layout.actionsheet_item, R.id.text, items) {
            override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
                return super.getView(position, convertView, parent).apply {
                    val backgroundId = when {
                        position == 0 && titleText.isEmpty() -> R.drawable.actionsheet_bg_item_first
                        position == items.lastIndex -> R.drawable.actionsheet_bg_item_last
                        else -> R.drawable.actionsheet_bg_item
                    }
                    setBackgroundResource(backgroundId)
                }
            }
        }
        list.setOnItemClickListener { _, _, position, _ ->
            onItemClickListener?.onItemClick(position)
            dismiss()
        }
        window?.attributes?.apply {
            dimAmount = 0.5f
        }
        behavior.apply {
            isDraggable = false
            setState(BottomSheetBehavior.STATE_EXPANDED)
        }
    }

    fun setOnItemClickListener(listener: OnItemClickListener?) {
        onItemClickListener = listener
    }
}