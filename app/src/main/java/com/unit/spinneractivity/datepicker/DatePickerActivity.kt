package com.unit.spinneractivity.datepicker

import android.annotation.SuppressLint
import android.app.DatePickerDialog
import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.activityViewModels
import com.unit.spinneractivity.databinding.ActivityDatePickerBinding
import java.text.SimpleDateFormat
import java.util.*

class DatePickerActivity : AppCompatActivity() {
    var binding: ActivityDatePickerBinding? = null
    var calendar: Calendar? = null
    var datepicker: DatePickerDialog.OnDateSetListener? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDatePickerBinding.inflate(layoutInflater)
        setContentView(binding?.root)


        datePickerDialog()
        clickListner()


    }

    @SuppressLint("SimpleDateFormat")
    private fun datePickerFormate(calender: Calendar) {

        val myformate1 = SimpleDateFormat("MMM-dd-yyyy")
        val myformate2 = SimpleDateFormat("dd-MM-yyyy")
        val myformate3 = SimpleDateFormat("yyy-MM-dd")
        val myformate4 = SimpleDateFormat("MM-EEEE-yyyy")
        val myformate5 = SimpleDateFormat("dd-MM-yyyy hh:mm:ss")
        val myformate6 = SimpleDateFormat("hh-mm-ss a")

        binding?.firstformatevalue?.text = myformate1.format(calender.time)
        binding?.secondformatevalue?.text = myformate2.format(calender.time)
        binding?.thirdformatevalue?.text = myformate3.format(calender.time)
        binding?.fourformatevalue?.text = myformate4.format(calender.time)
        binding?.fiveformatevalue?.text = myformate5.format(calender.time)
        binding?.sixformatevalue?.text = myformate6.format(calender.time)

    }

    private fun datePickerDialog() {
        calendar = Calendar.getInstance()
        datepicker = DatePickerDialog.OnDateSetListener { view, year, month, dayofmonth ->
            calendar?.set(Calendar.YEAR, year)
            calendar?.set(Calendar.MONTH, month)
            calendar?.set(Calendar.DAY_OF_MONTH, dayofmonth)
            datePickerFormate(calendar!!)

        }
    }

    private fun clickListner() {
        binding?.datepickerbtn?.setOnClickListener {
            DatePickerDialog(this,
                datepicker,
                calendar!!.get(Calendar.YEAR),
                calendar!!.get(Calendar.MONTH),
                calendar!!.get(Calendar.DAY_OF_MONTH)).show()
        }
    }
}