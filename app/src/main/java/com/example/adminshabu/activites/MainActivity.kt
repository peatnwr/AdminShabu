package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.adminshabu.R
import com.example.adminshabu.databinding.ActivityMainBinding
import com.example.adminshabu.dataclass.EmployeeParcelable

class MainActivity : AppCompatActivity() {
    private lateinit var binding: ActivityMainBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.btnCus.setOnClickListener {
            val customerPage = Intent(this@MainActivity, AdminCusActivity::class.java)
            customerPage.putExtra("empInfo", empInfo)
            startActivity(customerPage)
        }

        binding.btnStatusfood.setOnClickListener {
            val statusFood = Intent(this@MainActivity, AdminStatusOrderActivity::class.java)
            statusFood.putExtra("empInfo", empInfo)
            startActivity(statusFood)
        }

        binding.btnStatuspayment.setOnClickListener {
            val statusPayment = Intent(this@MainActivity, AdminStatusPaymentActivity::class.java)
            statusPayment.putExtra("empInfo", empInfo)
            startActivity(statusPayment)
        }

        binding.btnStock.setOnClickListener {
            val stockPage = Intent(this@MainActivity, AdminStockCategoryActivity::class.java)
            stockPage.putExtra("empInfo", empInfo)
            startActivity(stockPage)
        }
    }
}