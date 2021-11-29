package com.example.selltez.fragments

import android.os.Bundle
import android.text.Html
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.bumptech.glide.Glide
import com.example.selltez.R
import com.example.selltez.databinding.FragmentRecyclerdetailsBinding



class recyclerdetails : Fragment() {

    private lateinit var binding: FragmentRecyclerdetailsBinding

    var tim: String? = ""
    var personagainst: String? = ""
    var descrip: String? = ""
    var imgid: String? = ""

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {



        binding = FragmentRecyclerdetailsBinding.inflate(inflater, container, false)
        tim = arguments?.getString("dt")
        personagainst = arguments?.getString("an")
        descrip = arguments?.getString("dess")
        imgid = arguments?.getString("img")
        binding.showtime.text = tim
        binding.showagainst.text = personagainst
        binding.showdes.text = descrip
        val photoid = "http://mobilevakil.com/vasu/port/upload/"+"$imgid"
        Glide.with(activity?.applicationContext!!).load(photoid).into(binding.showimgid)

        binding.backdett.setOnClickListener {
            activity?.supportFragmentManager?.beginTransaction()?.replace(R.id.content_frame_2,ComplaintFragment())?.commit()
        }


        return binding.root

    }


}