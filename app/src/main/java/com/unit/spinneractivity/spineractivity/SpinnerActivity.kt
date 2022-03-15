package com.unit.spinneractivity.spineractivity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import com.unit.spinneractivity.LauncherActivity
import com.unit.spinneractivity.R
import com.unit.spinneractivity.databinding.ActivityMainBinding
import com.unit.spinneractivity.spineractivity.modelcllass.SpinerModelClass
import com.unit.spinneractivity.spineractivity.spineradapter.sppinerAdapter
import timber.log.Timber

class SpinnerActivity : AppCompatActivity(), sppinerAdapter.SppinerCallBack {
    val arrayvalue = arrayListOf<String>()
    var binding: ActivityMainBinding? = null
    var spineradpater = sppinerAdapter(this)
    val modelList = mutableListOf<SpinerModelClass>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        backpreesed()
        recyclerView()
        addValueinList()
        spinerAdapter()

    }

    private fun backpreesed() {
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    private fun recyclerView() {

        binding?.spinnerrecycler?.layoutManager = LinearLayoutManager(this)

        binding?.spinnerrecycler?.adapter = spineradpater

    }

    private fun spinerAdapter() {

        arrayvalue.add("please select your mobile")
        arrayvalue.add("Samsung")
        arrayvalue.add("Motorola")
        arrayvalue.add("Nokia")
        arrayvalue.add("Hawaii")


        val arrayadapter = ArrayAdapter(this, android.R.layout.simple_spinner_item, arrayvalue)

        binding?.spinner?.adapter = arrayadapter

        binding?.spinner?.onItemSelectedListener = object :

            AdapterView.OnItemSelectedListener {
            @SuppressLint("SetTextI18n")
            override fun onItemSelected(p0: AdapterView<*>?, p1: View?, p2: Int, p3: Long) {


                recyclerView()
                modelList.filter {
                    it.id == p2

                }.also {
                    spineradpater.dataset(it.toMutableList())
                }
//                binding?.receivedresult?.text =
//                    "id is:" + " " + list.get(p2).id.toString() + " Model is:" + " " + list.get(p2).model
            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                Timber.d("on ionNothingSelected $p0")
            }


        }
//        ArrayAdapter.createFromResource(this,
//            R.array.spinerlist,
//            android.R.layout.simple_spinner_item).also {adapter->
//            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item)
//            binding?.spinner?.adapter=adapter
//        }


    }

    //
    private fun addValueinList() {
        modelList.add(SpinerModelClass(1, R.drawable.samsung1))
        modelList.add(SpinerModelClass(1, R.drawable.samsung2))
        modelList.add(SpinerModelClass(1, R.drawable.samsung3))
        modelList.add(SpinerModelClass(2, R.drawable.metrola))
        modelList.add(SpinerModelClass(2, R.drawable.metrola2))
        modelList.add(SpinerModelClass(2, R.drawable.metrola3))
        modelList.add(SpinerModelClass(2, R.drawable.metrola4))
        modelList.add(SpinerModelClass(3, R.drawable.nokia))
        modelList.add(SpinerModelClass(3, R.drawable.nokia1))
        modelList.add(SpinerModelClass(4, R.drawable.nokia2))
        modelList.add(SpinerModelClass(4, R.drawable.huwaii1))
        modelList.add(SpinerModelClass(4, R.drawable.huwaii2))
        modelList.add(SpinerModelClass(4, R.drawable.huwaii3))

    }

    override fun onClickListner(positon: Int) {
        startActivity(Intent(this, LauncherActivity::class.java))
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    override fun onBackPressed() {
        super.onBackPressed()
    }

}