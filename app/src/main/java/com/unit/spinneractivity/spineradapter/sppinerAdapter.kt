package com.unit.spinneractivity.spineradapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.R
import com.unit.spinneractivity.modelcllass.SpinerModelClass

class sppinerAdapter(var spiinercallback: SppinerCallBack) :
    RecyclerView.Adapter<sppinerAdapter.VhRecycler>() {
    var imagelist = mutableListOf<SpinerModelClass>()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhRecycler {
        val view: View = LayoutInflater.from(parent.context)
            .inflate(R.layout.spiner_recyclerview_layout, parent, false)
        return VhRecycler(view)
    }

    override fun onBindViewHolder(holder: VhRecycler, position: Int) {
        val imageitem = imagelist[position]
        holder.imageview.setImageResource(imageitem.model)
        holder.imageview.setOnClickListener {
            spiinercallback.onClickListner(position)
        }
    }

    override fun getItemCount(): Int {
        return imagelist.size
    }

    fun dataset(image: MutableList<SpinerModelClass>) {
        imagelist = image
    }

    class VhRecycler(itemView: View) : RecyclerView.ViewHolder(itemView) {

        val imageview = itemView.findViewById<ImageView>(R.id.recyclerimage)

    }

    interface SppinerCallBack {
        fun onClickListner(positon: Int)
    }

}