package com.example.adminshabu.module

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshabu.activites.AdminStockFoodActivity
import com.example.adminshabu.databinding.AdminStockCategoryItemLayoutBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.FoodCategory

class AdminStockCategoryAdapter(val categoryFoodList: ArrayList<FoodCategory>, val empInfo: EmployeeParcelable?, val context: Context) :RecyclerView.Adapter<AdminStockCategoryAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: AdminStockCategoryItemLayoutBinding) :RecyclerView.ViewHolder(view){
        init {
            binding.btnStock.setOnClickListener {
                val item = categoryFoodList[adapterPosition]
                val contextView : Context = view.context
                val adminStockFood = Intent(contextView, AdminStockFoodActivity::class.java)
                adminStockFood.putExtra("empInfo", empInfo)
                adminStockFood.putExtra("foodcategory_id", item.foodcategory_id.toString())
                contextView.startActivity(adminStockFood)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminStockCategoryItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.showCategory.text = categoryFoodList!![position].foodcategory_name
    }

    override fun getItemCount(): Int {
        return categoryFoodList!!.size
    }
}