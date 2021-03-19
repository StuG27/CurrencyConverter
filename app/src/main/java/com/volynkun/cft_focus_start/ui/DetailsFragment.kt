package com.volynkun.cft_focus_start.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.doOnTextChanged
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.navArgs
import com.volynkun.cft_focus_start.data.Currency
import com.volynkun.cft_focus_start.data.ViewModel
import com.volynkun.cft_focus_start.databinding.FragmentDetailsBinding


class DetailsFragment : Fragment() {

    private lateinit var binding: FragmentDetailsBinding
    private val args: DetailsFragmentArgs by navArgs()
    private var currency: Currency? = null
    private val viewModel: ViewModel by viewModels()
    private var filtered = listOf<Currency>()
    private var currencies = listOf<Currency>()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentDetailsBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        findCurrencyById()
        binding.tVCode.text = currency?.CharCode
        binding.eTRUB.doOnTextChanged { _, _, _, _ ->
            calculate()
        }
    }

    private fun findCurrencyById() {
        currencies = viewModel.getCurrencies()
        filtered = currencies.filter { it.ID == args.id }
        currency = if (filtered.isEmpty()) {
            null
        } else {
            filtered[0]
        }
    }

    private fun calculate() {
        if (binding.eTRUB.text.isNotEmpty()) {
            val valueRUB = binding.eTRUB.text.toString().toDouble()
            val valueCurrency = (valueRUB / currency!!.Value) * currency!!.Nominal.toDouble()
            binding.tVValue.text = valueCurrency.toString()
        } else {
            binding.tVValue.text = ""
        }
    }
}