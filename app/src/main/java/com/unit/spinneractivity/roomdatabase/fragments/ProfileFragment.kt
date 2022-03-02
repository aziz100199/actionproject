package com.unit.spinneractivity.roomdatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.unit.spinneractivity.databinding.FragmentProfileBinding
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class ProfileFragment : Fragment() {

    var binding: FragmentProfileBinding? = null
    val viewmodel by viewModels<RoomViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater, container, false)
        return binding?.root

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        insertValue()

    }

    private fun insertValue() {
        binding?.submitbtn?.setOnClickListener {
            val username = binding?.nameedittext?.text.toString()
            val useremail = binding?.emailedittext?.text.toString()
            if (username.isEmpty() && useremail.isEmpty()) {
                Toast.makeText(requireContext(), "Please put both fields", Toast.LENGTH_SHORT)
                    .show()
            }
            else{
            viewmodel.profileData(username, useremail)
            }
        }
    }


}