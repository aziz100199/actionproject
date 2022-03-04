package com.unit.spinneractivity.roomdatabase.fragments

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Toast
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.R
import com.unit.spinneractivity.databinding.FragmentProfileBinding
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class ProfileFragment : Fragment() {

    var binding: FragmentProfileBinding? = null
    var imagestring: String? = null
    val viewmodel by activityViewModels<RoomViewModel>()
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
        statusBar()

    }

    private fun statusBar() {
        val window = activity?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun insertValue() {
        binding?.submitbtn?.setOnClickListener {

            val username = binding?.nameedittext?.text.toString()
            val useremail = binding?.emailedittext?.text.toString()
            if (username.isEmpty() && useremail.isEmpty()) {
                Toast.makeText(requireContext(), "Please put both fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                viewmodel.profileData(username, useremail, imagestring)
                Toast.makeText(requireContext(), "inserted successfully", Toast.LENGTH_SHORT)
                    .show()
                viewmodel.loadFragment(LoginSuccessFragment())
            }
        }

        binding?.insertimage?.setOnClickListener {
            val inten = Intent()
            inten.type = "image/*"
            inten.setAction(Intent.ACTION_GET_CONTENT)
            val Pick_Image = 1
            startActivityForResult(Intent.createChooser(inten, "select image"), Pick_Image)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, intentdata: Intent?) {
        if (requestCode == 1) {
            val imageuri: Uri = intentdata?.data!!
            imagestring = intentdata.data?.toString()
            binding?.insertimage?.setImageURI(imageuri)
            binding?.selectpicturetv?.isVisible = false
            Toast.makeText(requireContext(), "selection succesfully", Toast.LENGTH_SHORT).show()
        }
    }

}