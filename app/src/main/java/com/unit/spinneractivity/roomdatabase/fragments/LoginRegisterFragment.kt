package com.unit.spinneractivity.roomdatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.unit.spinneractivity.databinding.FragmentRoomLoginInterfaceBinding
import com.unit.spinneractivity.roomdatabase.room.entities.UserEntity
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class LoginRegisterFragment() : Fragment() {
    var binding: FragmentRoomLoginInterfaceBinding? = null
    var loginEntity = UserEntity()
    val viewmodel by activityViewModels<RoomViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRoomLoginInterfaceBinding.inflate(layoutInflater, container, false)
        return binding?.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListner()
        subscribeObservers()
    }

    private fun subscribeObservers() {

    }

    private fun clickListner() {
        binding?.registerbtn?.setOnClickListener {
            viewmodel.loadFragment(RegisterFragment())
        }

        binding?.loginbtn?.setOnClickListener {
            val username = binding?.editloginname?.text.toString()
            val userpassword = binding?.editpassword?.text.toString()

            if (username.isEmpty() || userpassword.isEmpty()) {
                Snackbar.make(binding!!.loginsnackbar,
                    "please enter both fields",
                    Snackbar.LENGTH_LONG).show()
            } else {
                viewmodel.submitLoginData(username, userpassword)
            }
        }


    }
}
