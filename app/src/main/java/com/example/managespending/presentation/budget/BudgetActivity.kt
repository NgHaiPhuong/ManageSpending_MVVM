package com.example.managespending.presentation.budget

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.EditText
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.managespending.R
import com.example.managespending.base.BaseActivity
import com.example.managespending.databinding.ActivityBudgetBinding
import com.example.managespending.presentation.activity.MainActivity

class BudgetActivity : BaseActivity() {
    private lateinit var binding : ActivityBudgetBinding
    private lateinit var viewModel: BudgetViewModel
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_budget)

        viewModel = ViewModelProvider(this).get(BudgetViewModel::class.java)
        viewModel.navigateToMain.observe(this, Observer { navigate ->
            if(navigate){
                val intent = Intent(this, MainActivity::class.java)
                startActivity(intent)
                finish()
            }
        })
        viewModel.showDialogInfor.observe(this, Observer {
            customAlertDialog()
        })
        viewModel.save.observe(this, Observer {
            val money = binding.tvmoney.text.toString()
            val limit = binding.tvlimit.text.toString()
            Log.d("cost", "budget: $money")
            Log.d("cost", limit)
            val bundle = Bundle()
            bundle.apply {
                putString("money", money)
                putString("limit", limit)
            }
            val intent = Intent(this, MainActivity::class.java).apply {
                putExtras(bundle)
            }
            startActivity(intent)
        })
        binding.budgetViewModel = viewModel
    }
    @SuppressLint("InflateParams")
    private fun customAlertDialog() {
        val builder : AlertDialog.Builder = AlertDialog.Builder(this)
        val inflater = this.layoutInflater
        val logicView : View = inflater.inflate(R.layout.custom_alert_dialog, null)
        val totalmoney = logicView.findViewById<EditText>(R.id.etmoney).text
        val totallitmit = logicView.findViewById<EditText>(R.id.etlimit).text

        builder.setView(logicView)
        builder.setPositiveButton("OK"){ dialog, which ->
            Toast.makeText(this,
                "Insert Successful",
                Toast.LENGTH_LONG).show()
            binding.tvmoney.text = totalmoney.toString()
            binding.tvlimit.text = totallitmit.toString()
        }
        builder.setNegativeButton("Cancel"){ dialog, which ->
            Toast.makeText(this,
                "Cancel Successful",
                Toast.LENGTH_LONG).show()
            dialog.dismiss()
        }
        val alertDialog : AlertDialog = builder.create()
        alertDialog.show()
        alertDialog.getButton(AlertDialog.BUTTON_POSITIVE).setTextColor(ContextCompat.getColor(this@BudgetActivity,R.color.black))
        alertDialog.getButton(AlertDialog.BUTTON_NEGATIVE).setTextColor(ContextCompat.getColor(this@BudgetActivity,R.color.black))
    }
}