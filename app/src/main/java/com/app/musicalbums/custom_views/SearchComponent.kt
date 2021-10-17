package com.app.musicalbums.custom_views


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import com.app.musicalbums.R
import com.app.musicalbums.databinding.SearchComponentBinding


class SearchComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private  var binding: SearchComponentBinding = SearchComponentBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SearchComponent, 0, 0
        )

        val hintText: String = typedArray.getString(R.styleable.SearchComponent_hintText) ?: ""
        binding.searchField.hint = hintText
        typedArray.recycle()
    }



}