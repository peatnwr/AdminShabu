package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.adminshabu.R
import com.example.adminshabu.api.clientAPI
import com.example.adminshabu.databinding.ActivityAdminEditProfileBinding
import com.example.adminshabu.dataclass.Employee
import com.example.adminshabu.dataclass.EmployeeParcelable
import retrofit2.*
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class AdminEditProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminEditProfileBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminEditProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        Glide.with(applicationContext).load(empInfo?.emp_img.toString()).into(binding.imageProfile)
        binding.edtName.setText(empInfo?.emp_name.toString())
        binding.edtUsername.setText(empInfo?.emp_username.toString())
        binding.edtUsername.isEnabled = false
        binding.edtTel.setText(empInfo?.emp_tel.toString())
        binding.edtImg.setText(empInfo?.emp_img.toString())

        binding.btnBack.setOnClickListener {
            val adminProfile = Intent(this@AdminEditProfileActivity, AdminProfileActivity::class.java)
            adminProfile.putExtra("empInfo", empInfo)
            startActivity(adminProfile)
        }

        binding.btnEdit.setOnClickListener {
            val api: clientAPI = Retrofit.Builder()
                .baseUrl("http://10.0.2.2:3000/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
                .create(clientAPI::class.java)
            api.editProfile(
                empInfo?.emp_id.toString().toInt(),
                binding.edtName.text.toString(),
                binding.edtUsername.text.toString(),
                binding.edtTel.text.toString(),
                binding.edtImg.text.toString()
            ).enqueue(object : Callback<Employee> {
                override fun onResponse(call: Call<Employee>, response: Response<Employee>) {
                    if(response.isSuccessful){
                        Toast.makeText(applicationContext, "Edit Profile Successful", Toast.LENGTH_SHORT).show()
                        val LoginPage = Intent(this@AdminEditProfileActivity, LoginActivity::class.java)
                        LoginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
                        startActivity(LoginPage)
                        finish()
                    }
                }

                override fun onFailure(call: Call<Employee>, t: Throwable) {
                    return t.printStackTrace()
                }
            })
        }
    }
}