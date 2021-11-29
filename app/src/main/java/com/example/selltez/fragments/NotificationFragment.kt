package com.example.selltez.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selltez.adapter.RecyclerAdapterNotification
import com.example.selltez.model.NotificationDetails
import com.example.selltez.model.NotificatonResponse
import com.example.selltez.api.RetrofitClient
import com.example.selltez.databinding.FragmentNotificationBinding
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.snackbar
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class NotificationFragment : Fragment() {

    private val TAG = "ComplaintFragment"

    private lateinit var preferences: SharedPreferences
    private lateinit var recyclerView: RecyclerView
    private var newArrayList: ArrayList<NotificationDetails> = arrayListOf()
    private lateinit var binding: FragmentNotificationBinding




    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentNotificationBinding.inflate(inflater,container,false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME,Context.MODE_PRIVATE)
        val empid = preferences.getString("empid",null)!!
        val madapter = RecyclerAdapterNotification(newArrayList)
        binding.mrecyclerview2.adapter = madapter
        binding.mrecyclerview2.layoutManager = LinearLayoutManager(activity?.applicationContext)
        binding.loading2.text = "No Notifications Yet"
        newArrayList.clear()
        RetrofitClient.instance.notificationStatus("$empid")
            .enqueue(object: Callback<NotificatonResponse>{
                override fun onResponse(
                    call: Call<NotificatonResponse>,
                    response: Response<NotificatonResponse>
                ) {
                    val det = response.body()
                    if (response.isSuccessful){
                        newArrayList.addAll(det!!.Details)
                        madapter.notifyDataSetChanged()
                        binding.loading2.text = ""
                    }
                }

                override fun onFailure(call: Call<NotificatonResponse>, t: Throwable) {
                    binding.ntf.snackbar("Please Check the internet")
                }

            })

    }

}