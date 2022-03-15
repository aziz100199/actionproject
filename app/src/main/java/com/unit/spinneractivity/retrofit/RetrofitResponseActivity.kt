package com.unit.spinneractivity.retrofit

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.unit.spinneractivity.databinding.ActivityRetrofitResponseBinding
import com.unit.spinneractivity.retrofit.adapter.RetrofitAdapter
import com.unit.spinneractivity.retrofit.api.RetrofitInstance
import retrofit2.HttpException
import timber.log.Timber

class RetrofitResponseActivity : AppCompatActivity() {

    var binding: ActivityRetrofitResponseBinding? = null
    var recycler = RetrofitAdapter()


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityRetrofitResponseBinding.inflate(layoutInflater)
        setContentView(binding?.root)

        lifecycleScope.launchWhenCreated {
            binding?.progressbar?.isVisible = true

            val res =
                try {
                    Timber.d("try")
                    RetrofitInstance.retrofitinstance.getvalue()
                } catch (e: Exception) {

                    Toast.makeText(this@RetrofitResponseActivity, "$e", Toast.LENGTH_SHORT).show()
                    binding?.progressbar?.isVisible = false
                    Timber.d("first  toast")
                    return@launchWhenCreated
                } catch (ee: HttpException) {
                    Toast.makeText(this@RetrofitResponseActivity, "$ee", Toast.LENGTH_SHORT).show()
                    Timber.d("second")
                    return@launchWhenCreated
                }
            if (res.isSuccessful && res.body() != null) {
                Timber.d("SUCCESS ${res.body()!!.get(0).title}")
                recycler.set(res.body()!!)
                binding?.progressbar?.isVisible = false
            } else {
                Toast.makeText(this@RetrofitResponseActivity,
                    "response not found",
                    Toast.LENGTH_SHORT).show()

            }
        }
        binding?.recyclerview?.layoutManager = LinearLayoutManager(this)
        binding?.recyclerview?.adapter = recycler


    }
}