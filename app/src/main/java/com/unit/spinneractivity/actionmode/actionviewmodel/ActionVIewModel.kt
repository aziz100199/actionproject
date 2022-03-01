package com.unit.spinneractivity.actionmode.actionviewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.liveData

class ActionVIewModel(application: Application) : AndroidViewModel(application) {
    val listname = mutableListOf<String>()
    private var mutableLiveData = MutableLiveData<MutableList<String>>()
    var liveData = liveData {
        emitSource(mutableLiveData)
    }


    fun init() {

        listname.add("first")
        listname.add("second")
        listname.add("third")
        listname.add("four")
        listname.add("five")
        listname.add("six")
        listname.add("seven")
        listname.add("eight")
        listname.add("nine")
        listname.add("ten")
        listname.add("eleven")
        listname.add("twelve")
        listname.add("thirteen")
        listname.add("fourteen")
        listname.add("fifteen")
//        listname.add("sixteen")
//        listname.add("seventeen")
//        listname.add("eighteen")
//        listname.add("nineteen")
//        listname.add("twenty")
        mutableLiveData.postValue(listname)

    }

    fun removeItem(item: MutableList<String>) {
        item.forEach{
            listname.remove(it)
            mutableLiveData.postValue(listname)
        }

    }

    fun swapremoveItem(item: String) {
        listname.remove(item)
        mutableLiveData.postValue(listname)

    }


}