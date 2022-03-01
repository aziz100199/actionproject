package com.unit.spinneractivity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.unit.spinneractivity.actionmode.ActionModeActivity
import com.unit.spinneractivity.colepsingtoolbar.CollapsingToolBarActivity
import com.unit.spinneractivity.databinding.ActivityLauncherBinding
import com.unit.spinneractivity.spineractivity.SpinnerActivity

class LauncherActivity : AppCompatActivity() {
    var binding: ActivityLauncherBinding? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityLauncherBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        clicklistner()

    }

    private fun clicklistner() {
        binding?.actionmodebtn?.setOnClickListener {
            activitylauncher(ActionModeActivity())
        }
        binding?.spinnerbutton?.setOnClickListener {
            activitylauncher(SpinnerActivity())
        }
        binding?.collapsingtoolbar?.setOnClickListener {
            activitylauncher(CollapsingToolBarActivity())
        }
        binding?.datepicker?.setOnClickListener {

            activitylauncher(DatePickerActivity())

        }
        binding?.roomdatabase?.setOnClickListener {

            activitylauncher(RoomDbActivity())

        }
    }

    private fun activitylauncher(activity: Activity) {
        startActivity(Intent(this, activity::class.java))
    }
}