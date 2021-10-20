package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
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

        binding.btnAdminProfile.setOnClickListener {
            val adminProfile = Intent(this@MainActivity, AdminProfileActivity::class.java)
            adminProfile.putExtra("empInfo", empInfo)
            startActivity(adminProfile)
        }

        binding.btnCus.setOnClickListener {
            val customerPage = Intent(this@MainActivity, AdminCusActivity::class.java)
            customerPage.putExtra("empInfo", empInfo)
            startActivity(customerPage)
        }

        binding.btnStatusfood.setOnClickListener {
            if(empInfo?.emp_type.toString().equals("1")){
                val statusFood = Intent(this@MainActivity, AdminStatusOrderActivity::class.java)
                statusFood.putExtra("empInfo", empInfo)
                startActivity(statusFood)
            }else{
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("คุณไม่ใช่พนักงานปรุงอาหาร")
                alertDialog.setPositiveButton("OK") { dialog, which -> }
                alertDialog.show()
            }
        }

        binding.btnStatuspayment.setOnClickListener {
            if(empInfo?.emp_type.toString().equals("2")){
                val statusPayment = Intent(this@MainActivity, AdminStatusPaymentActivity::class.java)
                statusPayment.putExtra("empInfo", empInfo)
                startActivity(statusPayment)
            }else{
                val alertDialog = AlertDialog.Builder(this)
                alertDialog.setMessage("คุณไม่ใช่พนักงานชำระเงิน")
                alertDialog.setPositiveButton("OK") { dialog, which -> }
                alertDialog.show()
            }
        }

        binding.btnStock.setOnClickListener {
            val stockPage = Intent(this@MainActivity, AdminStockCategoryActivity::class.java)
            stockPage.putExtra("empInfo", empInfo)
            startActivity(stockPage)
        }
    }
}