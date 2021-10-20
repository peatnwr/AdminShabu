package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminStockCategoryBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.FoodCategory
import com.example.adminshabu.module.AdminStockCategoryAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminStockCategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminStockCategoryBinding
    var categoryFoodList = arrayListOf<FoodCategory>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminStockCategoryBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.recyclerView.adapter = AdminStockCategoryAdapter(categoryFoodList, applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        binding.btnBack.setOnClickListener {
            val mainActivity = Intent(this@AdminStockCategoryActivity, MainActivity::class.java)
            mainActivity.putExtra("empInfo", empInfo)
            startActivity(mainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        callCategoryFood()
    }

    fun callCategoryFood(){
        categoryFoodList.clear()
        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)
        api.retrieveCategory().enqueue(object : Callback<List<FoodCategory>> {
            override fun onResponse(
                call: Call<List<FoodCategory>>,
                response: Response<List<FoodCategory>>
            ) {
                if(response.isSuccessful){
                    response.body()?.forEach {
                        categoryFoodList.add(FoodCategory(it.foodcategory_id, it.foodcategory_name, it.foodcategory_img))
                    }
                    binding.recyclerView.adapter = AdminStockCategoryAdapter(categoryFoodList, applicationContext)
                }
            }

            override fun onFailure(call: Call<List<FoodCategory>>, t: Throwable) {
                return t.printStackTrace()
            }
        })
    }
}