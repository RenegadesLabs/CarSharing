package com.cardee.util

import android.content.Context
import android.widget.TextView
import com.cardee.R

class TitleDelegate(context: Context) {

    private val saveSuffixes: Array<String>
    private val valueSuffixes: Array<String>

    init {
        saveSuffixes = context.resources.getStringArray(R.array.btn_save_title_suffixes)
        valueSuffixes = context.resources.getStringArray(R.array.days_availability_suffixes)
    }

    fun onDatesCountTitleChange(view: TextView, count: Int) {
        val index = if (count > 1) 2 else count
        val prefix = if(index == 0)  "" else "$count "
        val title = "$prefix + ${saveSuffixes[index]}"
        view.text = title
    }

}
