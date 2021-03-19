package com.volynkun.cft_focus_start.adapters

import androidx.recyclerview.widget.RecyclerView
import com.volynkun.cft_focus_start.R
import com.volynkun.cft_focus_start.data.Currency
import com.volynkun.cft_focus_start.databinding.ItemCurrencyBinding


abstract class BaseHolder(
    binding: ItemCurrencyBinding,
    onItemClick: (id: String) -> Unit,
) : RecyclerView.ViewHolder(binding.root) {

    private var currentId: String? = null

    init {
        binding.root.setOnClickListener {
            currentId?.let {
                onItemClick(it)
            }
        }
    }

    private val tVRate = binding.tVRate
    private val context = binding.root.context

    protected fun bindMainInfo(
        id: String,
        name: String,
        value: Double,
        nominal: Int
    ) {
        currentId = id
        tVRate.text = context.getString(R.string.rate, nominal, name, value.toString())
    }
}