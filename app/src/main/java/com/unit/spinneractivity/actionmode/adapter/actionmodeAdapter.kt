package com.unit.spinneractivity.actionmode.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.R
import timber.log.Timber

class actionmodeAdapter(var context: Context, var adapterCallBack: AdapterCallBack) :
    RecyclerView.Adapter<actionmodeAdapter.VhRecycler>() {
    var list = mutableListOf<String>()
    var isSelected = false
    var selecteditemlist = mutableListOf<String>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhRecycler {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.action_mode_recycler_layout, parent, false)
//        Timber.d("onCreateViewHolder")
        return VhRecycler(view)
    }


    override fun onBindViewHolder(holder: VhRecycler, position: Int) {
        val item = list[position]
        holder.textrecycler.text = item
//        Timber.d("onBindViewHolder")


        holder.layout.setOnLongClickListener {
            isSelected = true
            holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.selection))
            selecteditemlist.add(item)
            adapterCallBack.onItemLongClick(selecteditemlist.size, item)
            Timber.d("setOnLongClickListener ___ size  ${selecteditemlist.size}" +
                    "item ------- $selecteditemlist")

            true

        }
//        if (selecteditemlist.size > 0) {
//            selecteditemlist.clear()
//        }

        holder.layout.setOnClickListener {
            if (isSelected) {
                if (selecteditemlist.contains(item)) {
                    selecteditemlist.remove(item)
                    adapterCallBack.onItemClick(selecteditemlist.size, item)
                    holder.layout.setBackgroundColor(ContextCompat.getColor(context, R.color.white))
                    Timber.d("setOnClickListener if ------ item $selecteditemlist" +
                            "size ${selecteditemlist.size}")

                } else {
                    selecteditemlist.add(item)
                    adapterCallBack.onItemClick(selecteditemlist.size, item)
                    holder.layout.setBackgroundColor(ContextCompat.getColor(context,
                        R.color.selection))
                    Timber.d("setOnClickListener else------- size ${selecteditemlist.size}" +
                            "item $selecteditemlist")
                }

                if (selecteditemlist.size == 0) {
                    isSelected = false
                }

            }
//            selecteditemlist.clear()
        }


    }

    override fun getItemCount(): Int {

        return list.size
    }

    @SuppressLint("NotifyDataSetChanged")
    fun dataset(name: MutableList<String>) {
        list = name
        Timber.d("dataset")
        notifyDataSetChanged()
    }

    inner class VhRecycler(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val textrecycler = itemView.findViewById<TextView>(R.id.recyclertext)
        val layout = itemView.findViewById<ConstraintLayout>(R.id.layoutclick)
    }


    interface AdapterCallBack {

        fun onItemClick(selectedfilescount: Int, selecteItem: String)
        fun onItemLongClick(selectedfilescount: Int, selecteItem: String)

    }
}