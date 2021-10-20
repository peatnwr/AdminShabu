package com.example.adminshabu.module

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.adminshabu.activites.AdminEditStockFoodActivity
import com.example.adminshabu.databinding.AdminStockFoodItemLayoutBinding
import com.example.adminshabu.dataclass.Food

class AdminStockFoodAdapter(val stockFoodList : ArrayList<Food>, val context: Context) :RecyclerView.Adapter<AdminStockFoodAdapter.ViewHolder>() {
    inner class ViewHolder(view: View, val binding: AdminStockFoodItemLayoutBinding) :RecyclerView.ViewHolder(view){
        init {
            binding.btnEdit.setOnClickListener {
                val item = stockFoodList[adapterPosition]
                val contextView : Context = view.context
                val editStockFood = Intent(contextView, AdminEditStockFoodActivity::class.java)
                editStockFood.putExtra("foodcategory_id", item.foodcategory_id.toString())
                editStockFood.putExtra("foodId", item.food_id.toString())
                editStockFood.putExtra("foodName", item.food_name)
                editStockFood.putExtra("foodAmount", item.food_amount.toString())
                editStockFood.putExtra("foodImage", item.food_img)
                contextView.startActivity(editStockFood)
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val binding = AdminStockFoodItemLayoutBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ViewHolder(binding.root, binding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val binding = holder.binding

        binding.nameFood.text = stockFoodList!![position].food_name
        binding.allAmount.text = "คงเหลือ : " + stockFoodList!![position].food_amount
    }

    override fun getItemCount(): Int {
        return stockFoodList!!.size
    }
}