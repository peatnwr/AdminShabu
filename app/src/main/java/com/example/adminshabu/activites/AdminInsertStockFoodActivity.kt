package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminInsertStockFoodBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Food
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminInsertStockFoodActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminInsertStockFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminInsertStockFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val foodCategoryId = intent.getStringExtra("foodcategory_id")

        binding.btnBack.setOnClickListener {
            val stockFood = Intent(this@AdminInsertStockFoodActivity, AdminStockFoodActivity::class.java)
            stockFood.putExtra("empInfo", empInfo)
            stockFood.putExtra("foodcategory_id", foodCategoryId)
            startActivity(stockFood)
        }

        binding.btnInsert.setOnClickListener {
            val api: clientAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clientAPI::class.java)
            api.insertFood(
                foodCategoryId.toString().toInt(),
                binding.edtFood.text.toString(),
                binding.edtImg.text.toString(),
                binding.edtAmountfood.text.toString().toInt()
            ).enqueue(object : Callback<Food> {
                override fun onResponse(call: Call<Food>, response: Response<Food>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Insert Food Successful", Toast.LENGTH_SHORT).show()
                        val stockFood = Intent(this@AdminInsertStockFoodActivity, AdminStockFoodActivity::class.java)
                        stockFood.putExtra("empInfo", empInfo)
                        stockFood.putExtra("foodcategory_id", foodCategoryId)
                        startActivity(stockFood)
                    }
                }

                override fun onFailure(call: Call<Food>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
        }
    }
}