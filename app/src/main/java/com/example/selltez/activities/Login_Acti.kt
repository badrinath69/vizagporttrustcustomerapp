package com.example.selltez.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.widget.AppCompatButton
import com.example.selltez.R
import com.example.selltez.api.RetrofitClient
import com.example.selltez.model.LoginResponse
import com.example.selltez.storage.SharedPrefManager
import com.google.android.material.textfield.TextInputEditText
import kotlinx.android.synthetic.main.activity_login.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class Login_Acti : AppCompatActivity() {
    val TAG = "Login_Activity"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)


        val editTextNumber = findViewById<TextInputEditText>(R.id.editTextNumber)
        val editTextPassword = findViewById<TextInputEditText>(R.id.editTextPassword)
        val buttonLogin = findViewById<AppCompatButton>(R.id.buttonLogin)
        textView2.setOnClickListener {
            val i = Intent(this, ForgotPwdActivity::class.java)
            startActivity(i)
        }
        buttonLogin.setOnClickListener {
            val mobile = editTextNumber.text.toString().trim()
            val password = editTextPassword.text.toString().trim()

            if(mobile.isEmpty()){
                editTextNumber.error = "Email required"
                editTextNumber.requestFocus()
                return@setOnClickListener
            }

            if(password.isEmpty()){
                editTextPassword.error = "Email required"
                editTextPassword.requestFocus()
                return@setOnClickListener
            }

            RetrofitClient.instance.userLogin(mobile, password)
                .enqueue(object: Callback<LoginResponse> {
                    override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                        Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                    }

                    override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {

                        if(response.body()?.message.equals("success")){

                            // !response.body()?.userdata?.status
                                // !response.body()?.error!!
                                    //

                            Log.d(TAG, "onResponse: Successful login")
                            SharedPrefManager.getInstance(applicationContext).saveUser(response.body()?.userdata!!)

                            val intent = Intent(this@Login_Acti, MainActivity::class.java)
                            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

                            startActivity(intent)


                        }else{
                            Toast.makeText(applicationContext, "Invalid username/password", Toast.LENGTH_LONG).show()
                        }

                    }
                })














//            val i = Intent(this,MainActivity::class.java)
//            startActivity(i)
//            finish()
        }




        val signup_bt: TextView = findViewById(R.id.signup_bt)
        signup_bt.setOnClickListener {
            val i = Intent(this, Signup_Acti::class.java)
            startActivity(i)
            finish()
        }
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }
}