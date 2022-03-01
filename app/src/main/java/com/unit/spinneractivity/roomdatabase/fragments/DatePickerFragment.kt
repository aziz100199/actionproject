package com.unit.spinneractivity.roomdatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.databinding.FragmentDatePickerBinding
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class DatePickerFragment : Fragment() {
    var binding: FragmentDatePickerBinding? = null
    val viewmodel by activityViewModels<RoomViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentDatePickerBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        dataPicker()


    }

    private fun dataPicker() {

    }


}
