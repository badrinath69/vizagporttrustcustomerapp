package com.example.selltez.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.bumptech.glide.Glide
import com.example.selltez.api.RetrofitClient
import com.example.selltez.databinding.FragmentAccountBinding
import com.example.selltez.model.AccountResponse
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class AccountFragment() : Fragment() {


    lateinit var binding: FragmentAccountBinding
    private lateinit var preferences: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentAccountBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        preferences = requireActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)

        val empid = preferences.getString("empid", null)!!






        RetrofitClient.instance.accountDetails("$empid")
            .enqueue(object: Callback<AccountResponse>
            {
                override fun onResponse(
                    call: Call<AccountResponse>,
                    response: Response<AccountResponse>
                ) {
                    val det = response.body()?.Details
                    if (response.isSuccessful){
                        binding.textview32.text = det?.get(0)?.name.toString()
                        binding.textView5.text = det?.get(0)?.mobile.toString()
                      //   binding.textView6.text = det?.get(0)?.password.toString()
                        binding.textView7.text = det?.get(0)?.adhar.toString()
                        val photolasturl = det?.get(0)?.proof.toString()
                        val photoid = "http://mobilevakil.com/vasu/port/upload/"+"$photolasturl"
                        Glide.with(activity?.applicationContext!!).load(photoid).into(binding.imageView)

//                        val sharedPreferences = requireActivity().getSharedPreferences("l", Context.MODE_PRIVATE)
//                        val editor = sharedPreferences.edit()
//                            editor.putString("empid2", det?.get(0)!!.empid.toString())
//                            editor.putString("password2", det?.get(0)!!.password.toString())
//
//                        editor.apply()
//                        editor.commit()
                    }
                }

                override fun onFailure(call: Call<AccountResponse>, t: Throwable) {
                    //Toast.makeText(activity?.applicationContext,t.message,Toast.LENGTH_SHORT).show()
                    binding.ntf3.snackbar("Please Check the internet")

                }

            })

//        val photoid = "http://mobilevakil.com/vasu/port/upload/"+"$photopref"
//
//
//
//
//
//        binding.textview32.text = preferences.getString("name", null)!!
//        binding.textView5.text =  preferences.getString("mobile", null)!!
//        binding.textView6.text =  preferences.getString("password", null)!!
//        binding.textView7.text =  preferences.getString("adhar", null)!!
//
//        Glide.with(activity?.applicationContext!!).load(photoid).into(binding.imageView)























































//        preferences = requireActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
//
//        val photopref = preferences.getString("proof", null)!!
//
//
//        val photoid = "http://mobilevakil.com/vasu/port/upload/"+"$photopref"
//
//
//
//
//
//        binding.textview32.text = preferences.getString("name", null)!!
//        binding.textView5.text =  preferences.getString("mobile", null)!!
//        binding.textView6.text =  preferences.getString("password", null)!!
//        binding.textView7.text =  preferences.getString("adhar", null)!!
//
//        Glide.with(activity?.applicationContext!!).load(photoid).into(binding.imageView)

    }


}