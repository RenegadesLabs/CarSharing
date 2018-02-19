package com.cardee.util.ui

import android.text.Editable
import android.text.TextWatcher
import android.widget.EditText
import com.cardee.util.ui.input_strategy.InputStrategy
import java.lang.ref.WeakReference

class InputInteractionController {

    private val map = mutableMapOf<Int, InputStrategy>()

    fun addInput(input: EditText, strategy: InputStrategy) {
        map.put(input.id, strategy)
        val weakInput = WeakReference(input)
        input.addTextChangedListener(object : TextWatcher {
            override fun afterTextChanged(p0: Editable?) {

            }

            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {

            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
                weakInput.get()?.let { input ->
                    val watcher = this
                    map[input.id]?.apply {
                        input.removeTextChangedListener(watcher)
                        execute(input, p0.toString())
                        input.addTextChangedListener(watcher)
                    }
                }
            }
        })
    }

    fun valueOf(input: EditText): String {
        val strategy = map[input.id] ?:
                throw IllegalArgumentException("Controller doesn't contain current EditText")
        return strategy.valueOf(input)
    }

    fun clear() {
        map.clear()
    }
}
