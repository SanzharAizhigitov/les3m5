package com.geektech.les3m5

import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.RecyclerView
import com.geektech.les3m5.databinding.ActivityMainBinding
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MainActivity : AppCompatActivity() {
    lateinit var binding: ActivityMainBinding
    var adapter = PixaAdapter(arrayListOf())
    var page: Int = 1
    var images = arrayListOf<Hit>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        initClickers()

    }

    private fun initClickers() {
        with(binding) {
            imageRecycler.addOnScrollListener(object : RecyclerView.OnScrollListener() {
                override fun onScrollStateChanged(recyclerView: RecyclerView, newState: Int) {
                    super.onScrollStateChanged(recyclerView, newState)
                    if (!recyclerView.canScrollVertically(1)) {
                        ++page
                        moreRequest()
                    }
                }
            })
            btnMore.setOnClickListener {
                ++page
                moreRequest()
            }
            btnEnter.setOnClickListener {
                page = 1
                doRequest()
            }
        }
    }

    private fun ActivityMainBinding.doRequest() {
        PixaService().api.getImages(
            pictureWord = etSearch.text.toString(), page = page
        ).enqueue(object : Callback<PixaModel> {
            override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                if (response.isSuccessful) {
                    adapter = PixaAdapter(response.body()?.hits!!)
                    imageRecycler.adapter = adapter
                    images = response.body()?.hits!!
                }
            }

            override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                Log.e("xvr", t.message.toString())
            }

        })
    }

    fun ActivityMainBinding.moreRequest() {
        PixaService().api.getImages(
            pictureWord = etSearch.text.toString(), page = page
        ).enqueue(object : Callback<PixaModel> {
            override fun onResponse(call: Call<PixaModel>, response: Response<PixaModel>) {
                if (response.isSuccessful) {
                    var moreImages = response.body()?.hits!!
                    images.addAll(moreImages)
                    adapter = PixaAdapter(images)
                    imageRecycler.adapter = adapter
                }
            }

            override fun onFailure(call: Call<PixaModel>, t: Throwable) {
                Log.e("xvr", t.message.toString())
            }

        })
    }

}