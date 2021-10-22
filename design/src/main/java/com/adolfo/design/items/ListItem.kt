package com.adolfo.design.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.adolfo.core.extensions.loadFromUrl
import com.adolfo.design.databinding.ItemListBinding

class ListItem @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding: ItemListBinding = ItemListBinding.inflate(
        LayoutInflater.from(context), this
    )

    fun inflateItem(name: String, avatarUrl: String) {
        binding.tvName.text = name

        with(avatarUrl) {
            if (this.isNotEmpty() && this.isNotBlank()) {
                binding.ivAvatar.loadFromUrl(avatarUrl)
            }
        }
    }
}
