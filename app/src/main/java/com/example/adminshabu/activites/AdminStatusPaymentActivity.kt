package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminStatusPaymentBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Payment
import com.example.adminshabu.module.AdminStatusPaymentAdapter
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminStatusPaymentActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminStatusPaymentBinding
    var paymentStatusList = arrayListOf<Payment>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminStatusPaymentBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.recyclerView.adapter = AdminStatusPaymentAdapter(paymentStatusList, empInfo, applicationContext)
        binding.recyclerView.layoutManager = LinearLayoutManager(applicationContext)

        binding.btnBack.setOnClickListener {
            val mainActivity = Intent(this@AdminStatusPaymentActivity, MainActivity::class.java)
            mainActivity.putExtra("empInfo", empInfo)
            startActivity(mainActivity)
        }
    }

    override fun onResume() {
        super.onResume()
        callPaymentStatus()
    }

    fun callPaymentStatus(){
        paymentStatusList.clear()
        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")
        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)
        api.retrievePaymentStatus().enqueue(object : Callback<List<Payment>> {
            override fun onResponse(call: Call<List<Payment>>, response: Response<List<Payment>>) {
                if(response.isSuccessful){
                    response.body()?.forEach {
                        paymentStatusList.add(Payment(it.cus_table, it.cus_amount, it.order_id, it.order_price, it.cus_code))
                    }
                    binding.recyclerView.adapter = AdminStatusPaymentAdapter(paymentStatusList, empInfo, applicationContext)
                }
            }

            override fun onFailure(call: Call<List<Payment>>, t: Throwable) {
                return t.printStackTrace()
            }
        })
    }
}