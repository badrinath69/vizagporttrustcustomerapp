package com.example.selltez.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import com.example.selltez.R
import com.example.selltez.api.RetrofitClient
import com.example.selltez.model.Passwordresponse
import com.example.selltez.utils.hideKeyboard
import com.example.selltez.utils.snackbar
import kotlinx.android.synthetic.main.activity_change_password.*
import kotlinx.android.synthetic.main.activity_create_new_password.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class CreateNewPassword : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_new_password)
        bt_back_4.setOnClickListener {
            val i = Intent(this, Login_Acti::class.java)
            startActivity(i)
        }
        val numberr = intent.getStringExtra("mobilenum")

        Toast.makeText(applicationContext,"$numberr",Toast.LENGTH_SHORT).show()

        confirmpwdbutton.setOnClickListener {

            if (newpwd2.text.toString().trim().isEmpty()) {
                newpwd2.error = "Enter new password"
                newpwd2.requestFocus()
                return@setOnClickListener
            }

            if (renewpwd2.text.toString().trim().isEmpty()) {
                renewpwd2.error = "Enter new password"
                renewpwd2.requestFocus()
                return@setOnClickListener
            }

            if (newpwd2.text.toString().trim() != renewpwd2.text.toString().trim()) {

                renewpwd2.error = "password doesn't matches"
                renewpwd2.requestFocus()
                return@setOnClickListener
            }

            val newpass2: String = renewpwd2.text.toString().trim()




            RetrofitClient.instance.forgotPassword("$numberr", "$newpass2")
                .enqueue(object : Callback<Passwordresponse>{
                    override fun onResponse(
                        call: Call<Passwordresponse>,
                        response: Response<Passwordresponse>
                    ) {
                        if (response.isSuccessful){
                            newpwd2.text?.clear()
                            renewpwd2.text?.clear()
                            Toast.makeText(applicationContext,"Password changed Successfully",Toast.LENGTH_SHORT).show()
                            val i = Intent(this@CreateNewPassword,Login_Acti::class.java)
                            startActivity(i)
                            finish()
                        }
                    }

                    override fun onFailure(call: Call<Passwordresponse>, t: Throwable) {
                        ll_newpwd.snackbar("Please check the internet")
                        renewpwd2.hideKeyboard()
                    }

                })

        }

    }
}