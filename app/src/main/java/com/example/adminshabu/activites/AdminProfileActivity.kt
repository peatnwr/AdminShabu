package com.example.adminshabu.activites

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.bumptech.glide.Glide
import com.example.adminshabu.R
import com.example.adminshabu.databinding.ActivityAdminProfileBinding
import com.example.adminshabu.dataclass.EmployeeParcelable

class AdminProfileActivity : AppCompatActivity() {
    private lateinit var binding : ActivityAdminProfileBinding
    var typeMessage: String = ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityAdminProfileBinding.inflate(layoutInflater)
        setContentView(binding.root)

        var data = intent.extras
        var empInfo: EmployeeParcelable? = data?.getParcelable("empInfo")

        binding.btnBack.setOnClickListener {
            val mainPage = Intent(this@AdminProfileActivity, MainActivity::class.java)
            mainPage.putExtra("empInfo", empInfo)
            startActivity(mainPage)
        }

        if(empInfo?.emp_type.toString().equals("1")){
            typeMessage = "พนักงานปรุงอาหาร"
        }else if(empInfo?.emp_type.toString().equals("2")){
            typeMessage = "พนักงานชำระเงิน"
        }

        binding.showName.text = "ชื่อ : " + empInfo?.emp_name
        binding.showUsername.text = "ชื่อผู้ใช้งาน : " + empInfo?.emp_username
        binding.showTel.text = "เบอร์โทร : " + empInfo?.emp_tel
        binding.showType.text = "ตำแหน่ง : " + typeMessage
        Glide.with(applicationContext).load(empInfo?.emp_img.toString()).into(binding.imageProfile)

        binding.btnEdit.setOnClickListener {
            val editProfile = Intent(this@AdminProfileActivity, AdminEditProfileActivity::class.java)
            editProfile.putExtra("empInfo", empInfo)
            startActivity(editProfile)
        }

        binding.btnLogout.setOnClickListener {
            val LoginPage = Intent(this, LoginActivity::class.java)
            LoginPage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
            startActivity(LoginPage)
            finish()
        }
    }
}