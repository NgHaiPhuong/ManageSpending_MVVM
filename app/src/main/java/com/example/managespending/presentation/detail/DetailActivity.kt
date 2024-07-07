package com.example.managespending.presentation.detail

import android.app.AlertDialog
import android.app.DatePickerDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.base.BaseActivity
import com.example.managespending.databinding.ActivityDetailBinding
import com.example.managespending.db.database.MyDatabase
import com.example.managespending.db.viewmodel.MyViewModel
import com.example.managespending.db.viewmodel.MyViewModelFactory
import com.example.managespending.model.Transaction
import com.example.managespending.presentation.activity.MainActivity
import com.example.managespending.presentation.home.HomeFragment
import com.example.managespending.util.FormatNumberUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.Calendar

class DetailActivity : BaseActivity() {
    private lateinit var binding : ActivityDetailBinding
    private var transactionId : Int = 0
    private lateinit var myViewModel: MyViewModel
    private var listTransaction : MutableList<Transaction> = ArrayList()
    private lateinit var viewModel: DetailViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_detail)
        viewModel = ViewModelProvider(this).get(DetailViewModel::class.java)
        viewModel.navigateToMain.observe(this, Observer { navigate ->
            if (navigate) {
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
            }
        })
        setupDatabase()
        initData()
        updateTrans()
        deleteTrans()
        binding.detailViewModel = viewModel
    }

    private fun setupDatabase() {
        val dao = MyDatabase.getInstance(application).myDao()
        val factory = MyViewModelFactory(dao)
        myViewModel = ViewModelProvider(this, factory).get(MyViewModel::class.java)
    }

    private fun initData() {
        transactionId = intent.getIntExtra("id", 0)
        binding.transactionId = transactionId

        myViewModel.allTransactionList.observe(this) { transactions ->
            listTransaction.clear()
            listTransaction = transactions.toMutableList()
        }
        binding.listTransaction = listTransaction

        binding.tvname.text = intent.getStringExtra("name")
        val cost =  intent.getFloatExtra("cost", 0f)
        binding.tvcost.text = FormatNumberUtil.format(cost)
        binding.etcategory.setText(intent.getStringExtra("category"))
        binding.tvdate.text = intent.getStringExtra("date")
        binding.etnote.setText(intent.getStringExtra("note"))
        binding.url = intent.getStringExtra("url")
        binding.executePendingBindings()
    }

    private fun updateTrans() {
        viewModel.confirmUpdate.observe(this, Observer { confirm ->
            if(confirm){
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Confirm update transaction")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes") { dialog, which ->
                        listTransaction = viewModel.updateTransaction(transactionId,listTransaction)

                        CoroutineScope(Dispatchers.IO).launch {
                            myViewModel.deleteAllTransaction()
                            myViewModel.addAllTransaction(listTransaction)
                            withContext(Dispatchers.Main) {
                                val homeFragment = HomeFragment()
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, homeFragment)
                                    .commit()
                            }
                        }
                    }
                    .setPositiveButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()

                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.black)
                )
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.red)
                )
            }
        })
    }
    private fun deleteTrans(){
        viewModel.confirmDelete.observe(this, Observer { confirm ->
            if(confirm){
                Log.d("DeleteTransaction", "Deleting transaction with id: $transactionId")
                val alertDialog = AlertDialog.Builder(this)
                    .setTitle("Confirm delete transaction")
                    .setMessage("Are you sure?")
                    .setPositiveButton("Yes") { dialog, which ->
                        listTransaction = viewModel.deleteTransaction(transactionId,listTransaction)

                        CoroutineScope(Dispatchers.IO).launch {
                            myViewModel.deleteAllTransaction()
                            myViewModel.addAllTransaction(listTransaction)
                            withContext(Dispatchers.Main){
                                val homeFragment =  HomeFragment()
                                supportFragmentManager.beginTransaction()
                                    .replace(R.id.fragment_container, homeFragment)
                                    .commit()
                            }
                        }
                    }
                    .setNegativeButton("No") { dialog, which ->
                        dialog.dismiss()
                    }
                    .create()

                alertDialog.show()
                alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.black)
                )
                alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(
                    ContextCompat.getColor(this@DetailActivity, R.color.red)
                )
            }
        })
    }

    fun btnShowCanlender(view: View) {
        val calendar = Calendar.getInstance()
        val day = calendar.get(Calendar.DAY_OF_MONTH)
        val month = calendar.get(Calendar.MONTH) + 1
        val year = calendar.get(Calendar.YEAR)

        val datePickerDialog = DatePickerDialog(this, DatePickerDialog.OnDateSetListener { view, year, month, dayOfMonth ->
            binding.tvdate.text = String.format("%d/%d/%d", dayOfMonth, month + 1, year)
        },  year, month, day)
        datePickerDialog.show()
    }
}