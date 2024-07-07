package com.example.managespending.presentation.insert

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.base.BaseActivity
import com.example.managespending.databinding.ActivityInsertBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.model.Transaction
import com.example.managespending.presentation.activity.MainActivity
import com.example.managespending.presentation.category.CategoryFragment
import com.example.managespending.presentation.viewpager.ViewPagerAdapter
import com.example.managespending.presentation.home.HomeFragment
import com.example.managespending.presentation.viewpager.ViewPagerAdapter2
import com.google.android.material.tabs.TabLayoutMediator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class InsertActivity : BaseActivity() {
    private lateinit var binding : ActivityInsertBinding
    private lateinit var myViewModel: MyViewModel
    private lateinit var homeFragment : HomeFragment
    private lateinit var viewPagerAdapter: ViewPagerAdapter2
    private lateinit var viewModel :InsertViewModel
    private var tinhtoan = ""
    private var pheptinh = ""
    private var so1 : Float = 0F
    private var so2 : Float = 0F
    private var kq : Float = 0F
    private var checkbang = false
    private var checkDot = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_insert)

        viewModel = ViewModelProvider(this).get(InsertViewModel::class.java)
        setupDatabase()
        setupView()
        handleEvent()
        binding.insertViewModel = viewModel
    }

    private fun setupView() {
        viewPagerAdapter = ViewPagerAdapter2(this)
        binding.viewpager.adapter = viewPagerAdapter

        TabLayoutMediator(binding.tabLayout, binding.viewpager) { tab, position ->
            when (position) {
                0 -> tab.text = "Spend"
                1 -> tab.text = "Income"
            }
        }.attach()
    }

    private fun setupDatabase() {
        val dao = MyDatabase.getInstance(application).myDao()
        val factory = MyViewModelFactory(dao)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
    }

    @SuppressLint("ClickableViewAccessibility")
    private fun handleEvent() {
        viewModel.navigateToMain.observe(this, Observer { navigate ->
            if(navigate){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        viewModel.showCalculator.observe(this, Observer {
            binding.calculation.visibility = View.VISIBLE
            binding.layout.visibility = View.GONE
            calculation()
            binding.viewDimBackground.visibility = View.VISIBLE
        })
        viewModel.showBackground.observe(this, Observer {
            binding.viewDimBackground.visibility = View.GONE
            binding.calculation.visibility = View.GONE
            binding.layout.visibility = View.GONE
        })
        viewModel.showList.observe(this, Observer {
            binding.layout.visibility = View.VISIBLE
            binding.calculation.visibility = View.GONE
            binding.viewDimBackground.visibility = View.VISIBLE
        })
        if(binding.btntru.isActivated || binding.btncong.isActivated||
            binding.btnnhan.isActivated || binding.btnchia.isActivated){
            binding.tvsum.visibility = View.VISIBLE
        }
        saveTransaction()
    }

    private fun saveTransaction(){
        viewModel.confirmSave.observe(this, Observer {confirm->
            if(confirm){
                val transaction : Transaction = viewModel.saveTrans()

                CoroutineScope(Dispatchers.IO).launch {
                    myViewModel.addTransaction(transaction)
                    withContext(Dispatchers.Main){
                        val bundle = Bundle()
                        homeFragment.arguments = bundle
                        supportFragmentManager.beginTransaction()
                            .add(R.id.fragment_container, homeFragment)
                            .commit()
                    }
                }
            }
        })
    }

    @SuppressLint("SetTextI18n")
    private fun calculation(){
        binding.btnclear.setOnClickListener {
            binding.tvnumber.text = ""
            binding.tvsum.text = ""
            binding.tvnumber.hint = "0"
            binding.tvsum.hint = "0"
            tinhtoan = ""
            pheptinh = ""
            checkDot = false
            checkbang = false
        }

        binding.btn1.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "1")
            }
        }

        binding.btn2.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "2")
            }
        }

        binding.btn3.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "3")
            }
        }

        binding.btn4.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "4")
            }
        }

        binding.btn5.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "5")
            }
        }

        binding.btn6.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "6")
            }
        }

        binding.btn7.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "7")
            }
        }

        binding.btn8.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "8")
            }
        }

        binding.btn9.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "9")
            }
        }

        binding.btn0.setOnClickListener {
            if(!checkbang){
                tinhtoan = binding.tvnumber.text.toString()
                binding.tvnumber.setText(tinhtoan + "0")
            }
        }

        binding.btnpphay.setOnClickListener {
            if(!checkbang){
                if(!checkDot){
                    tinhtoan = binding.tvnumber.text.toString()
                    binding.tvnumber.setText(tinhtoan + ".")
                    checkDot = true
                }
            }
        }

        binding.btncong.setOnClickListener {
            if(binding.tvnumber.text.equals("")){
                so1 = 0F
                binding.tvhour.setText("0 + ")
            }else{
                so1 = binding.tvnumber.text.toString().toFloat()
                binding.tvsum.setText(binding.tvnumber.text.toString() + "+")
                binding.tvnumber.setText("")
                binding.tvnumber.hint = ""
                tinhtoan = ""
                pheptinh = "+"
                checkDot = false
                checkbang = false
            }
        }

        binding.btntru.setOnClickListener {
            if(binding.tvnumber.text.equals("")){
                so1 = 0F
                binding.tvhour.setText("0 - ")
            }else{
                so1 = binding.tvnumber.text.toString().toFloat()
                binding.tvsum.setText(binding.tvnumber.text.toString() + "-")
                binding.tvnumber.setText("")
                binding.tvnumber.hint = ""
                tinhtoan = ""
                pheptinh = "-"
                checkDot = false
                checkbang = false
            }
        }

        binding.btnnhan.setOnClickListener {
            if(binding.tvnumber.text.equals("")){
                so1 = 0F
                binding.tvhour.setText("0 x ")
            }else{
                so1 = binding.tvnumber.text.toString().toFloat()
                binding.tvsum.setText(binding.tvnumber.text.toString() + "x")
                binding.tvnumber.setText("")
                binding.tvnumber.hint = ""
                tinhtoan = ""
                pheptinh = "*"
                checkDot = false
                checkbang = false
            }
        }

        binding.btnchia.setOnClickListener {
            if(binding.tvnumber.text.equals("")){
                so1 = 0F
                binding.tvhour.setText("0 / ")
            }else{
                so1 = binding.tvnumber.text.toString().toFloat()
                binding.tvsum.setText(binding.tvnumber.text.toString() + "/")
                binding.tvnumber.setText("")
                binding.tvnumber.hint = ""
                tinhtoan = ""
                pheptinh = "/"
                checkDot = false
                checkbang = false
            }
        }

        binding.btnbang.setOnClickListener {
            if(!checkbang){
                checkDot = false
                tinhtoan = binding.tvnumber.text.toString()
                so2 = binding.tvnumber.text.toString().toFloat()
                binding.tvsum.setText(binding.tvsum.text.toString() + so2.toString())
                if(pheptinh == "+")
                    kq = so1 + so2
                else if(pheptinh == "-")
                    kq = so1 - so2
                else if(pheptinh == "*")
                    kq = so1*so2
                else
                    kq = so1/so2
                checkbang = true

                binding.tvnumber.setText(kq.toString() + "")
            }
        }

        binding.btndelete.setOnClickListener {
            if(!checkbang){
                var text = binding.tvnumber.text.toString()
                if(!text.isEmpty()){
                    text = text.substring(0,text.length - 1)
                    binding.tvnumber.setText(text)
                }
            }
        }
    }

    fun btnShowCanlender(view: View) {
        binding.calculation.visibility = View.GONE
        val calender = Calendar.getInstance()
        val year = calender.get(Calendar.YEAR)
        val month = calender.get(Calendar.MONTH) + 1
        val day = calender.get(Calendar.DAY_OF_MONTH)

        val datePickerDialog = DatePickerDialog(this,DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvdate.text = String.format("%d/%d/%d", dayOfMonth, month + 1, year)
        }, year, month, day)
        datePickerDialog.show()
    }

    fun btnShowOClock(view: View) {
        binding.calculation.visibility = View.GONE
        val calender = Calendar.getInstance()
        val hour = calender.get(Calendar.HOUR)
        val minute = calender.get(Calendar.MINUTE)

        binding.tvhour.text = String.format("%d:%d", hour, minute)

        TimePickerDialog(
            this@InsertActivity,
            TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
                binding.tvhour.text= String.format(" %d:%d", hourOfDay, minute).toString()
            }, hour, minute, true
        ).show()

        /*val timePickerDialog = TimePickerDialog(this, TimePickerDialog.OnTimeSetListener { view, hourOfDay, minute ->
            binding.tvhour.text= String.format(" %d:%d", hourOfDay, minute).toString()
        }, hour, minute, true)
        timePickerDialog.show()*/
    }

    fun showList(view: View) {
        binding.layout.visibility = View.VISIBLE
        binding.calculation.visibility = View.GONE
        binding.viewDimBackground.visibility = View.VISIBLE
    }

    fun showManager(view: View) {
        val categoryFragment = CategoryFragment()
        supportFragmentManager.beginTransaction()
            .add(R.id.fragment_container, categoryFragment)
            .commit()
    }
}



