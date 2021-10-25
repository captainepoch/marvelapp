package com.adolfo.design.common.extensions

import android.graphics.drawable.Drawable
import com.adolfo.core.extensions.visible
import com.adolfo.design.info.InformationView

fun InformationView.oneAction(
    image: Drawable?,
    title: String,
    description: String,
    action: String
) {
    this.visible()
    this.bringToFront()
    this.setErrorInfo(image, title, description, action)
}

fun InformationView.actions(
    image: Drawable?,
    title: String,
    description: String,
    primaryAction: String,
    secondaryAction: String
) {
    this.visible()
    this.bringToFront()
    this.setErrorInfo(image, title, description, primaryAction, secondaryAction)
}
