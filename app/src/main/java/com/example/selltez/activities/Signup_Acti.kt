package com.example.selltez.activities

import android.app.Activity
import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.webkit.WebView
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.AppCompatButton
import androidx.core.view.isVisible
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.selltez.R
import com.example.selltez.api.RetrofitClient
import com.example.selltez.model.DefaultResponse
import com.example.selltez.model.UploadRequestBody
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.getFileName
import com.example.selltez.utils.snackbar
import com.google.android.material.textfield.TextInputEditText
import com.mukesh.OnOtpCompletionListener
import com.mukesh.OtpView
import kotlinx.android.synthetic.main.activity_forgot_pwd.*
import kotlinx.android.synthetic.main.activity_signup.*
import kotlinx.android.synthetic.main.dialougebox.*
import kotlinx.android.synthetic.main.fragment_home.*
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream
import java.util.*
import java.util.regex.Matcher
import java.util.regex.Pattern

class Signup_Acti : AppCompatActivity(), UploadRequestBody.UploadCallback {
    private var otp: Int? = null
    private var code: Int? = null
    val TAG = "Signup_activity"
    private var selectedImageUri: Uri? = null
    private var urlPath : String? = null
    private var mob: String? = null
    private var bool = false



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)



        val file_upload_1 = findViewById<AppCompatButton>(R.id.file_upload_1)
        val editTextName_signup = findViewById<TextInputEditText>(R.id.editTextName_signup)
        val editTextNumber_signup = findViewById<TextInputEditText>(R.id.editTextNumber_signup)
        val editTextPassword_signup = findViewById<TextInputEditText>(R.id.editTextPassword_signup)
        val editTextAdhar_signup = findViewById<TextInputEditText>(R.id.editTextAdhar_signup)
        val editTextProof_signup = findViewById<TextView>(R.id.editTextProof_signup)
        val mukeshview = findViewById<OtpView>(R.id.mukeshview)
        val bt_back = findViewById<ImageButton>(R.id.bt_back)
        val buttonSignUp: AppCompatButton = findViewById(R.id.buttonSignUp)
        val vfbt: AppCompatButton = findViewById(R.id.vfbt)
         mob = editTextNumber_signup.text?.toString()

        bt_back.setOnClickListener {
            val i = Intent(this, Login_Acti::class.java)
            startActivity(i)
            finish()
        }


        vfbt.isEnabled = false
        //otpverify.isVisible = true

        vfbt.setOnClickListener {
            otpSend(mob!!)
            otpverify.isVisible = true
        }

        otpverify.setOnClickListener {
            Log.d(TAG, "onViewClicked: $otp")
            if (code == otp) {
                otpconfirmed.isVisible = true
                Toast.makeText(applicationContext, "OTP Verified.", Toast.LENGTH_SHORT).show()
                mukeshview.isVisible = false
                vfbt.isVisible = false
                otpverify.isVisible = false
                bool = true
            } else {
                Toast.makeText(applicationContext, "OTP Invalid.", Toast.LENGTH_SHORT).show()
            }

        }


        editTextNumber_signup.addTextChangedListener(textWatcher)














        //  verifyfbt.isClickable = mobileValiate( editTextNumber_signup.text.toString().trim())



        file_upload_1.setOnClickListener {
            openImageChooser()
        }

        val mobile2 = editTextNumber_signup.text.toString().trim()
        val adhar2 = editTextAdhar_signup.text.toString().trim()
        //buttonSignUp.isEnabled( !adhar2.isEmpty()  )


        buttonSignUp.setOnClickListener {
            val name = editTextName_signup.text.toString().trim()
            val mobile = editTextNumber_signup.text.toString().trim()
            val password = editTextPassword_signup.text.toString().trim()
            val adhar = editTextAdhar_signup.text.toString().trim()
            val proof =  editTextProof_signup.text.toString().trim()



            if(name.isEmpty()){
                editTextName_signup.error = "Name required"
                editTextName_signup.requestFocus()
                return@setOnClickListener
            }
            if(mobile.isEmpty()){
                editTextNumber_signup.error = "Mobile Number Required"
                editTextNumber_signup.requestFocus()
                return@setOnClickListener

            }
            if (bool == true){



            }else{
                layout_root.snackbar("Please Verify OTP")
                return@setOnClickListener
            }

            if(adharValiate(adhar)){

            }else{
                editTextAdhar_signup.error = "enter the 12 digit adhar number"
                editTextAdhar_signup.requestFocus()
                return@setOnClickListener
            }

            if (mobileValiate(mobile)){
            }else{
                editTextNumber_signup.error = "check the number you have entered"
                editTextNumber_signup.requestFocus()
                return@setOnClickListener

            }


            if(password.isEmpty()){
                editTextPassword_signup.error = "Password required"
                editTextPassword_signup.requestFocus()
                return@setOnClickListener
            }
            if(adhar.isEmpty()){
                editTextAdhar_signup.error = "adhar Number required"
                editTextAdhar_signup.requestFocus()
                return@setOnClickListener
            }
            if(proof.isEmpty()){
                editTextProof_signup.error = "Upload File"
                editTextProof_signup.requestFocus()
                return@setOnClickListener
            }


            if (selectedImageUri == null) {
                layout_root.snackbar("Select an File First")
                return@setOnClickListener
            }

            val parcelFileDescriptor =
                contentResolver.openFileDescriptor(selectedImageUri!!, "r", null) ?: return@setOnClickListener

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))

            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            progress_bar.progress = 0
            val body = UploadRequestBody(file, "proof", this)




