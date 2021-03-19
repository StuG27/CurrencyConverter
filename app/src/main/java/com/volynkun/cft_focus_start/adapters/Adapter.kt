package com.volynkun.cft_focus_start.adapters

import androidx.recyclerview.widget.DiffUtil
import com.hannesdorfmann.adapterdelegates4.AsyncListDifferDelegationAdapter
import com.volynkun.cft_focus_start.data.Currency


class Adapter(
    onItemClick: (id: String) -> Unit,
) : AsyncListDifferDelegationAdapter<Currency>(PersonDiffUtilCallback()) {

    init {
        delegatesManager.addDelegate(AdapterDelegate(onItemClick))
    }

    class PersonDiffUtilCallback : DiffUtil.ItemCallback<Currency>() {

        override fun areItemsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem.ID == newItem.ID
        }

        override fun areContentsTheSame(oldItem: Currency, newItem: Currency): Boolean {
            return oldItem == newItem
        }
    }
}