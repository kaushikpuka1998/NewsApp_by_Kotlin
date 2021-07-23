package com.kgstrivers.kgnews

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity(), NewsItemClicked {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       recyclerview.layoutManager = LinearLayoutManager(this)

        val items = fetchData()

        val adapter = NewsListadapter(items,this)

        recyclerview.adapter = adapter







    }


    private fun fetchData():ArrayList<String>{


        val list = ArrayList<String>();

        for(i in 0 until 50)
        {
            list.add("Item $i")
        }


        return list
    }

    override fun onItemClicked(item:String)
    {
        Toast.makeText(this,"Clicked $item",Toast.LENGTH_LONG).show()
    }


}