//  RetrofitClient.instance.createUser(name,adhar,proof,mobile,password)
            RetrofitClient.instance.createUser(
                RequestBody.create(MediaType.parse("multipart/form-data"), "$name"),
                RequestBody.create(MediaType.parse("multipart/form-data"), "$adhar"),
                MultipartBody.Part.createFormData("proof", file.name, body),
                RequestBody.create(MediaType.parse("multipart/form-data"), "$mobile"),
                RequestBody.create(MediaType.parse("multipart/form-data"), "$password"))
                .enqueue(object: Callback<DefaultResponse>{
                    override fun onFailure(call: Call<DefaultResponse>, t: Throwable) {
                      //  Toast.makeText(applicationContext, t.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "onResponse: signup failed")
                        layout_root.snackbar("Please Check the internet")
                        progress_bar.progress = 0
                    }

                    override fun onResponse(call: Call<DefaultResponse>, response: Response<DefaultResponse>) {
                        Log.d(TAG, response.body().toString())
                        Log.d(TAG,name+" "+mobile+" "+password+" "+adhar+" "+proof)

                        val responseBody =response.body()

                        response.body()?.let {


                            progress_bar.progress = 100
                            if (responseBody?.status.equals("success")){

                                val intent = Intent(applicationContext, Login_Acti::class.java)
                                intent.flags =
                                    Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
                                Toast.makeText(applicationContext,"successfully register please login to continue",Toast.LENGTH_LONG).show()
                                startActivity(intent)
                            }else{
                                Toast.makeText(applicationContext,"Mobile Number Already Registered",Toast.LENGTH_SHORT).show()
                            }
                        }

//                        if(response.isSuccessful) {
//
//                        }
//                        else{
//                            Toast.makeText(applicationContext,"Mobile Number Already Registered",Toast.LENGTH_SHORT).show()
//                        }
                    }

                })
        }
    }

   // ****************************************************************************


    private fun otpSend(mobile: String) {
       // code = Random().nextInt(9000) + 1000
       code = 1234
        Log.d(TAG, "otpSend: $code")
//        val message =
//            " Your registration OTP is $code"
//        val urlString =
//            "http://app.smsmoon.com/submitsms.jsp?user=PIVOTAL&key=7d9a0596c8XX&mobile=$mobile&message=$message&senderid=PVOTAL&accusage=1"
//        val webView = WebView(applicationContext)
//        webView.loadUrl(urlString)
        Toast.makeText(
            applicationContext,
            "OTP sent to your registered mobile no.${mobile}",
            Toast.LENGTH_SHORT
        ).show()
        mukeshview.setOtpCompletionListener(OnOtpCompletionListener { otprecive ->
            otp = otprecive.toInt()
            Log.d(TAG, "otpnum: $otp")
        })
    }


//    fun onViewClicked(view: View) {
//        when (view.id) {
//            R.id.vfbt -> {
//                Log.d(
//                    TAG,
//                    "onViewClicked: $otp"
//                )
//                if (code == otp) {
//                    Toast.makeText(applicationContext, "OTP Verified.", Toast.LENGTH_SHORT).show()
//
//                } else {
//                    Toast.makeText(applicationContext, "OTP Invalid.", Toast.LENGTH_SHORT).show()
//                }
//            }
////            R.id.txtresend ->                // progressBar.setVisibility(View.VISIBLE);
////                otpSend(mob)
//        }
//    }



    private fun mobileValiate(text : String): Boolean {
        var p: Pattern = Pattern.compile("[6-9][0-9]{9}")
        var m: Matcher = p.matcher(text)
        return m.matches()
    }

    private fun adharValiate(text : String): Boolean {
        var p: Pattern = Pattern.compile("[0-9]{12}")
        var m: Matcher = p.matcher(text)
        return m.matches()
    }



//    private fun startFileChooser() {
//
//        var i = Intent()
//        i.setType("image/*")
//        i.setAction(Intent.ACTION_GET_CONTENT)
//        startActivityForResult(Intent.createChooser(i,"Choose Picture"),111)
//
//    }

//    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
//        super.onActivityResult(requestCode, resultCode, data)
//        if (requestCode == 111 && resultCode == Activity.RESULT_OK && data != null){
//            selectedImageUri = data.data!!
//            urlPath=data.data!!.path
//            editTextProof_signup.text = urlPath
//        }
//    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, REQUEST_CODE_PICK_IMAGE)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                REQUEST_CODE_PICK_IMAGE -> {
                    if (data != null) {
                        selectedImageUri = data.data!!
                        urlPath= selectedImageUri!!.path
                        val file2 = File(cacheDir, contentResolver.getFileName(selectedImageUri!!))
                        editTextProof_signup.text = file2.name.toString()
                            // "imagefile/"+urlPath


                    }

                }
            }
        }
    }

    companion object {
        const val REQUEST_CODE_PICK_IMAGE = 101
    }

    override fun onStart() {
        super.onStart()

        if(SharedPrefManager.getInstance(this).isLoggedIn){
            val intent = Intent(applicationContext, MainActivity::class.java)
            intent.flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK

            startActivity(intent)
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        progress_bar.progress = percentage

    }

    private val textWatcher =(object : TextWatcher{
        override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

        }

        override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {

            val n  = editTextNumber_signup.text.toString()

            if (!n.isEmpty() && mobileValiate(n)){
                vfbt.isEnabled = true
            }else{
                vfbt.isEnabled = false
            }
        }

        override fun afterTextChanged(s: Editable?) {

        }

    })
}


