package com.volynkun.cft_focus_start.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import com.hannesdorfmann.adapterdelegates4.AbsListItemAdapterDelegate
import com.volynkun.cft_focus_start.data.Currency
import com.volynkun.cft_focus_start.databinding.ItemCurrencyBinding


class AdapterDelegate(
    private val onItemClick: (id: String) -> Unit,
) :
    AbsListItemAdapterDelegate<Currency, Currency, AdapterDelegate.CurrencyHolder>() {

    override fun isForViewType(
        item: Currency,
        items: MutableList<Currency>,
        position: Int
    ): Boolean {
        return true
    }

    override fun onCreateViewHolder(parent: ViewGroup): CurrencyHolder {
        val layoutInflater = LayoutInflater.from(parent.context)
        val binding = ItemCurrencyBinding.inflate(layoutInflater, parent, false)
        return CurrencyHolder(binding, onItemClick)
    }

    override fun onBindViewHolder(
        item: Currency,
        holder: CurrencyHolder,
        payloads: MutableList<Any>
    ) {
        holder.bind(item)
    }

    class CurrencyHolder(
        binding: ItemCurrencyBinding,
        onItemClick: (id: String) -> Unit
    ) : BaseHolder(binding, onItemClick) {

        fun bind(currency: Currency) {
            bindMainInfo(currency.ID, currency.CharCode, currency.Value, currency.Nominal)
        }
    }
}