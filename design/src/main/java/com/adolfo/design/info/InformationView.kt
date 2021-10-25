package com.adolfo.design.info

import android.content.Context
import android.graphics.drawable.Drawable
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.adolfo.core.extensions.gone
import com.adolfo.core.extensions.isNull
import com.adolfo.design.databinding.ViewInfoErrorBinding
import com.adolfo.design.info.InformationView.ACTION.PRIMARY_ACTION
import com.adolfo.design.info.InformationView.ACTION.SECONDARY_ACTION

class InformationView @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private val binding = ViewInfoErrorBinding.inflate(
        LayoutInflater.from(context), this
    )
    var onActionClick: (ACTION) -> Unit = {}

    fun setErrorInfo(
        image: Drawable?,
        title: String?,
        description: String?,
        primaryActionText: String?,
        secondaryActionText: String? = null,
    ) {
        if (image != null) {
            binding.errorImage.setImageDrawable(image)
        } else {
            binding.errorImage.gone()
        }

        if (!title.isNull()) {
            binding.errorTitle.text = title
        } else {
            binding.errorTitle.gone()
        }

        if (!description.isNull()) {
            binding.errorText.text = description
        } else {
            binding.errorText.gone()
        }

        if (!primaryActionText.isNull()) {
            binding.buttonPrimary.text = primaryActionText
            binding.buttonPrimary.setOnClickListener {
                onActionClick(PRIMARY_ACTION)
            }
        } else {
            binding.buttonPrimary.gone()
        }

        if (!secondaryActionText.isNull()) {
            binding.buttonSecondary.text = secondaryActionText
            binding.buttonSecondary.setOnClickListener {
                onActionClick(SECONDARY_ACTION)
            }
        } else {
            binding.buttonSecondary.gone()
        }
    }

    enum class ACTION {
        PRIMARY_ACTION,
        SECONDARY_ACTION
    }
}
