package com.unit.spinneractivity.roomdatabase.fragments

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.databinding.FragmentLoginSuccessBinding
import com.unit.spinneractivity.roomdatabase.adapter.RoomAdapter
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*


class LoginSuccessFragment : Fragment() {

    var binding: FragmentLoginSuccessBinding? = null
    val roomadatapter = RoomAdapter()
    var entity = UserEntity()
    var calendar: Calendar? = null
    var dateInstance: DatePickerDialog.OnDateSetListener? = null
    val viewmodel by activityViewModels<RoomViewModel>()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {

        binding = FragmentLoginSuccessBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)


        recycler()
        datePickder()
        clicklistner()
        Timber.d("uidw ${entity.uid}")

    }

    private fun recycler() {

//
//            roomadatapter.datsset(it)
//            binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
//            binding?.recycler?.adapter = roomadatapter
//            Timber.d("size ${it.size}")
//
//

    }


    private fun clicklistner() {

        binding?.logout?.setOnClickListener {
            viewmodel.loadFragment(LoginRegisterFragment())
            entity.islogin = false
        }


        binding?.insert?.setOnClickListener {
            entity.islogin = true
            DatePickerDialog(requireContext(),
                dateInstance,
                calendar!!.get(Calendar.YEAR),
                calendar!!.get(Calendar.MONTH),
                calendar!!.get(Calendar.DAY_OF_MONTH))
                .show()

        }
    }

    fun datePickder() {
        calendar = Calendar.getInstance()
        dateInstance = DatePickerDialog.OnDateSetListener { view, year, mont, dayofmont ->
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, mont)
            calendar?.set(Calendar.DAY_OF_MONTH, dayofmont)
            dateFormate(calendar!!)
        }
    }

    @SuppressLint("SimpleDateFormat")
    fun dateFormate(calendar: Calendar) {
//        viewmodel.liveDataForUserInfo.observe(viewLifecycleOwner, Observer {
//            entity = it
//            Timber.d("uidd ${entity.uid}")
//        })
        val dateformate = SimpleDateFormat("dd_MM_yyyy")
        val dateconvert = dateformate.format(calendar.time)
        val dateformate1 = SimpleDateFormat("MM_dd_yyyy")
        val dateconvert1 = dateformate1.format(calendar.time)
        val dateformate2 = SimpleDateFormat("yyyy_MM_dd")
        val dateconvert2 = dateformate2.format(calendar.time)
        entity.uid?.let { viewmodel.addInUserData(dateconvert, dateconvert1, dateconvert2, it) }

    }

}