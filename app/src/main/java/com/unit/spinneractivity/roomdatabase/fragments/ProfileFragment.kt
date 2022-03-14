package com.unit.spinneractivity.roomdatabase.fragments

import android.Manifest
import android.app.Activity
import android.app.Activity.RESULT_CANCELED
import android.app.AlertDialog
import android.content.ContentValues
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Bitmap
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.provider.MediaStore
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.view.isVisible
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.R
import com.unit.spinneractivity.databinding.FragmentProfileBinding
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel
import timber.log.Timber
import java.io.File
import java.io.FileOutputStream


class ProfileFragment : Fragment() {

    var binding: FragmentProfileBinding? = null
    internal var imagePath: String? = null
    var dataentity: DataEntity? = null
    val requestcodeforgallery = 1
    val requestcondeforcamera = 2
    val camerapermissioncode = 3
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
        permissions()


    }

    private fun permissions() {

        ContextCompat.checkSelfPermission(requireActivity(),
            Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
            requireContext(),
            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED


    }

    private fun subscribeobserver() {
        viewmodel.userDataListLD.observe(viewLifecycleOwner) {
            dataentity = it
            binding?.nameedittext?.setText(it.username)
            binding?.emailedittext?.setText(it.useremail)
            binding?.phoneedittext?.setText(it.userphone)
            binding?.adressedittext?.setText(it.useraddress)
            if (it.imageuri != null)
                binding?.insertimage?.setImageURI(Uri.parse(it.imageuri))
            imagePath = it.imageuri

        }
    }

    private fun statusBar() {
        val window = activity?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun insertValue() {

        binding?.insertbtn?.setOnClickListener {
            val username = binding?.nameedittext?.text.toString()
            val useremail = binding?.emailedittext?.text.toString()
            val userphone = binding?.phoneedittext?.text.toString()
            val useraddress = binding?.adressedittext?.text.toString()
            if (username.isEmpty() || useremail.isEmpty() || userphone.isEmpty() || useraddress.isEmpty() || imagestring == null) {
                Toast.makeText(requireContext(), "Please put all fields", Toast.LENGTH_SHORT)
                    .show()
            } else {
                Timber.d("username $username")
                Timber.d("imageuri in insert ${imagestring}")
                viewmodel.profileData(username, useremail, imagestring, userphone, useraddress)

                Toast.makeText(requireContext(), "inserted successfully", Toast.LENGTH_SHORT)
                    .show()

            }
        }
        binding?.updatebtn?.setOnClickListener {
            val username = binding?.nameedittext?.text.toString()
            val useremail = binding?.emailedittext?.text.toString()
            val userphone = binding?.phoneedittext?.text.toString()
            val useraddress = binding?.adressedittext?.text.toString()

            imagePath?.let { it1 ->
                dataentity?.username = username
                dataentity?.useremail = useremail
                dataentity?.imageuri = it1
                dataentity?.userphone = userphone
                dataentity?.useraddress = useraddress
                Timber.d("image uri $it1")
                dataentity?.let { it11 -> viewmodel.updateuser(it11) }
            }
            Toast.makeText(requireContext(), "update successfully", Toast.LENGTH_SHORT).show()
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

            Timber.d("click on gallery")
            val galleryintetn = Intent()
            galleryintetn.type = "image/*"
            galleryintetn.setAction(Intent.ACTION_OPEN_DOCUMENT)
            startActivityForResult(galleryintetn, requestcodeforgallery)
        }

        val selectusingcamera = dialogview?.findViewById<TextView>(R.id.cameraselection)
        selectusingcamera?.setOnClickListener {
            Timber.d("click on camera")
            if (ContextCompat.checkSelfPermission(requireActivity(),
                    Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(
                    requireContext(),
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED
            ) {

                val opencamer = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(opencamer, requestcondeforcamera)


            } else {
                ActivityCompat.requestPermissions(requireActivity(),
                    arrayOf(Manifest.permission.CAMERA,
                        Manifest.permission.WRITE_EXTERNAL_STORAGE,
                        Manifest.permission.READ_EXTERNAL_STORAGE),
                    camerapermissioncode)
            }

        }

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray,
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == camerapermissioncode) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                val opencamer = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
                startActivityForResult(opencamer, requestcondeforcamera)
            } else {
                Toast.makeText(requireContext(), "permission denied", Toast.LENGTH_SHORT).show()
            }
        }
    }


    override fun onActivityResult(requestCode: Int, resultCode: Int, intentdata: Intent?) {
        if (resultCode != RESULT_CANCELED) {
            if (requestCode == requestcodeforgallery && resultCode == Activity.RESULT_OK && intentdata != null) {
                val imageuri = intentdata.data!!
                imagestring = intentdata.data.toString()
                Timber.d("image uri at insert ${imagestring}")
                binding?.insertimage?.setImageURI(imageuri)
                binding?.selectpicturetv?.isVisible = false
                Toast.makeText(requireContext(), "selection succesfully", Toast.LENGTH_SHORT)
                    .show()
                alertDialog?.dismiss()
            }
            if (requestCode == requestcondeforcamera && resultCode == Activity.RESULT_OK) {

                val imagebitmap = intentdata?.extras?.get("data") as Bitmap
                Timber.d("bitmap first $imagebitmap")
                binding?.insertimage?.setImageBitmap(imagebitmap)

                binding?.selectpicturetv?.isVisible = false
                Toast.makeText(requireContext(), "selection succesfully", Toast.LENGTH_SHORT)
                    .show()
                saveImage(imagebitmap)
                alertDialog?.dismiss()
            }
        }

    }

    private fun saveImage(finalBitmap: Bitmap) {

        try {
//            val hBitmap = (hActivityDownLoadImageBinding.hDisplayIV.drawable as BitmapDrawable)
//                .bitmap
            Toast.makeText(requireContext(), "try", Toast.LENGTH_SHORT).show()
            if (Build.VERSION.SDK_INT < Build.VERSION_CODES.Q) {
                Toast.makeText(requireContext(), "if", Toast.LENGTH_SHORT).show()
                val hFile = File(requireContext().getExternalFilesDir(null), "testimage.jpg")

                val hFileOutputStream: FileOutputStream?

                hFileOutputStream = FileOutputStream(hFile)

                finalBitmap.compress(Bitmap.CompressFormat.PNG, 100, hFileOutputStream)

                Toast.makeText(
                    requireContext(),
                    "Download successfully to  ${hFile.path}",
                    Toast.LENGTH_LONG
                ).show()

            } else {
                Toast.makeText(requireContext(), "else", Toast.LENGTH_SHORT).show()
                val hContentValues = ContentValues().apply {
                    put(MediaStore.Images.Media.DISPLAY_NAME, "imgMS.jpeg")
                    put(MediaStore.Images.Media.MIME_TYPE, "image/jpeg")
                    put(MediaStore.Images.Media.IS_PENDING, 1)
                }
                val hResolver = requireContext().contentResolver

                val hCollection = MediaStore.Images.Media
                    .getContentUri(MediaStore.VOLUME_EXTERNAL_PRIMARY)

                val hItem = hResolver.insert(hCollection, hContentValues)

                if (hItem != null) {
                    hResolver.openOutputStream(hItem).use { out ->
                        finalBitmap.compress(Bitmap.CompressFormat.JPEG, 90, out)
                    }
                    hContentValues.clear()
                    hContentValues.put(MediaStore.Images.Media.IS_PENDING, 0)
                    hResolver.update(hItem, hContentValues, null, null)

                    val path: String =
                        MediaStore.Images.Media.insertImage(requireContext().getContentResolver(),
                            finalBitmap,
                            "imgMS.jpeg",
                            null)
                    imagestring = path
                    Timber.d("ureww $path")
                    Toast.makeText(
                        requireContext(),
                        "Download successfully to ${hItem.path}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        } catch (e: Exception) {
            Timber.d("$e")
            Toast.makeText(
                requireContext(),
                "$e",
                Toast.LENGTH_LONG
            ).show()
        }


    }


}