package com.example.selltez.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.example.selltez.R
import com.example.selltez.model.NotificationDetails

class RecyclerAdapterNotification(val userList: ArrayList<NotificationDetails>): RecyclerView.Adapter<RecyclerAdapterNotification.MyAdapter>() {


    inner class MyAdapter(view: View) : RecyclerView.ViewHolder(view){

        val tx1 = view.findViewById<TextView>(R.id.datetext)
        val tx2 = view.findViewById<TextView>(R.id.reviewtext)
        val tx3 = view.findViewById<TextView>(R.id.reviewdescription)
        val tx4 = view.findViewById<TextView>(R.id.againstmsgid)
        val tx5 = view.findViewById<TextView>(R.id.datetextraised)


    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): MyAdapter {
        val v = LayoutInflater.from(parent.context).inflate(R.layout.notificationsub,parent,false)
        return MyAdapter(v)
    }

    override fun onBindViewHolder(holder: MyAdapter, position: Int) {
        val currentItem = userList[position]
        holder.tx1.text = currentItem.modifieddate
        holder.tx2.text = currentItem.currentstatus
        holder.tx3.text = currentItem.statusdesc
        holder.tx4.text = currentItem.against
        holder.tx5.text = currentItem.raiseddate

    }

    override fun getItemCount(): Int {
        return userList.size
    }
}
