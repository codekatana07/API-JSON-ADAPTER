package com.example.test1

import android.os.Bundle
import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.create

class MainActivity : AppCompatActivity() {
    lateinit var recyclerView: RecyclerView
    lateinit var myAdapter: MyAdapter
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView = findViewById(R.id.recyclerView)


        val retrofitBuilder = Retrofit.Builder()
            .baseUrl("https://dummyjson.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(ApiInterface::class.java)

        val retrofitData = retrofitBuilder.getProductData()

        retrofitData.enqueue(object : Callback<DummyJsonProduct?> {
            override fun onResponse(p0: Call<DummyJsonProduct?>, p1: Response<DummyJsonProduct?>) {
                //on success use data
                var responseBody = p1.body()
                val productList = responseBody?.products!!

                myAdapter = MyAdapter(this@MainActivity, productList)
                recyclerView.adapter = myAdapter
                recyclerView.layoutManager = LinearLayoutManager(this@MainActivity)

//                val collectDataInSB = StringBuilder()
//                    for(myData in productList){
//                    collectDataInSB.append(myData.title + " ")
//                    }
//                val tv = findViewById<TextView>(R.id.text1)
//                tv.text = collectDataInSB
            }

            override fun onFailure(p0: Call<DummyJsonProduct?>, p1: Throwable) {
                // if api fail
                Log.d("Main Activity", "onFailure: " + p1.message)
            }
        })



    }
}