package me.sukru.carpettravel.common.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder

fun Fragment.showWarning(text: String, title: String, hasNegativeButton: Boolean = false, onDoneClickListener: () -> Unit = {} ) {
    context?.let { it1 ->
        val builder = MaterialAlertDialogBuilder(it1)
            .setTitle(title)
            .setMessage(text)
            .setPositiveButton("Done") { _, _ -> onDoneClickListener()}
        if(hasNegativeButton) builder.setNegativeButton("Cancel") { _, _ -> }
        builder.show()
    }
}