package com.unit.spinneractivity.roomdatabase.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.LinearLayoutManager
import com.unit.spinneractivity.databinding.FragmentLoginSuccessBinding
import com.unit.spinneractivity.roomdatabase.adapter.RoomAdapter
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel
import java.util.*


class LoginSuccessFragment : Fragment() {

    var binding: FragmentLoginSuccessBinding? = null
    val roomadatapter = RoomAdapter()
    var entity = UserEntity()
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

        suscribeobserver()
    }

    private fun suscribeobserver() {
        viewmodel.userDataListLD.observe(viewLifecycleOwner) {
            roomadatapter.datsset(it)
        }
    }

    private fun recycler() {
        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
        binding?.recycler?.adapter = roomadatapter

    }


    private fun clicklistner() {

        binding?.logout?.setOnClickListener {
            viewmodel.loadFragment(LoginRegisterFragment())
            entity.islogin = false
        }


        binding?.insert?.setOnClickListener {
            entity.islogin = true
            val calendar = Calendar.getInstance()
            DatePickerDialog(requireContext(),
                dateInstance,
                calendar.get(Calendar.YEAR),
                calendar.get(Calendar.MONTH),
                calendar.get(Calendar.DAY_OF_MONTH))
                .show()

        }
    }

    fun datePickder() {
        dateInstance = DatePickerDialog.OnDateSetListener { view, year, mont, dayofmont ->
            viewmodel.onDatePicked(year, mont, dayofmont)
        }
    }


}