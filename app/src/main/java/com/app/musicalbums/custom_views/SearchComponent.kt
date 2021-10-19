package com.app.musicalbums.custom_views


import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.widget.doAfterTextChanged
import com.app.musicalbums.R
import com.app.musicalbums.databinding.SearchComponentBinding
import com.app.musicalbums.helpers.InputValidator


class SearchComponent @JvmOverloads constructor(
    context: Context,
    attrs: AttributeSet? = null,
    defStyle: Int = 0
) : ConstraintLayout(context, attrs, defStyle) {

    private var binding: SearchComponentBinding = SearchComponentBinding.inflate(
        LayoutInflater.from(context),
        this,
        true
    )

    init {
        val typedArray = context.obtainStyledAttributes(
            attrs,
            R.styleable.SearchComponent, 0, 0
        )

        val hintText: Int = typedArray.getResourceId(R.styleable.SearchComponent_hintTextSearch, 0)
        binding.searchField.hint = context.getString(hintText)
        binding.searchField.editText?.doAfterTextChanged {
            enableButton(it.toString())
        }
        binding.searchButton.setOnClickListener {
            searchButtonClick()
        }
        typedArray.recycle()
    }

    fun enableButton(text: String){
        binding.searchButton.isEnabled = InputValidator.validateName(text) && text.isNotBlank()
    }

    fun getSearchText() = binding.searchField.editText?.text.toString()

    var searchButtonClick = {}


}