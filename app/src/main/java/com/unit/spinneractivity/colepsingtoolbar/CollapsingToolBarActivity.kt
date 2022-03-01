package com.unit.spinneractivity.colepsingtoolbar

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.ArrayAdapter
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unit.spinneractivity.databinding.ActivityCollapsingToolBarActivituBinding
import timber.log.Timber
import java.text.SimpleDateFormat
import java.util.*

class CollapsingToolBarActivity : AppCompatActivity() {
    var binding: ActivityCollapsingToolBarActivituBinding? = null
    val listOFDateAndTime = mutableListOf<String>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityCollapsingToolBarActivituBinding.inflate(layoutInflater)
        setContentView(binding?.root)
        toolbar()
        getDataAndTime()
        listOfTimeFormate()
        spinner()

    }


    private fun spinner() {

        val arratadapter =
            ArrayAdapter(this, android.R.layout.simple_spinner_dropdown_item, listOFDateAndTime)

        binding?.dateandtimespiner?.adapter = arratadapter

        binding?.dateandtimespiner?.onItemSelectedListener = object :
            AdapterView.OnItemSelectedListener {

            @SuppressLint("SimpleDateFormat")
            override fun onItemSelected(
                view: AdapterView<*>?,
                view1: View?,
                position: Int,
                p3: Long,
            ) {

                when (position) {

                    0 -> timeZone("GMT+1:00")
                    1 -> timeZone("GMT+2:00")
                    2 -> timeZone("GMT+3:00")
                    3 -> timeZone("GMT+5:00")
                    4 -> timeZone("GMT+5:30")
                    5 -> timeZone("GMT+6:00")
                    6 -> timeZone("GMT+7:00")
                    7 -> timeZone("GMT+8:00")
                    8 -> timeZone("GMT+9:00")
                    9 -> timeZone("GMT+9:30")


                }

            }

            override fun onNothingSelected(p0: AdapterView<*>?) {
                TODO("Not yet implemented")
            }

        }
    }

    private fun listOfTimeFormate() {

        listOFDateAndTime.add("European Central Time (GMT+1:00)")
        listOFDateAndTime.add("Eastern European Time (GMT+2:00)")
        listOFDateAndTime.add("Eastern African Time (GMT+3:00)")
        listOFDateAndTime.add("Pakistan Lahore Time (GMT+5:00)")
        listOFDateAndTime.add("India Standard Time (GMT+5:30)")
        listOFDateAndTime.add("Bangladesh Standard Time (GMT+6:00)")
        listOFDateAndTime.add("Vietnam Standard Time (GMT+7:00)")
        listOFDateAndTime.add("China Taiwan Time (GMT+8:00)")
        listOFDateAndTime.add("Japan Standard Time (GMT+9:00)")
        listOFDateAndTime.add("Australia Central Time (GMT+9:30)")


    }

    private fun getDataAndTime() {
        val data = Calendar.getInstance().time

        Toast.makeText(this, "current time $data", Toast.LENGTH_LONG).show()
    }

    private fun toolbar() {
        setSupportActionBar(binding?.toolbar)
        supportActionBar?.setDisplayShowHomeEnabled(true)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }

    @SuppressLint("SimpleDateFormat")
    fun timeZone(timezoe: String) {

        TimeZone.setDefault(TimeZone.getTimeZone(timezoe))
        val datatimeformate = SimpleDateFormat("dd-MM-yyyy EEEE hh:mm:ss:a")
        val firstformate = SimpleDateFormat("MM-dd-yyyy")
        val secondformate = SimpleDateFormat("dd-MM-yyyy")
        val thirdformate = SimpleDateFormat("yyy-MM-dd")
        val fourformate = SimpleDateFormat("MM-EEEE-yyyy")
        val fiveformate = SimpleDateFormat("dd-MM-yyyy hh:mm:ss:a")
        val sixformate = SimpleDateFormat("MM-EEEE-yyyy")
        val date = Date()
        val datefor = datatimeformate.format(date)
        val firstresformatevalue = firstformate.format(date)
        val secondformatevalue = secondformate.format(date)
        val thirdformatevalue = thirdformate.format(date)
        val fourformatevalue = fourformate.format(date)
        val fiveformatevalue = fiveformate.format(date)
        val sixformatevalue = sixformate.format(date)
        Timber.d("date secondformation,$datefor")
        binding?.dateandtimeview?.text = datefor
        binding?.firstformatevalue?.text = firstresformatevalue
        binding?.secondformatevalue?.text = secondformatevalue
        binding?.thirdformatevalue?.text = thirdformatevalue
        binding?.fourformatevalue?.text = fourformatevalue
        binding?.fiveformatevalue?.text = fiveformatevalue
        binding?.sixformatevalue?.text = sixformatevalue

    }

}