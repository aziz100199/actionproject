package com.unit.spinneractivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.unit.spinneractivity.databinding.ActivityRoomDataBaseBinding
import com.unit.spinneractivity.roomdatabase.fragments.LoginRegisterFragment
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel

class RoomDbActivity : AppCompatActivity() {
    val viewmodel by viewModels<RoomViewModel>()
    private var binding: ActivityRoomDataBaseBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDataBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        viewmodel.loadFragment(LoginRegisterFragment())
        viewmodel.fragmentLD.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentcontainer, it).commit()
        }

    }

}