package com.kgstrivers.kgnews

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Toast
import androidx.browser.customtabs.CustomTabsIntent
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


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

       recyclerview.layoutManager = LinearLayoutManager(this)



        fetchData()

        mAdapter = NewsListadapter(this)

        recyclerview.adapter = mAdapter

        refresh()








    }


    private fun fetchData(){
        val queue = Volley.newRequestQueue(this)
        val url = "https://newsapi.org/v2/top-headlines?country=in&apiKey=ca890a69b1c8490484c83c844701261b"

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
            fetchData()
            Toast.makeText(this, "Page Refreshing...", Toast.LENGTH_SHORT).show()
            swipeContainer.isRefreshing = false
        }
    }



}