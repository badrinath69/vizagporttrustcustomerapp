package com.example.selltez.fragments

import android.content.Context
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.selltez.R
import com.example.selltez.activities.RecyclerShow
import com.example.selltez.adapter.RecyclerAdapter
import com.example.selltez.api.RetrofitClient
import com.example.selltez.databinding.FragmentComplaintBinding
import com.example.selltez.model.ComplaintInfoResponse
import com.example.selltez.model.ComplaintResponse
import com.example.selltez.model.LoginResponse
import com.example.selltez.storage.Communicator
import com.example.selltez.storage.ComplaintDetails
import com.example.selltez.storage.SharedPrefManager
import com.example.selltez.utils.snackbar
import kotlinx.android.synthetic.main.complaint_cardview.*
import kotlinx.android.synthetic.main.fragment_complaint.*
import kotlinx.android.synthetic.main.fragment_home.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class ComplaintFragment : Fragment() {
    private val TAG = "ComplaintFragment"
    private lateinit var communicator: Communicator

    private lateinit var preferences: SharedPreferences
  //  private lateinit var recyclerView: RecyclerView
    private var newArrayList: ArrayList<ComplaintDetails> = arrayListOf()




    private lateinit var binding: FragmentComplaintBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)


    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
       binding = FragmentComplaintBinding.inflate(inflater,container,false)
        return binding.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        preferences = requireActivity().getSharedPreferences(SharedPrefManager.SHARED_PREF_NAME, Context.MODE_PRIVATE)
        val empid = preferences.getString("empid", null)!!

        communicator = activity as Communicator

        val madapter = RecyclerAdapter(requireActivity(),newArrayList)
        binding.mrecyclerview.adapter = madapter
//        madapter.setOnItemClickListner(object : RecyclerAdapter.onItemClickListner{
//            override fun onItemClick(position: Int) {
//                activity?.supportFragmentManager?.beginTransaction()?.apply {
//                    replace(R.id.content_frame_2,recyclerdetails())
//                    commit()
//
//                }
//            }
//
//        })


        binding.mrecyclerview.layoutManager = LinearLayoutManager(activity?.applicationContext)


        binding.loading.text = "No Complaints Yet"
        fab.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.apply {
                replace(R.id.content_frame_2,HomeFragment())
                commit()
            }
        }

        newArrayList.clear()
        RetrofitClient.instance.complaintposts(empid)
            .enqueue(object: Callback<ComplaintInfoResponse> {
                override fun onFailure(call: Call<ComplaintInfoResponse>, t: Throwable) {
                   // Toast.makeText(activity?.applicationContext, t.message, Toast.LENGTH_LONG).show()
                    binding.ntf2.snackbar("Please Check the internet")


                }

                override fun onResponse(call: Call<ComplaintInfoResponse>, response: Response<ComplaintInfoResponse>) {
                    val det = response.body()
                    // newArrayList = response
                    Log.d(TAG, response.body().toString())
//
//                    response.body()?.let {
//
//
//                       // progress_bar2.progress = 100
//                    }
                    if(response.isSuccessful) {
                        //Toast.makeText(this,,Toast.LENGTH_SHORT)
                            for (i in det?.Details!!.indices){
                                newArrayList.addAll(listOf(det!!.Details[i]))

                            }
                        madapter!!.notifyDataSetChanged()
                        binding.loading.text = ""


                        madapter.setOnItemClickListner(object : RecyclerAdapter.onItemClickListner{
                            override fun onItemClick(position: Int) {
                                communicator.passData(newArrayList.get(position).raiseddate.toString(),
                                    newArrayList.get(position).against.toString(),
                                    newArrayList.get(position).description.toString(),
                                    newArrayList.get(position).file.toString())
                            }

                        })



                    }
                }

            })

                }


}