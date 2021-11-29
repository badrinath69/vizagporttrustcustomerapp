package com.example.selltez.adapter

import android.content.Context
import android.content.Intent
import android.text.TextUtils.replace
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import com.example.selltez.R
import com.example.selltez.activities.RecyclerShow
import com.example.selltez.fragments.ComplaintFragment
import com.example.selltez.fragments.HomeFragment
import com.example.selltez.storage.ComplaintDetails
import com.example.selltez.utils.snackbar
import kotlinx.android.synthetic.main.complaint_cardview.view.*
import kotlinx.android.synthetic.main.fragment_complaint.view.*

class RecyclerAdapter(val con: Context,private val userList: ArrayList<ComplaintDetails>) : RecyclerView.Adapter<RecyclerAdapter.MyViewHolder>(){

    private lateinit var mListener : onItemClickListner

    interface onItemClickListner{
        fun onItemClick(position: Int)
    }

    fun setOnItemClickListner(listner: onItemClickListner){
        mListener = listner
    }



    inner class MyViewHolder(view: View,listner: onItemClickListner) : RecyclerView.ViewHolder(view){

        val issue: TextView = view.findViewById(R.id.issueid)
        val dateandtime : TextView = view.findViewById(R.id.raiseDate)
        val againstmsg : TextView = view.findViewById(R.id.againstid)


//
//        v: View ->
//        val position: Int = adapterPosition
//        Toast.makeText(con,"${position + 1 } is clicked",Toast.LENGTH_SHORT).show()

        init {
            itemView.setOnClickListener {
                    listner.onItemClick(adapterPosition)
            }
        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyViewHolder {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.complaint_cardview,parent,false)
        return MyViewHolder(v,mListener)
    }

    override fun onBindViewHolder(holder: MyViewHolder, position: Int) {
        val currentItem = userList[position]
        holder.issue.text = currentItem.description
        holder.dateandtime.text = currentItem.raiseddate
        holder.againstmsg.text = currentItem.against


    }

    override fun getItemCount(): Int {
       return userList.size
    }

}

