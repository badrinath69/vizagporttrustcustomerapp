package com.example.selltez.fragments

import android.app.Activity
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.selltez.R
import com.example.selltez.activities.Signup_Acti
import com.example.selltez.api.RetrofitClient
import com.example.selltez.databinding.FragmentHomeBinding
import com.example.selltez.model.ComplaintResponse
import com.example.selltez.model.DefaultResponse
import com.example.selltez.model.UploadRequestBody
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.getFileName
import com.example.selltez.utils.snackbar

import kotlinx.android.synthetic.main.activity_signup.*
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


class HomeFragment : Fragment(), UploadRequestBody.UploadCallback {

    lateinit var  description: String
    lateinit var  complaintagainst: String
    private val TAG = "HomeFragment"
    private lateinit var binding: FragmentHomeBinding
    private var selectedImageUri: Uri? = null
    private var urlPath : String? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
//        binding = FragmentHomeBinding.inflate(layoutInflater)
//        binding.root



    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentHomeBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        val preferences = requireActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val empid = preferences.getString("empid", null)

        binding.frhmbcbt.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.content_frame_2, ComplaintFragment())
                commit()
            }
        }
        binding.chooseFile.setOnClickListener {
            openImageChooser()
        }

        binding.submitbtHome.setOnClickListener {

            if(binding.homeEdittextnew.text.toString().trim().isEmpty()){
                binding.homeEdittextnew.error = "field required"
                binding.homeEdittextnew.requestFocus()
                return@setOnClickListener
            }
            if(binding.homeEdittext.text.toString().trim().isEmpty()){
                binding.homeEdittext.error = "description required"
                binding.homeEdittext.requestFocus()
                return@setOnClickListener
            }



            if (selectedImageUri == null) {
                layout_root2.snackbar("Select an File First")
                return@setOnClickListener
            }
            description = binding.homeEdittext.text.toString()
            complaintagainst = binding.homeEdittextnew.text.toString()

            val parcelFileDescriptor =
                activity?.contentResolver?.openFileDescriptor(selectedImageUri!!, "r", null) ?: return@setOnClickListener

            val inputStream = FileInputStream(parcelFileDescriptor.fileDescriptor)
            val file = File(activity?.cacheDir, activity?.contentResolver?.getFileName(selectedImageUri!!))

            val outputStream = FileOutputStream(file)
            inputStream.copyTo(outputStream)

            progress_bar2.progress = 0
            val body = UploadRequestBody(file, "file", this)

            RetrofitClient.instance.userComplaint(
                RequestBody.create(MediaType.parse("multipart/form-data"),"$complaintagainst"),
                RequestBody.create(MediaType.parse("multipart/form-data"), "$description"),
                MultipartBody.Part.createFormData("file", file.name, body),
                RequestBody.create(MediaType.parse("multipart/form-data"), "$empid"))
                .enqueue(object: Callback<ComplaintResponse>{
                    override fun onFailure(call: Call<ComplaintResponse>, t: Throwable) {
                      //  Toast.makeText(activity, t.message, Toast.LENGTH_LONG).show()
                        Log.d(TAG, "onResponse: upload failed")
                        layout_root2.snackbar("Please Check the internet")
                        progress_bar2.progress = 0
                    }

                    override fun onResponse(call: Call<ComplaintResponse>, response: Response<ComplaintResponse>) {
                        Log.d(TAG, response.body().toString())

                        response.body()?.let {
                            progress_bar2.progress = 100
                        }

                        if(response.isSuccessful) {
                            //Toast.makeText(this,,Toast.LENGTH_SHORT)
                            Toast.makeText(activity!!.applicationContext, "complaint raised successfully", Toast.LENGTH_LONG).show()
                            progress_bar2.progress = 0
                            binding.chooseFileTextview.text = "No file chosen"
                            binding.homeEdittext.text?.clear()
                            binding.homeEdittextnew.text?.clear()
                            activity?.supportFragmentManager?.beginTransaction()?.apply {
                                replace(R.id.content_frame_2, ComplaintFragment())
                                commit()
                            }

                        }
                    }

                })





        }






    }

    private fun openImageChooser() {
        Intent(Intent.ACTION_PICK).also {
            it.type = "image/*"
            val mimeTypes = arrayOf("image/jpeg", "image/png")
            it.putExtra(Intent.EXTRA_MIME_TYPES, mimeTypes)
            startActivityForResult(it, Signup_Acti.REQUEST_CODE_PICK_IMAGE)
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == Activity.RESULT_OK) {
            when (requestCode) {
                Signup_Acti.REQUEST_CODE_PICK_IMAGE -> {
                    if (data != null) {
                        selectedImageUri = data.data!!
                        urlPath = selectedImageUri!!.path
                        val file2 = File(activity?.cacheDir, activity?.contentResolver?.getFileName(selectedImageUri!!))
                        choose_file_textview.text = file2.name.toString()
                        // "imagefile/"+urlPath
                    }


                }


            }
        }
    }

    override fun onProgressUpdate(percentage: Int) {
        progress_bar2.progress = percentage
    }

}
