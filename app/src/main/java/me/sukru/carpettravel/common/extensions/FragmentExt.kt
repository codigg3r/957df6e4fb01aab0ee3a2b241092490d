package me.sukru.carpettravel.common.extensions

import androidx.fragment.app.Fragment
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import me.sukru.carpettravel.R

fun Fragment.showAlertDialog(
    text: String? = null,
    title: String? = null,
    hasNegativeButton: Boolean = false,
    onDoneClickListener: () -> Unit = {}
) {
    context?.let { it1 ->
        val builder = MaterialAlertDialogBuilder(it1)
            .setPositiveButton(getString(R.string.done)) { _, _ -> onDoneClickListener() }
        title?.let { builder.setTitle(it) }
        text?.let { builder.setMessage(it) }
        if (hasNegativeButton) builder.setNegativeButton(getString(R.string.cancel)) { _, _ -> }
        builder.show()
    }
}