package com.unit.spinneractivity.practiceactivity

import android.os.Bundle
import android.widget.Button
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.unit.spinneractivity.databinding.ActivityPracticeBinding

class PracticeActivity : AppCompatActivity() {
    lateinit var binding: ActivityPracticeBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityPracticeBinding.inflate(layoutInflater)
        setContentView(binding.root)


        binding.apply {
            clicklistner(firstbtn)
            clicklistner(secondbtn)
            clicklistner(thirdbtn)
            clicklistner(fourbtn)
        }




    }





    //////////////////////    click listner  //////////////////////////////



    private fun clicklistner(btn: Button) {
        btn.setOnClickListener {
            binding.apply {
                when (btn) {
                    firstbtn  -> {
                        Toast.makeText(this@PracticeActivity,
                            "click on first btn",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    secondbtn -> {
                        Toast.makeText(this@PracticeActivity,
                            "click on second btn",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    thirdbtn -> {
                        Toast.makeText(this@PracticeActivity,
                            "click on third btn",
                            Toast.LENGTH_SHORT)
                            .show()
                    }
                    fourbtn -> {
                        Toast.makeText(this@PracticeActivity,
                            "click on four btn",
                            Toast.LENGTH_SHORT)
                            .show()
                    }


                }
            }

        }
    }

    //////////////////////////// click listner endt //////////////////////




}