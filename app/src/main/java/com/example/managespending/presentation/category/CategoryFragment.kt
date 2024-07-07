package com.example.managespending.presentation.category

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.widget.addTextChangedListener
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.databinding.FragmentCategoryBinding
import com.example.managespending.presentation.activity.MainActivity
import com.example.managespending.presentation.budget.BudgetViewModel
import com.example.managespending.presentation.viewpager.ViewPagerAdapter
import com.google.android.material.tabs.TabLayoutMediator

class CategoryFragment : Fragment() {
    private lateinit var binding: FragmentCategoryBinding
    private lateinit var viewPagerAdapter: ViewPagerAdapter
    private var isFiltering = false
    private lateinit var viewModel: CategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_category, container, false)
        return (binding.root)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        viewModel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        viewModel.navigateToMain.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                val intent = Intent(requireContext().applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        })
        binding.categoryViewModel = viewModel
        binding.lifecycleOwner = viewLifecycleOwner
        setupView()
        handleEvent()
    }

    private fun handleEvent() {
        binding.etsearch.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            }

            override fun afterTextChanged(s: Editable?) {

            }
        })
    }
    private fun setupView() {
        viewPagerAdapter = ViewPagerAdapter(this)
        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "Spend"
                1 -> tab.text = "Income"
            }
        }.attach()
    }

    companion object {
        fun newInstance() : CategoryFragment {
            val args = Bundle()
            val fragment = CategoryFragment()
            fragment.arguments = args
            return fragment
        }
    }
}