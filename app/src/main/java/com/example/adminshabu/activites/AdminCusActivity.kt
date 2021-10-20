    package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminCusBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Customer
import retrofit2.*
import retrofit2.converter.gson.GsonConverterFactory

    class AdminCusActivity : AppCompatActivity() {
    private lateinit var binding: ActivityAdminCusBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminCusBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo : EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.btnCode.setOnClickListener {
            binding.edtCode.setText(randomCode(6))
        }

        binding.btnBack.setOnClickListener {
            val mainActivity = Intent(this@AdminCusActivity, MainActivity::class.java)
            mainActivity.putExtra("empInfo", empInfo)
            startActivity(mainActivity)
        }

        binding.btnConfirm.setOnClickListener {
            val api: clientAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create()
            api.createTable(
                binding.edtTable.text.toString().toInt(),
                binding.edtCusamount.text.toString().toInt(),
                binding.edtCode.text.toString()
            ).enqueue(object : Callback<Customer> {
                override fun onResponse(call: Call<Customer>, response: Response<Customer>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Insert Table Successful", Toast.LENGTH_SHORT).show()
                        val mainActivity = Intent(this@AdminCusActivity, MainActivity::class.java)
                        mainActivity.putExtra("empInfo", empInfo)
                        startActivity(mainActivity)
                    }else{
                        Toast.makeText(applicationContext, "This table already have", Toast.LENGTH_SHORT).show()
                    }
                }

                override fun onFailure(call: Call<Customer>, t: Throwable) {
                    Log.d("Failure Connect : ", "Failure Alert")
                    return t.printStackTrace()
                }
            })
        }
    }

    fun randomCode(length: Int) :String {
        val charset = ('a'..'z') + ('A'..'Z') + ('0'..'9')
        return (1..length)
            .map { charset.random() }
            .joinToString("")
    }
}