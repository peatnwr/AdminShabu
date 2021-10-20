package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminStockFoodBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Food
import com.example.adminshabu.module.AdminStockFoodAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminStockFoodActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminStockFoodBinding
    var stockFoodList = arrayListOf<Food>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminStockFoodBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val foodCategoryId = intent.getStringExtra("foodcategory_id")

        binding.recyclerView.adapter = AdminStockFoodAdapter(stockFoodList, empInfo, applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        binding.btnBack.setOnClickListener {
            val categoryStock = Intent(this@AdminStockFoodActivity, AdminStockCategoryActivity::class.java)
            categoryStock.putExtra("empInfo", empInfo)
            startActivity(categoryStock)
        }

        binding.btnAdd.setOnClickListener {
            val insertFood = Intent(this@AdminStockFoodActivity, AdminInsertStockFoodActivity::class.java)
            insertFood.putExtra("empInfo", empInfo)
            insertFood.putExtra("foodcategory_id", foodCategoryId)
            startActivity(insertFood)
        }
    }

    override fun onResume() {
        super.onResume()
        callFood()
    }

    fun callFood(){
        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val foodCategoryId = intent.getStringExtra("foodcategory_id")
        stockFoodList.clear()
        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)
        api.retrieveFood(
            foodCategoryId.toString().toInt()
        ).enqueue(object : Callback<List<Food>> {
            override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                if(response.isSuccessful){
                    response.body()?.forEach {
                        stockFoodList.add(Food(it.food_id, it.food_name, it.food_img, it.food_amount, it.foodcategory_id))
                    }
                    binding.recyclerView.adapter = AdminStockFoodAdapter(stockFoodList, empInfo, applicationContext)
                }
            }

            override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                return t.printStackTrace()
            }
        })
    }

    fun clickSearch(v: View){
        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val foodCategoryId = intent.getStringExtra("foodcategory_id")
        stockFoodList.clear()
        if(binding.edtSearch.text!!.isEmpty()){
            callFood()
        } else {
            val api: clientAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clientAPI::class.java)
            api.searchFood(
                binding.edtSearch.text.toString(),
                foodCategoryId.toString().toInt()
            ).enqueue(object : Callback<List<Food>> {
                override fun onResponse(call: Call<List<Food>>, response: Response<List<Food>>) {
                    if(response.isSuccessful){
                        response.body()?.forEach {
                            stockFoodList.add(Food(it.food_id, it.food_name, it.food_img, it.food_amount, it.foodcategory_id))
                        }
                        binding.recyclerView.adapter = AdminStockFoodAdapter(stockFoodList, empInfo, applicationContext)
                    }
                }

                override fun onFailure(call: Call<List<Food>>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
        }
    }
}