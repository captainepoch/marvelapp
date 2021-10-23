package com.adolfo.design.items

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.adolfo.core.extensions.empty
import com.adolfo.design.R
import com.adolfo.design.databinding.ItemDetailInformationBinding

class ItemDetailInformation @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ItemDetailInformationBinding.inflate(
        LayoutInflater.from(context), this
    )

    private var title: String = String.empty()

    init {
        attrs?.let { attr ->
            val typedArray = context.obtainStyledAttributes(attr, R.styleable.ItemDetailInformation)

            title = typedArray.getString(R.styleable.ItemDetailInformation_idiTitle)
                ?: String.empty()

            typedArray.recycle()
        }

        setTitle()
    }

    private fun setTitle() {
        binding.tvTitle.text = title
    }

    fun setDescription(description: String? = String.empty()) {
        binding.tvDescription.text =
            if (description.isNullOrEmpty() || description.isNullOrBlank()) {
                context.getString(R.string.item_detail_information_description_placeholder)
            } else {
                description
            }
    }
}
