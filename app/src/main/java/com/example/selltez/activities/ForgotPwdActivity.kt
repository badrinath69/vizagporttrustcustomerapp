package com.example.selltez.activities

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.widget.EditText
import android.widget.Toast
import androidx.core.view.isVisible
import com.example.selltez.R
import com.example.selltez.storage.Communicator2
import com.example.selltez.utils.hideKeyboard
import com.google.android.material.textfield.TextInputEditText
import com.mukesh.OnOtpCompletionListener
import kotlinx.android.synthetic.main.activity_forgot_pwd.*
import kotlinx.android.synthetic.main.activity_signup.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class ForgotPwdActivity : AppCompatActivity() {
    private var otp: Int? = null
    private var code: Int? = null
     val TAG = "ForgorPwdActivity"
    var ms : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_forgot_pwd)

        var number = findViewById<TextInputEditText>(R.id.getnumber)
        ms = number.text.toString()

        bt_back_3.setOnClickListener {
            val i = Intent(this,Login_Acti::class.java)
            startActivity(i)
        }

        sendbt.isEnabled = false
        sendbt.setOnClickListener {

            if(number.text.toString().trim().isEmpty()){
                number.error = "Mobile Number Required"
                number.requestFocus()
                return@setOnClickListener

            }

            if (mobileValiate(number.text.toString())){


                number.hideKeyboard()
                otpSend(ms.toString())
                sendbt.isVisible =  false

                verifyfbnbt.isVisible = true
                number.setFocusable(false);
                number.setClickable(true);



            }else{
                number.error = "check the number you have entered"
                number.requestFocus()
                return@setOnClickListener
            }


        }

        verifyfbnbt.setOnClickListener {
            Log.d(TAG, "onViewClicked: $otp")
            if (code == otp) {

                Toast.makeText(applicationContext, "OTP Verified.", Toast.LENGTH_SHORT).show()

               // Toast.makeText(applicationContext, "${number.text}", Toast.LENGTH_SHORT).show()
                val i = Intent(applicationContext,CreateNewPassword::class.java)
                i.putExtra("mobilenum","${number.text}")
                startActivity(i)
                finish()



            } else {
                Toast.makeText(applicationContext, "OTP Invalid.", Toast.LENGTH_SHORT).show()
            }
        }

        getnumber.addTextChangedListener(textWatcher)





    }

    private fun mobileValiate(text: String): Boolean {
        var p: Pattern = Pattern.compile("[6-9][0-9]{9}")
        var m: Matcher = p.matcher(text)
        return m.matches()

    }




    private fun otpSend(mobile: String) {
        // code = Random().nextInt(9000) + 1000
        code = 1234
        Log.d(TAG, "otpSend: $code")
        val message =
            " Your registration OTP is $code"
//        val urlString =
//            "http://app.smsmoon.com/submitsms.jsp?user=PIVOTAL&key=7d9a0596c8XX&mobile=$mobile&message=$message&senderid=PVOTAL&accusage=1"
//        val webView = WebView(applicationContext)
//        webView.loadUrl(urlString)
        Toast.makeText(
            applicationContext,
            "OTP sent to your registered mobile no.${mobile}",
            Toast.LENGTH_SHORT
        ).show()
        textInputLayout_5_6.setOtpCompletionListener(OnOtpCompletionListener { otprecive ->
            otp = otprecive.toInt()
            Log.d(TAG, "otpnum: $otp")
        })
    }

    private val textWatcher =(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            val n2  = getnumber.text.toString()


                if (!n2.isEmpty() && mobileValiate(n2)){
                    sendbt.isEnabled = true
                }else{
                    sendbt.isEnabled = false
                }
            }


        override fun afterTextChanged(s: Editable?) {

        }
    })




}