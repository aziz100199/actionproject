package com.unit.spinneractivity.actionmode

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.view.ActionMode
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.ItemTouchHelper
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.snackbar.Snackbar
import com.unit.spinneractivity.R
import com.unit.spinneractivity.actionmode.actionviewmodel.ActionVIewModel
import com.unit.spinneractivity.adapter.actionmodeAdapter
import com.unit.spinneractivity.databinding.ActivityActionModeBinding

class ActionModeActivity : AppCompatActivity(), actionmodeAdapter.AdapterCallBack {
    var binding: ActivityActionModeBinding? = null
    var actionModeCallBack: ActionMode.Callback? = null
    var posList = ArrayList<Int>()
    var actionmode: ActionMode? = null
    var adaapter: actionmodeAdapter? = null
    val actionViewModel by viewModels<ActionVIewModel>()
    var itemm = mutableListOf<String>()
    var listname = mutableListOf<String>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityActionModeBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        iTemTouchDelete()
        actionMode()
        clicklistner()
        actionViewModel.init()
//        recyclerlist()

    }


    private fun clicklistner() {
        actionViewModel.liveData.observe(this, Observer {

            binding?.actionmoderecycler?.layoutManager = LinearLayoutManager(this)
            adaapter = actionmodeAdapter(this, this)
            binding?.actionmoderecycler?.adapter = adaapter
            adaapter?.dataset(it)

        })

    }


    private fun iTemTouchDelete() {


        ItemTouchHelper(object : ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
            override fun onMove(
                recyclerView: RecyclerView,
                viewHolder: RecyclerView.ViewHolder,
                target: RecyclerView.ViewHolder,
            ): Boolean {
                return false
            }

            override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
                val position = viewHolder.adapterPosition
                val currentItem = actionViewModel.listname[viewHolder.adapterPosition]
                actionViewModel.swapremoveItem(currentItem)

//                val position = viewHolder.adapterPosition
//                listname.removeAt(viewHolder.adapterPosition)

                adaapter!!.notifyItemRemoved(viewHolder.adapterPosition)

                Snackbar.make(binding?.actionmoderecycler!!,
                    currentItem,
                    Snackbar.LENGTH_INDEFINITE)
                    .setAction("Undo") {
                        actionViewModel.listname.add(position, currentItem)
                        adaapter!!.notifyItemInserted(position)
                    }.setBackgroundTint(ContextCompat.getColor(this@ActionModeActivity,
                        R.color.black))
                    .setActionTextColor(ContextCompat.getColor(this@ActionModeActivity,
                        R.color.white)).show()
            }

        }).attachToRecyclerView(binding?.actionmoderecycler)


    }


    private fun actionMode() {
        actionModeCallBack = object : ActionMode.Callback {
            override fun onCreateActionMode(p0: ActionMode?, p1: Menu?): Boolean {
                Toast.makeText(this@ActionModeActivity, "created", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onPrepareActionMode(mode: ActionMode?, menu: Menu?): Boolean {
                actionmode = mode
                mode?.menuInflater?.inflate(R.menu.actionmodemenu, menu)
                mode?.title = "selected item"
                return true
            }

            @SuppressLint("NotifyDataSetChanged")
            override fun onActionItemClicked(mode: ActionMode?, menu: MenuItem?): Boolean {
                when (menu?.itemId) {
                    R.id.delet ->
                        actionViewModel.removeItem(itemm)
                }
                adaapter?.selecteditemlist?.clear()
                mode?.finish()
                adaapter?.notifyDataSetChanged()
//                Timber.d("remove ${itemm}}")
                Toast.makeText(this@ActionModeActivity, "deleted", Toast.LENGTH_SHORT).show()
                return true
            }

            override fun onDestroyActionMode(mode: ActionMode?) {
                Toast.makeText(this@ActionModeActivity, "distroy", Toast.LENGTH_SHORT).show()
                adaapter?.selecteditemlist?.clear()
            }

        }

    }

    private fun recyclerlist() {
        listname.add("first")
        listname.add("second")
        listname.add("third")
        listname.add("four")
        listname.add("five")
        listname.add("six")
        listname.add("seven")
        listname.add("eight")
        listname.add("nine")
        listname.add("ten")
        listname.add("eleven")
        listname.add("twelve")
        listname.add("thirteen")
        listname.add("fourteen")
        listname.add("fifteen")

//        listname.add("sixteen")
//        listname.add("seventeen")
//        listname.add("eighteen")
//        listname.add("nineteen")
//        listname.add("twenty")

    }


    override fun onItemClick(selectedfilescount: Int, selectedItem: String) {
        actionmode?.title = "selected item $selectedfilescount"
        itemm.add(selectedItem)
        if (selectedfilescount == 0) {
            actionmode?.finish()
        }
    }

    override fun onItemLongClick(selectedfilescount: Int, selectedItem: String) {
        startSupportActionMode(actionModeCallBack!!)
        actionmode?.title = "selected item $selectedfilescount"
        itemm.add(selectedItem)
    }
}