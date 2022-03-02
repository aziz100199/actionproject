package com.unit.spinneractivity.roomdatabase.fragments

import android.app.DatePickerDialog
import android.os.Bundle
import android.view.*
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.unit.spinneractivity.R
import com.unit.spinneractivity.databinding.FragmentLoginSuccessBinding
import com.unit.spinneractivity.roomdatabase.adapter.RoomAdapter
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel
import java.util.*


class LoginSuccessFragment : Fragment() {

    var binding: FragmentLoginSuccessBinding? = null
    val roomadatapter = RoomAdapter()
    var actionmodecallback: ActionMode.Callback? = null
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

        itemTouchListner()
        recycler()
        datePickder()
        clicklistner()
        suscribeobserver()
        actionmode()

    }

    private fun actionmode() {

        actionmodecallback = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                mode?.menuInflater?.inflate(R.menu.actionmodemenu, menu)
                mode?.setTitle("RoomActionMode")
                return true
            }

            override fun onActionItemClicked(actionmode: ActionMode?, menu: MenuItem?): Boolean {
                when (menu?.itemId) {
                    R.id.delet -> Toast.makeText(requireContext(), "deleted", Toast.LENGTH_SHORT)
                        .show()
                }

                return true
            }

            override fun onDestroyActionMode(p0: ActionMode?) {
                Toast.makeText(requireContext(), "Destroy", Toast.LENGTH_SHORT).show()
            }

        }
    }

    private fun itemTouchListner() {

        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val itemposition = viewHolder.adapterPosition
                viewmodel.deletItemOnPositon(itemposition)
            }

        }).attachToRecyclerView(binding?.recycler)
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
            viewmodel.logout()
            viewmodel.loadFragment(LoginRegisterFragment())

        }
        binding?.actionmode?.setOnClickListener {


        }


        binding?.insert?.setOnClickListener {
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