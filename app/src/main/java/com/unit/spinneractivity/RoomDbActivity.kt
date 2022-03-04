package com.unit.spinneractivity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.unit.spinneractivity.databinding.ActivityRoomDataBaseBinding
import com.unit.spinneractivity.roomdatabase.viewmodel.RoomViewModel
import timber.log.Timber

class RoomDbActivity : AppCompatActivity() {
    val viewmodel by viewModels<RoomViewModel>()
    private var binding: ActivityRoomDataBaseBinding? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRoomDataBaseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

//            viewmodel.loadFragment(LoginRegisterFragment())
        viewmodel.load()
        viewmodel.fragmentLD.observe(this) {
            supportFragmentManager.beginTransaction()
                .replace(R.id.fragmentcontainer, it).commit()
            Timber.d("fragment")
        }

    }

}