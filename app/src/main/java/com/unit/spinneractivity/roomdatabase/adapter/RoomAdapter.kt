package com.unit.spinneractivity.roomdatabase.adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.databinding.RoomadapterlayoutBinding
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity

class RoomAdapter : RecyclerView.Adapter<RoomAdapter.VHRoom>() {
    var itemlist = listOf<DataEntity>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RoomAdapter.VHRoom {

        return VHRoom(
            RoomadapterlayoutBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: RoomAdapter.VHRoom, position: Int) {
        val item = itemlist[position]
//        holder.binding.apply {
//            firsttext.text = item.timeformate1
//            secondtext.text = item.timeformate2
//            thirdtext.text = item.timeformate3
//        }

    }

    override fun getItemCount(): Int {
        return itemlist.size
    }

    fun datsset(item: List<DataEntity>) {
        itemlist = item
        notifyDataSetChanged()
    }

    class VHRoom(val binding: RoomadapterlayoutBinding) : RecyclerView.ViewHolder(binding.root)

}