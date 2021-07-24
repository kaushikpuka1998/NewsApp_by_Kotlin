package com.kgstrivers.kgnews

import android.annotation.SuppressLint
import android.app.ActionBar
import android.net.Uri
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Gravity.*
import android.widget.TextView
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.view.GravityCompat.apply
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.volley.AuthFailureError
import com.android.volley.Request
import com.android.volley.Response
import com.android.volley.toolbox.JsonObjectRequest
import com.android.volley.toolbox.Volley
import kotlinx.android.synthetic.main.activity_main.*
import kotlin.math.log

class MainActivity : AppCompatActivity(), NewsItemClicked {


    private lateinit var mAdapter:NewsListadapter

    lateinit var newsurl:String
    //@RequiresApi(Build.VERSION_CODES.LOLLIPOP)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerview.layoutManager = LinearLayoutManager(this)



        newsurl = "https://newsapi.org/v2/top-headlines?country=in&apiKey=ca890a69b1c8490484c83c844701261b"
        CustomfetchData(newsurl)

        mAdapter = NewsListadapter(this)

        recyclerview.adapter = mAdapter

        refresh()


        floatingActionButton1.setOnClickListener {


            newsurl = "https://newsapi.org/v2/everything?q=bollywood&from=2021-06-24&sortBy=publishedAt&apiKey=ca890a69b1c8490484c83c844701261b"
            CustomfetchData(newsurl)

        }
        floatingActionButton2.setOnClickListener {

            newsurl = "https://newsapi.org/v2/everything?q=indiasports&sortBy=publishedAt&apiKey=ca890a69b1c8490484c83c844701261b"
            CustomfetchData(newsurl)
        }
        floatingActionButton3.setOnClickListener {

            newsurl = "https://newsapi.org/v2/everything?q=technology&sortBy=publishedAt&apiKey=ca890a69b1c8490484c83c844701261b"
            CustomfetchData(newsurl)
        }

        floatingActionButton3.setOnClickListener {

            newsurl = "https://newsapi.org/v2/everything?q=technology&sortBy=publishedAt&apiKey=ca890a69b1c8490484c83c844701261b"
            CustomfetchData(newsurl)
        }
        floatingActionButton4.setOnClickListener {

            newsurl = "https://newsapi.org/v2/everything?q=corona-virus&sortBy=publishedAt&apiKey=ca890a69b1c8490484c83c844701261b"
            CustomfetchData(newsurl)
        }








    }

    private fun CustomfetchData(newsurl:String){
        val queue = Volley.newRequestQueue(this)
        val url = newsurl

        val jsonObjectRequest:JsonObjectRequest =object : JsonObjectRequest(
            Request.Method.GET, url, null,
            Response.Listener {
                Log.e("sdsadas", "$it")
                val newsJsonArray = it.getJSONArray("articles")
                val newsArray = ArrayList<model>()
                for(i in 0 until  newsJsonArray.length()){
                    val newsJsonObject = newsJsonArray.getJSONObject(i)
                    val news = model(
                        newsJsonObject.getString("author"),
                        newsJsonObject.getString("title"),
                        newsJsonObject.getString("url"),
                        newsJsonObject.getString("urlToImage")
                    )
                    //Toast.makeText(this,newsJsonObject.getString("urlToImage"),Toast.LENGTH_LONG).show()
                    newsArray.add(news)
                }

                mAdapter.updateAll(newsArray)



            },
            Response.ErrorListener { error ->
                // TODO: Handle error

                Log.v("Error",error.toString())
                Toast.makeText(this,"Something Went Wrong",Toast.LENGTH_SHORT).show()
            }
        ){
            @Throws(AuthFailureError::class)
            override fun getHeaders(): Map<String, String> {
                val params: MutableMap<String, String> = HashMap()
                params["User-Agent"] = "Mozilla/5.0"
                return params
            }
        }

        queue.add(jsonObjectRequest)

// Access the RequestQueue through your singleton class.







    }

    override fun onItemClicked(item: model)
    {
        val builder = CustomTabsIntent.Builder()
        val  customTabsIntent = builder.build()
        customTabsIntent.launchUrl(this, Uri.parse(item.url))

    }

    private fun refresh()
    {
        swipeContainer.setOnRefreshListener{
            mAdapter.clearone()
            CustomfetchData(newsurl)
            Toast.makeText(this, "Page Refreshing...", Toast.LENGTH_SHORT).show()
            swipeContainer.isRefreshing = false
        }
    }



}