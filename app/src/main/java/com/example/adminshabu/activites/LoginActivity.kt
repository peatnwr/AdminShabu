package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityLoginBinding
import com.example.adminshabu.dataclass.EmployeeParcelable
import com.example.adminshabu.dataclass.Employee
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)
    }

    fun checkLogin(v: View){
        val api: clientAPI = Retrofit.Builder()
            .baseUrl("http://10.0.2.2:3000/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(clientAPI::class.java)
        api.loginEmp(
            binding.edtUsername.text.toString(),
            binding.edtPassword.text.toString()
        ).enqueue(object : Callback<Employee> {
            override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                if(response.isSuccessful){
                    val emp_id = response.body()?.emp_id.toString().toInt()
                    val emp_username = response.body()?.emp_username.toString()
                    val emp_name = response.body()?.emp_name.toString()
                    val emp_tel = response.body()?.emp_tel.toString()
                    val mainAdmin = Intent(this@LoginActivity, MainActivity::class.java)
                    mainAdmin.putExtra("empInfo", EmployeeParcelable(emp_id, emp_username, emp_name, emp_tel)
                    )
                    startActivity(mainAdmin)
                }else{
                    Toast.makeText(applicationContext, "Username or password incorrect.", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<Employee>, t: Throwable) {
                Log.d("Failure Connect : ", "Failure Alert")
                return t.printStackTrace()
            }
        })
    }
}