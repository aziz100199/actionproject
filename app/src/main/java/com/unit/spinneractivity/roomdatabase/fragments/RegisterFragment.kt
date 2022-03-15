package com.unit.spinneractivity.roomdatabase.fragments

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.google.android.material.snackbar.Snackbar
import com.unit.spinneractivity.roomdatabase.RoomDbActivity
import com.unit.spinneractivity.databinding.FragmentRegisterBinding
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class RegisterFragment : Fragment() {
    var binding: FragmentRegisterBinding? = null

    val viewmodel by activityViewModels<RoomViewModel>()
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View? {
        binding = FragmentRegisterBinding.inflate(layoutInflater, container, false)
        return (binding?.root)

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        clickListner()
        toolbar()

    }

    private fun toolbar() {
        (requireContext() as RoomDbActivity).setSupportActionBar(binding?.toolbar)
    }

    private fun clickListner() {

        binding?.backbtn?.setOnClickListener {

            viewmodel.loadFragment(LoginRegisterFragment())

        }

        binding?.registerbtn?.setOnClickListener {

            val username = binding?.editregistername?.text.toString()
            val userpassword = binding?.editregisterpassword?.text.toString()
            if (username.isEmpty() || userpassword.isEmpty()) {
                Snackbar.make(binding!!.registersnackbar,
                    "Please put both fileds",
                    Snackbar.LENGTH_LONG).show()
            } else {

                binding?.registersnackbar?.let { snackbar ->
                    viewmodel.registerUsers(username, userpassword, snackbar)
                }

                Snackbar.make(binding!!.registersnackbar, "Data Inserted", Snackbar.LENGTH_LONG)
                    .show()
            }
        }

    }
}