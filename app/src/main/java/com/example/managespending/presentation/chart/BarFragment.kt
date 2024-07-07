package com.example.managespending.presentation.chart

import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.databinding.FragmentBarBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.model.Transaction
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarData
import com.github.mikephil.charting.data.BarDataSet
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate

class BarFragment : Fragment(), OnChartValueSelectedListener {
    private lateinit var binding : FragmentBarBinding
    private lateinit var myViewModel: MyViewModel
    private var listTransaction : MutableList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentBarBinding.inflate(layoutInflater)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatabase()
        // spend
        myViewModel.allTransactionList.observe(viewLifecycleOwner) { transactions ->
            listTransaction.clear()
            transactions.toMutableList().forEach { item->
                if(item.category.contains("spend", true)){
                    listTransaction.add(item)
                }
            }
            listTransaction.forEach { item ->
                Log.d("Phuong", "infor" + item.name + " " + item.cost)
            }
            Log.d("Phuong", "mn " + listTransaction.size.toString())

            designChart()
            setData("Spend")
        }

        // income
        myViewModel.allTransactionList.observe(viewLifecycleOwner){ transactions ->
            listTransaction.clear()
            transactions.toMutableList().forEach { item->
                if(item.category.contains("income", true)){
                    listTransaction.add(item)
                }
            }
            listTransaction.forEach { item ->
                Log.d("Phuong", "income" + item.name + " " + item.cost)
            }
            Log.d("Phuong", "mn " + listTransaction.size.toString())

            designChart1()
            setData1("Income")
        }
    }
    private fun setupDatabase() {
        val dao = MyDatabase.getInstance(requireContext().applicationContext).myDao()
        val factory = MyViewModelFactory(dao)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
    }
    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }
    override fun onNothingSelected() {

    }
    private fun designChart() {
        // cấu hình cho biểu đồ
        binding.expendChart.apply {
            description.isEnabled = true
            description.text = "SPEND"
            description.textColor = Color.parseColor("#C0C0C0")
            description.setPosition(200f, 50f)
            description.textSize = 20f
            description.typeface = Typeface.DEFAULT_BOLD
            setDrawGridBackground(false)
            setScaleEnabled(true)
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            setPinchZoom(true)
            setTouchEnabled(true)
        }

        // thiết lập legend (chú thích)
        val legend = binding.expendChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.yOffset = 10f

        // Cấu hình trục X
        val xAxis = binding.expendChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textColor = Color.BLACK

        // Cấu hình trục Y bên trái
        val yAxisLeft = binding.expendChart.axisLeft
        yAxisLeft.setDrawGridLines(true)
        yAxisLeft.textColor = Color.BLACK

        // Vô hiệu hóa trục Y bên phải
        val yAxisRight = binding.expendChart.axisRight
        yAxisRight.isEnabled = false
    }
    private fun setData(key : String) {
        val values = ArrayList<BarEntry>()

        listTransaction.forEachIndexed { index, item ->
            val value = item.cost
            values.add(BarEntry(index.toFloat(), value, item.name))
        }

        val set1: BarDataSet

        if (binding.expendChart.data != null && binding.expendChart.data.dataSetCount > 0) {
            set1 = binding.expendChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            binding.expendChart.data.notifyDataChanged()
            binding.expendChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, key)
            set1.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
            set1.valueTextColor = Color.BLACK
            set1.valueTextSize = 10f

            val data = BarData(set1)
            binding.expendChart.data = data
            binding.expendChart.invalidate()
            val barValueFormatter = BarValueFormatter()

            // Assigning the ValueFormatter to the DataSet
            set1.valueFormatter = barValueFormatter
        }
    }
    private class BarValueFormatter() : ValueFormatter() {
        override fun getBarLabel(barEntry: BarEntry): String {
            return "${barEntry.data}"
        }
    }
    private fun setData1(key : String) {
        val values = ArrayList<BarEntry>()

        listTransaction.forEachIndexed { index, item ->
            val value = item.cost
            values.add(BarEntry(index.toFloat(), value, item.name))
        }

        val set1: BarDataSet

        if (binding.incomeChart.data != null && binding.incomeChart.data.dataSetCount > 0) {
            set1 = binding.incomeChart.data.getDataSetByIndex(0) as BarDataSet
            set1.values = values
            binding.incomeChart.data.notifyDataChanged()
            binding.incomeChart.notifyDataSetChanged()
        } else {
            set1 = BarDataSet(values, key)
            set1.colors = ColorTemplate.VORDIPLOM_COLORS.toList()
            set1.valueTextColor = Color.BLACK
            set1.valueTextSize = 10f

            val data = BarData(set1)
            binding.incomeChart.data = data
            binding.incomeChart.invalidate()
            val barValueFormatter = BarValueFormatter()

            // Assigning the ValueFormatter to the DataSet
            set1.valueFormatter = barValueFormatter
        }
    }
    private fun designChart1() {
        // cấu hình cho biểu đồ
        binding.incomeChart.apply {
            description.isEnabled = true
            description.text = "INCOME"
            description.textColor = Color.parseColor("#C0C0C0")
            description.setPosition(220f, 50f)
            description.textSize = 20f
            description.typeface = Typeface.DEFAULT_BOLD
            setDrawGridBackground(false)
            setScaleEnabled(true)
            setDrawBarShadow(false)
            setDrawValueAboveBar(true)
            setPinchZoom(true)
            setTouchEnabled(true)
        }

        // thiết lập legend (chú thích)
        val legend = binding.incomeChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK
        legend.verticalAlignment = Legend.LegendVerticalAlignment.BOTTOM
        legend.horizontalAlignment = Legend.LegendHorizontalAlignment.LEFT
        legend.orientation = Legend.LegendOrientation.HORIZONTAL
        legend.setDrawInside(false)
        legend.yOffset = 10f

        // Cấu hình trục X
        val xAxis = binding.incomeChart.xAxis
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawGridLines(false)
        xAxis.textColor = Color.BLACK

        // Cấu hình trục Y bên trái
        val yAxisLeft = binding.incomeChart.axisLeft
        yAxisLeft.setDrawGridLines(true)
        yAxisLeft.textColor = Color.BLACK

        // Vô hiệu hóa trục Y bên phải
        val yAxisRight = binding.incomeChart.axisRight
        yAxisRight.isEnabled = false
    }
    companion object {
        fun newInstance() : BarFragment{
            val args = Bundle()
            val fragment = BarFragment()
            fragment.arguments = args
            return fragment
        }
    }
}