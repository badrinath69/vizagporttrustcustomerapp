package com.example.selltez.activities

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Binder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils.isEmpty
import android.widget.ImageButton
import android.widget.Toast
import com.example.selltez.R
import com.example.selltez.api.RetrofitClient
import com.example.selltez.databinding.ActivityChangePasswordBinding
import com.example.selltez.model.AccountResponse
import com.example.selltez.model.ChangePasswordResponse
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.snackbar
import kotlinx.android.synthetic.main.activity_change_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.util.regex.Matcher

class ChangePasswordActivity : AppCompatActivity() {
    private var pass: String? = null
    lateinit var preferences: SharedPreferences
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_change_password)
        val bt_back_1 = findViewById<ImageButton>(R.id.bt_back_1)
        bt_back_1.setOnClickListener {
            val i = Intent(this, MainActivity::class.java)
            startActivity(i)
            finish()
        }
        preferences = getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val empid = preferences.getString("empid", null)


//        val sharedPreferences = getSharedPreferences("my_name", Context.MODE_PRIVATE)
//        val pass = sharedPreferences.getString("password2", null)


        RetrofitClient.instance.accountDetails("$empid")
            .enqueue(object: Callback<AccountResponse>
            {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
                ) {
                    val det = response.body()?.Details
                    pass =  det?.get(0)?.password.toString()
                }

                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                    ntf4.snackbar("Please check the internet connection")
                }

            })


        changepwdbutton.setOnClickListener {

          //  Toast.makeText(applicationContext,"$pass",Toast.LENGTH_SHORT).show()
            if (oldpassword.text.toString().trim().isEmpty()) {
                oldpassword.error = "Enter your old password"
                oldpassword.requestFocus()
                return@setOnClickListener
            }

            if (oldpassword.text.toString().trim() != pass){
                oldpassword.error = "old password doesn't matches"
                oldpassword.requestFocus()
                return@setOnClickListener
            }

            if (newpwd.text.toString().trim().isEmpty()) {
                newpwd.error = "Enter new password"
                newpwd.requestFocus()
                return@setOnClickListener
            }

            if (renewpwd.text.toString().trim().isEmpty()) {
                renewpwd.error = "Enter new password"
                renewpwd.requestFocus()
                return@setOnClickListener
            }

            if (newpwd.text.toString().trim() != renewpwd.text.toString().trim()){

                renewpwd.error = "password doesn't matches"
                renewpwd.requestFocus()
                return@setOnClickListener
            }
            val newpass : String = renewpwd.text.toString().trim()


            RetrofitClient.instance.changePassword("$empid","$newpass")
                .enqueue(object: Callback<ChangePasswordResponse>{
                    override fun onResponse(
                        call: Call<ChangePasswordResponse>,
                        response: Response<ChangePasswordResponse>
                    ) {
                        recordadded.text = "Record added successfully"
                        if (response.isSuccessful){
                            oldpassword.text?.clear()
                            newpwd.text?.clear()
                            renewpwd.text?.clear()
                            recordadded.text = ""
                            Toast.makeText(applicationContext,"Password changed Successfully",Toast.LENGTH_SHORT).show()


                            val i = Intent(this@ChangePasswordActivity,MainActivity::class.java)
                            startActivity(i)
                            finish()
                        }

                    }

                    override fun onFailure(call: Call<ChangePasswordResponse>, t: Throwable) {
                        Toast.makeText(applicationContext,t.message,Toast.LENGTH_SHORT).show()
                    }

                } )
        }
    }


//    }
//
//    private fun isPasswordCorrect(text: String): Boolean {
//        val pass = preferences.getString("password",null)!!
//        var m: Boolean = pass.matches(text.toString())
//        return true
//
//
//    }
}