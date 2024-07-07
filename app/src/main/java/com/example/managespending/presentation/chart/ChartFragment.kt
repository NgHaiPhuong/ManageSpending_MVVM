package com.example.managespending.presentation.chart

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.managespending.R
import com.example.managespending.databinding.FragmentChartBinding
import com.example.managespending.presentation.activity.MainActivity
import com.example.managespending.presentation.viewpager.ViewPagerAdapter_Chart
import com.google.android.material.tabs.TabLayoutMediator

class ChartFragment : Fragment(){
    private lateinit var binding : FragmentChartBinding
    private lateinit var viewpagerAdapter : ViewPagerAdapter_Chart
    private lateinit var viewModel: ChartViewModel

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_chart, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        viewModel = ViewModelProvider(this).get(ChartViewModel::class.java)
        viewModel.showPL.observe(viewLifecycleOwner, Observer {
            binding.viewDimBackground.visibility = View.VISIBLE
            binding.layoutPL.visibility = View.VISIBLE
        })
        viewModel.navigateToMain.observe(viewLifecycleOwner, Observer { navigate ->
            if (navigate) {
                val intent = Intent(requireContext().applicationContext, MainActivity::class.java)
                startActivity(intent)
            }
        })
        viewModel.showDate.observe(viewLifecycleOwner, Observer {
            binding.viewDimBackground.visibility = View.VISIBLE
            binding.layoutdate.visibility = View.VISIBLE
        })
        viewModel.showView.observe(viewLifecycleOwner, Observer {
            binding.viewDimBackground.visibility = View.GONE
            binding.layoutdate.visibility = View.GONE
            binding.layoutPL.visibility = View.GONE
        })
        binding.chartViewModel = viewModel
        setupView()
        initData()
        setUpRecyclerView()
    }
    @SuppressLint("ClickableViewAccessibility")
    private fun setUpRecyclerView() {
        binding.recyclermonth.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recyclermonth.adapter = com.example.managespending.date.MonthAdapter()

        binding.recycleryear.layoutManager = LinearLayoutManager(requireContext().applicationContext)
        binding.recycleryear.adapter = com.example.managespending.date.YearAdapter()
    }

    private fun initData() {

    }

    private fun setupView(){
        viewpagerAdapter = ViewPagerAdapter_Chart(this)
        binding.viewChart.adapter = viewpagerAdapter

        TabLayoutMediator(binding.tab, binding.viewChart){tab, position ->
            tab.customView = createTabView(position)
        }.attach()
    }
    @SuppressLint("InflateParams")
    private fun createTabView(position: Int): View {
        val view = LayoutInflater.from(requireContext()).inflate(R.layout.custom_tab, null)
        val icon = view.findViewById<ImageView>(R.id.tab_icon)
        val textView = view.findViewById<TextView>(R.id.tab_text)

        when (position) {
            0 -> {
                icon.setImageResource(R.drawable.line_chart)
                textView.text = "Bar Chart"
            }
            1 -> {
                icon.setImageResource(R.drawable.line)
                textView.text = "Line Chart"
            }
        }
        return view
    }

    companion object {
        fun newInstance() : ChartFragment {
            val args = Bundle()
            val fragment = ChartFragment()
            fragment.arguments = args
            return fragment
        }
    }
}