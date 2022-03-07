package com.unit.spinneractivity.roomdatabase.fragments

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
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
    val requestcodeforgallery = 1
    val requestcondeforcamera = 2
    var alertDialog: AlertDialog? = null
    var dialogview: View? = null
    var imagestring: String? = null
    var name: String? = null
    var email: String? = null
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
        showDialogBox()
        insertValue()
        statusBar()
        subscribeobserver()

    }

    private fun subscribeobserver() {
        viewmodel.userDataListLD.observe(viewLifecycleOwner) {
            binding?.nameedittext?.setText(it.username)
            binding?.emailedittext?.setText(it.useremail)
//            name = it.username
//            email = it.useremail
        }
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
//                viewmodel.updateuser(username, useremail)
                viewmodel.profileData(username, useremail, imagestring)

                Toast.makeText(requireContext(), "inserted successfully", Toast.LENGTH_SHORT)
                    .show()

            }
        }

        binding?.insertimage?.setOnClickListener {
            alertDialog?.show()
        }


    }

    private fun showDialogBox() {
        dialogview =
            LayoutInflater.from(requireContext()).inflate(R.layout.coustomdialog_layout, null)
        alertDialog = AlertDialog.Builder(requireContext()).setView(dialogview)
            .setTitle("Image Selection").create()

        val cancelDialogbtn = dialogview?.findViewById<Button>(R.id.cancelbtn)

        cancelDialogbtn?.setOnClickListener {
            alertDialog?.dismiss()
        }
        val selectfromgallery = dialogview?.findViewById<TextView>(R.id.galleryselection)
        selectfromgallery?.setOnClickListener {
            val galleryintetn = Intent()
            galleryintetn.type = "image/*"
            galleryintetn.setAction(Intent.ACTION_GET_CONTENT)
            startActivityForResult(galleryintetn, requestcodeforgallery)
        }
        val selectusingcamera = dialogview?.findViewById<TextView>(R.id.cameraselection)
        selectusingcamera?.setOnClickListener {
            val opencamer = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
            startActivityForResult(opencamer, requestcondeforcamera)
        }

    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentdata: Intent?) {
        if (requestCode == requestcodeforgallery && resultCode == Activity.RESULT_OK) {
            val imageuri: Uri = intentdata?.data!!
            imagestring = intentdata.data?.toString()
            binding?.insertimage?.setImageURI(imageuri)
            binding?.selectpicturetv?.isVisible = false
            Toast.makeText(requireContext(), "selection succesfully", Toast.LENGTH_SHORT).show()
            alertDialog?.dismiss()
        }
        if (requestCode == requestcondeforcamera && resultCode == Activity.RESULT_OK) {
//            val imageuri: Uri = intentdata?.data!!
            imagestring = intentdata?.data?.toString()
            val imagebitmap = intentdata?.extras?.get("data") as Bitmap
            binding?.insertimage?.setImageBitmap(imagebitmap)
            binding?.selectpicturetv?.isVisible = false
            Toast.makeText(requireContext(), "selection succesfully", Toast.LENGTH_SHORT).show()
            alertDialog?.dismiss()
        }
    }

}