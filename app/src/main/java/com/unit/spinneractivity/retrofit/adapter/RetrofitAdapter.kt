package com.unit.spinneractivity.retrofit.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.R
import com.unit.spinneractivity.retrofit.model.RetrofitModel
import timber.log.Timber

class RetrofitAdapter : RecyclerView.Adapter<RetrofitAdapter.VhRetrofit>() {
   private var responselist = listOf<RetrofitModel>()

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): VhRetrofit {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.retrofitadapter_layout, parent, false)
        Timber.d("recycler viewholder")
        return VhRetrofit(view)
    }

    override fun onBindViewHolder(holder: VhRetrofit, position: Int) {
        val item = responselist[position]
        Timber.d("recycler item ${item}")
        holder.textfirst.setText(item.title)
    }

    override fun getItemCount(): Int {
//        Timber.d("recycler item count")
        return responselist.size
    }

    fun set(data: List<RetrofitModel>) {
        Timber.d("recycler ${data.get(1).title}")
        responselist = data
    }

    class VhRetrofit(itemview: View) :
        RecyclerView.ViewHolder(itemview) {
        val textfirst = itemview.findViewById<TextView>(R.id.adaptertextview)
    }

}