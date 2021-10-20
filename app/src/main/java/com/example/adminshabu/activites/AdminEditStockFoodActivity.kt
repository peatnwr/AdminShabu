package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminEditStockFoodBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Food
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminEditStockFoodActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminEditStockFoodBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditStockFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val foodCategoryId = intent.getStringExtra("foodcategory_id")
        val foodId = intent.getStringExtra("foodId")
        val foodName = intent.getStringExtra("foodName")
        val foodAmount = intent.getStringExtra("foodAmount")
        val foodImage = intent.getStringExtra("foodImage")

        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)

        binding.edtFood.setText(foodName)
        binding.edtAmount.setText(foodAmount)
        binding.edtImg.setText(foodImage)

        binding.btnBack.setOnClickListener {
            val stockFood = Intent(this@AdminEditStockFoodActivity, AdminStockFoodActivity::class.java)
            stockFood.putExtra("empInfo", empInfo)
            stockFood.putExtra("foodcategory_id", foodCategoryId)
            startActivity(stockFood)
        }

        binding.btnEdit.setOnClickListener {
            api.editFood(
                foodId.toString().toInt(),
                binding.edtFood.text.toString(),
                binding.edtAmount.text.toString().toInt(),
                binding.edtImg.text.toString()
            ).enqueue(object : Callback<Food> {
                override fun onResponse(call: Call<Food>, response: Response<Food>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Edit Food Successful", Toast.LENGTH_SHORT).show()
                        val stockFood = Intent(this@AdminEditStockFoodActivity, AdminStockFoodActivity::class.java)
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

        binding.btnDelete.setOnClickListener {
            val deleteAlert = AlertDialog.Builder(this)
            deleteAlert.setMessage("ต้องการลบรายการอาหารนี้หรือไม่?")
            deleteAlert.setPositiveButton("No") { dialog, which ->}
            deleteAlert.setNegativeButton("Yes") { _,_ ->
                api.deleteFood(
                    foodId.toString().toInt()
                ).enqueue(object : Callback<Food> {
                    override fun onResponse(call: Call<Food>, response: Response<Food>) {
                        if(response.isSuccessful){
                            Toast.makeText(applicationContext, "Delete Food Successful", Toast.LENGTH_SHORT).show()
                            val stockFood = Intent(this@AdminEditStockFoodActivity, AdminStockFoodActivity::class.java)
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
            deleteAlert.show()
        }
    }
}