package com.volynkun.cft_focus_start.ui

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.widget.SearchView
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.DividerItemDecoration
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.volynkun.cft_focus_start.R
import com.volynkun.cft_focus_start.adapters.Adapter
import com.volynkun.cft_focus_start.data.ViewModel
import com.volynkun.cft_focus_start.databinding.FragmentListBinding
import com.volynkun.cft_focus_start.extensions.ItemOffsetDecoration
import com.volynkun.cft_focus_start.extensions.autoCleared


class ListFragment : Fragment() {

    private lateinit var binding: FragmentListBinding
    private var currencyAdapter: Adapter by autoCleared()
    private val viewModel: ViewModel by viewModels()
    private var json: String? = null
    private lateinit var preferences: SharedPreferences

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        super.onCreateView(inflater, container, savedInstanceState)
        binding = FragmentListBinding.inflate(inflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = this.requireActivity().getSharedPreferences(JSON, Context.MODE_PRIVATE)
        restore()
        initList()
        bindViewModel()
        initToolbar()
    }

    override fun onStop() {
        super.onStop()
        saveJson()
    }

    private fun saveJson() {
        val editor: SharedPreferences.Editor = preferences.edit()
        editor.putString(JSON_STRING, viewModel.getMyJson()).apply()
    }

    private fun restore() {
        json = preferences.getString(JSON_STRING, null)
        viewModel.updateJson(json)
    }

    private fun initList() {
        currencyAdapter = Adapter { id ->
            val actions = ListFragmentDirections
                .actionListFragmentToDetailsFragment(id)
            findNavController().navigate(actions)
        }
        with(binding.rV) {
            adapter = currencyAdapter
            layoutManager = LinearLayoutManager(requireContext()).apply {
                orientation = RecyclerView.VERTICAL
            }
            setHasFixedSize(true)
            val dividerItemDecoration =
                DividerItemDecoration(requireContext(), DividerItemDecoration.VERTICAL)
            addItemDecoration(dividerItemDecoration)
            addItemDecoration(
                ItemOffsetDecoration(
                    requireContext(),
                    10,
                    10,
                    10,
                    10
                )
            )
        }
    }

    private fun initToolbar() {
        binding.toolbar.title = "Конвертер валюты"
        val searchItem = binding.toolbar.menu.findItem(R.id.search)
        (searchItem.actionView as SearchView).setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String?): Boolean {
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                viewModel.list.observe(viewLifecycleOwner) {
                    currencyAdapter.items = it.filter { currency ->
                        (currency.CharCode.contains(newText ?: "", true)
                                || currency.Name.contains(newText ?: "", true))
                    }.toMutableList()
                }
                return true
            }
        })
    }

    private fun bindViewModel() {
        viewModel.list.observe(viewLifecycleOwner) { currencyAdapter.items = it }
        viewModel.isLoading.observe(viewLifecycleOwner, ::updateLoadingState)
        viewModel.search()
        binding.swipeRefresh.setColorSchemeColors(resources.getColor(R.color.orange_dark))
        binding.swipeRefresh.setOnRefreshListener {
            viewModel.search()
        }
    }

    private fun updateLoadingState(isLoading: Boolean) {
        binding.rV.isVisible = isLoading.not()
        binding.swipeRefresh.isRefreshing = isLoading
    }

    companion object {
        private const val JSON = "json"
        private const val JSON_STRING = "jsonString"
    }
}