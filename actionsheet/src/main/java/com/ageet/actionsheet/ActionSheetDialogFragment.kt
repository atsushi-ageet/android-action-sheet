package com.ageet.actionsheet

import android.app.Dialog
import android.os.Bundle
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

class ActionSheetDialogFragment : DialogFragment() {
    var title: String by stringArgument()
    var message: String by stringArgument()
    var cancel: String by stringArgument()
    var items: List<String> by stringListArgument()

    init {
        arguments = Bundle()
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        return ActionSheetDialog(
            context = requireContext(),
            title = title,
            message = message,
            cancel = cancel,
            items = items,
        ).apply {
            setOnItemClickListener(onItemClickListener)
        }
    }

    override fun getTheme(): Int = R.style.actionsheet_ThemeOverlay_App_BottomSheetDialog

    private var onItemClickListener: ActionSheetDialog.OnItemClickListener? = null

    fun setOnItemClickListener(listener: ActionSheetDialog.OnItemClickListener) {
        onItemClickListener = listener
    }

    companion object {
        fun findByTag(fragmentManager: FragmentManager, tag: String): ActionSheetDialogFragment? {
            return fragmentManager.findFragmentByTag(tag) as? ActionSheetDialogFragment
        }

        private fun stringArgument(): ReadWriteProperty<Fragment, String> = object : ReadWriteProperty<Fragment, String> {
            override fun getValue(thisRef: Fragment, property: KProperty<*>): String {
                return thisRef.arguments?.getString(property.name).orEmpty()
            }
            override fun setValue(thisRef: Fragment, property: KProperty<*>, value: String) {
                thisRef.arguments ?: run { thisRef.arguments = Bundle() }
                thisRef.arguments?.putString(property.name, value)
            }
        }
        private fun stringListArgument(): ReadWriteProperty<Fragment, List<String>> = object : ReadWriteProperty<Fragment, List<String>> {
            override fun getValue(thisRef: Fragment, property: KProperty<*>): List<String> {
                return thisRef.arguments?.getStringArrayList(property.name).orEmpty()
            }
            override fun setValue(thisRef: Fragment, property: KProperty<*>, value: List<String>) {
                thisRef.arguments ?: run { thisRef.arguments = Bundle() }
                thisRef.arguments?.putStringArrayList(property.name, ArrayList(value))
            }
        }
    }
}
