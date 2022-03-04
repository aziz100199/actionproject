package com.unit.spinneractivity.roomdatabase.fragments

import android.app.DatePickerDialog
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.R
import com.unit.spinneractivity.RoomDbActivity
import com.unit.spinneractivity.databinding.FragmentLoginSuccessBinding
import com.unit.spinneractivity.roomdatabase.adapter.RoomAdapter
import com.unit.spinneractivity.roomdatabase.room.entities.DataEntity
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel


class LoginSuccessFragment : Fragment() {

    var binding: FragmentLoginSuccessBinding? = null
    val roomadatapter = RoomAdapter()
    var checklistisEmpy: MutableList<DataEntity>? = null
    lateinit var actionmodecallback: ActionMode.Callback
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


        clicklistner()
        suscribeobserver()
        checkProfileInformation()
        actionbar()

        //itemTouchListner()
//        recycler()
//        datePickder()
//        actionmode()

    }

    private fun actionbar() {
        (requireContext() as RoomDbActivity)
        val window = activity?.window
        window?.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS)
        window?.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS)
        window?.statusBarColor = ContextCompat.getColor(requireContext(), R.color.white)
    }

    private fun checkProfileInformation() {

        binding?.snackbar?.let { viewmodel.checkUserProfile(it) }

    }

    private fun suscribeobserver() {
        viewmodel.userDataListLD.observe(viewLifecycleOwner) {
            if (!it.isEmpty()) {
                observeableData(it)
            }

//roomadatapter.datsset(it)
//checklistisEmpy?.addAll(it)


        }

    }

    private fun observeableData(list: List<DataEntity>) {
        if (list.get(0).username != null)
            binding?.extralayout?.usernamedisplay?.text = list.get(0).username
        if (list.get(0).useremail != null)
            binding?.extralayout?.emaildisplay?.text = list.get(0).useremail
        if (list.get(0).imageuri != null)
            binding?.extralayout?.profilepicture?.setImageURI(Uri.parse(list.get(0).imageuri))
    }


    private fun clicklistner() {
        binding?.logout?.setOnClickListener {
            viewmodel.logout()
            viewmodel.loadFragment(LoginRegisterFragment())

        }


//    private fun actionmode() {
//
//        actionmodecallback = object : ActionMode.Callback {
//            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
//                return true
//            }
//
//            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
//                mode?.menuInflater?.inflate(R.menu.actionmodemenu, menu)
//                mode?.setTitle("RoomActionMode")
//                return true
//            }
//
//            override fun onActionItemClicked(actionmode: ActionMode?, menu: MenuItem?): Boolean {
//                when (menu?.itemId) {
//                    R.id.delet -> Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT)
//                        .show()
//                }
//
//                return true
//            }
//
//            override fun onDestroyActionMode(p0: ActionMode?) {
//                Toast.makeText(requireContext(), "Destroy", Toast.LENGTH_SHORT).show()
//            }
//
//        }
//    }

//    private fun itemTouchListner() {
//
//        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
//            override fun onMove(
//                recyclerView: RecyclerView,
//                viewHolder: RecyclerView.ViewHolder,
//                target: RecyclerView.ViewHolder,
//            ): Boolean {
//                return false
//            }
//
//            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
//                val itemposition = viewHolder.adapterPosition
//                viewmodel.deletItemOnPositon(itemposition)
//            }
//
//        }).attachToRecyclerView(binding?.recycler)
//    }


//    private fun recycler() {
//        binding?.recycler?.layoutManager = LinearLayoutManager(requireContext())
//        binding?.recycler?.adapter = roomadatapter
//    }


//        binding?.actionmode?.setOnClickListener {
//            (requireContext() as RoomDbActivity).startSupportActionMode(actionmodecallback)
//
//        }
//
//        binding?.insert?.setOnClickListener {
//            val calendar = Calendar.getInstance()
//            DatePickerDialog(requireContext(),
//                dateInstance,
//                calendar.get(Calendar.YEAR),
//                calendar.get(Calendar.MONTH),
//                calendar.get(Calendar.DAY_OF_MONTH))
//                .show()
//
//        }
    }

//    fun datePickder() {
//        dateInstance = DatePickerDialog.OnDateSetListener { view, year, mont, dayofmont ->
//            viewmodel.onDatePicked(year, mont, dayofmont)
//        }
//    }


}