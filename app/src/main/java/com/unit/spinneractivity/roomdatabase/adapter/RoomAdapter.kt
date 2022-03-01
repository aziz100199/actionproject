package com.unit.spinneractivity.roomdatabase.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.R
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.VHRoom>() {
    var itemlist = listOf<UserEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.VHRoom {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.roomadapterlayout, parent, false)
        return VHRoom(view)
    }

    override fun onBindViewHolder(holder: RoomAdapter.VHRoom, position: Int) {
        val item = itemlist[position]
//        holder.firstitem.text =item.userdata.get(position).timeformate1
//        holder.seconditem.text = item.userdata.get(position).timeformate2
//        holder.thirditem.text = item.userdata.get(position).timeformate3
    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun datsset(item: List<UserEntity>) {
        itemlist = item
        notifyDataSetChanged()
    }

    class VHRoom(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val firstitem = itemView.findViewById<TextView>(R.id.firsttext)
        val seconditem = itemView.findViewById<TextView>(R.id.secondtext)
        val thirditem = itemView.findViewById<TextView>(R.id.thirdtext)
    }

}