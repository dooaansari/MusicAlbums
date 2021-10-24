package com.app.musicalbums.custom_views

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.musicalbums.R
import com.app.musicalbums.databinding.FontIconBinding

class FontIcon @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var binding: FontIconBinding = FontIconBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    fun setText(icon: Int){
        binding.icon.text = context.getString(icon)
    }

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.FontIcon, 0, 0
        )

        val iconText: Int = typedArray.getResourceId(R.styleable.FontIcon_iconValue, 0)
        binding.icon.text = context.getString(iconText)
        typedArray.recycle()
    }
}