package com.example.managespending.presentation.chart

import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.databinding.FragmentLineBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.model.Transaction
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.BarEntry
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.formatter.ValueFormatter
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.interfaces.datasets.ILineDataSet
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.utils.ColorTemplate
import org.jetbrains.skia.Color

class LineFragment : Fragment(), OnChartValueSelectedListener{
    private lateinit var binding: FragmentLineBinding
    private lateinit var myViewModel: MyViewModel
    private var listTransaction : MutableList<Transaction> = ArrayList()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentLineBinding.inflate(layoutInflater)
        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setupDatabase()
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
    private fun designChart1() {
        // cấu hình biểu đồ
        binding.incomeChart.apply {
            description.isEnabled = true
            description.text = "INCOME"
            description.textColor = android.graphics.Color.parseColor("#C0C0C0")
            description.setPosition(220f, 50f)
            description.textSize = 20f
            description.typeface = Typeface.DEFAULT_BOLD
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(true)  // tắt lưới
            maxHighlightDistance = 300f
            setBackgroundColor(Color.WHITE)
        }

        // cấu hình legend (chú thích)
        val legend = binding.incomeChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK

        // cấu hình trục x
        val xAxis = binding.incomeChart.xAxis
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.axisMinimum = 0f // Bắt đầu từ 0

        // cấu hình trục y
        val yAxisLeft = binding.incomeChart.axisLeft
        yAxisLeft.textColor = Color.BLACK
        yAxisLeft.setDrawAxisLine(true)
        yAxisLeft.axisMinimum = 0f

        val yAxisRight = binding.incomeChart.axisRight
        yAxisRight.isEnabled = false
    }
    private fun setData1(key:String) {
        val values = ArrayList<Entry>()

        listTransaction.forEachIndexed { index, item ->
            val value = item.cost
            values.add(BarEntry(index.toFloat(), value, item.name))
        }

        val set1: LineDataSet

        if (binding.incomeChart.data != null && binding.incomeChart.data.dataSetCount > 0) {
            set1 = binding.incomeChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding.incomeChart.data.notifyDataChanged()
            binding.incomeChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, key)

            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f // điều chỉnh mức độ cong
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.BLACK)
            set1.highLightColor = Color.CYAN
            set1.color = ColorTemplate.VORDIPLOM_COLORS[0]
            set1.fillColor = ColorTemplate.VORDIPLOM_COLORS[0]
            set1.fillAlpha = 100

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)

            val data = LineData(dataSets)
            binding.incomeChart.data = data
        }
    }

    private fun designChart() {
        // cấu hình biểu đồ
        binding.expendChart.apply {
            description.isEnabled = true
            description.text = "SPEND"
            description.textColor = android.graphics.Color.parseColor("#C0C0C0")
            description.setPosition(200f, 50f)
            description.textSize = 20f
            description.typeface = Typeface.DEFAULT_BOLD
            setTouchEnabled(true)
            isDragEnabled = true
            setScaleEnabled(true)
            setPinchZoom(true)
            setDrawGridBackground(true)  // tắt lưới
            maxHighlightDistance = 300f
            setBackgroundColor(Color.WHITE)
        }

        // cấu hình legend (chú thích)
        val legend = binding.expendChart.legend
        legend.form = Legend.LegendForm.LINE
        legend.textColor = Color.BLACK

        // cấu hình trục x
        val xAxis = binding.expendChart.xAxis
        xAxis.textColor = Color.BLACK
        xAxis.position = XAxis.XAxisPosition.BOTTOM
        xAxis.setDrawAxisLine(false)
        xAxis.axisMinimum = 0f // Bắt đầu từ 0

        // cấu hình trục y
        val yAxisLeft = binding.expendChart.axisLeft
        yAxisLeft.textColor = Color.BLACK
        yAxisLeft.setDrawAxisLine(true)
        yAxisLeft.axisMinimum = 0f

        val yAxisRight = binding.expendChart.axisRight
        yAxisRight.isEnabled = false
    }
    private fun setData(key:String) {
        val values = ArrayList<Entry>()

        listTransaction.forEachIndexed { index, item ->
            val value = item.cost
            values.add(BarEntry(index.toFloat(), value, item.name))
        }

        val set1: LineDataSet

        if (binding.expendChart.data != null && binding.expendChart.data.dataSetCount > 0) {
            set1 = binding.expendChart.data.getDataSetByIndex(0) as LineDataSet
            set1.values = values
            binding.expendChart.data.notifyDataChanged()
            binding.expendChart.notifyDataSetChanged()
        } else {
            set1 = LineDataSet(values, key)

            set1.mode = LineDataSet.Mode.CUBIC_BEZIER
            set1.cubicIntensity = 0.2f // điều chỉnh mức độ cong
            set1.setDrawFilled(true)
            set1.setDrawCircles(true)
            set1.lineWidth = 1.8f
            set1.circleRadius = 4f
            set1.setCircleColor(Color.BLACK)
            set1.highLightColor = Color.CYAN
            set1.color = ColorTemplate.VORDIPLOM_COLORS[0]
            set1.fillColor = ColorTemplate.VORDIPLOM_COLORS[0]
            set1.fillAlpha = 100

            val dataSets = ArrayList<ILineDataSet>()
            dataSets.add(set1)

            val data = LineData(dataSets)
            binding.expendChart.data = data
        }
    }

    override fun onValueSelected(e: Entry?, h: Highlight?) {

    }
    override fun onNothingSelected() {

    }
    companion object {
        fun newInstance() : LineFragment{
            val args = Bundle()
            val fragment = LineFragment()
            fragment.arguments = args
            return fragment
        }
    }
}