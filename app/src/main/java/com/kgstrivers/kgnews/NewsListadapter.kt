package com.kgstrivers.kgnews

import android.util.Log
import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.itemcompo.view.*

class NewsListadapter(private val listener:NewsItemClicked): RecyclerView.Adapter<NewsViewHolder>() {

    var  items : ArrayList<model> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcompo,parent,false)
        val viewH = NewsViewHolder(view)
       view.setOnClickListener{
           listener.onItemClicked(items[viewH.adapterPosition])
       }
        return viewH
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentItem = items[position].title
        val url:String = items[position].urlToImage
        val auth = "Author:"+items[position].author
        holder.titleview.text = currentItem
        holder.author.text = auth
        if(url!="null")
        {
            Log.v("Tapped:",url)
            Glide.with(holder.itemView.context).load(url).into(holder.imageview)
        }
        else
        {
            Glide.with(holder.itemView.context).load("https://bitsofco.de/content/images/2018/12/broken-1.png").into(holder.imageview)
        }



    }

    override fun getItemCount(): Int {
        //TODO("Not yet implemented")

        return items.size
    }

    fun updateAll(updatedItems : ArrayList<model>){

        items.clear()
        items.addAll(updatedItems)

        notifyDataSetChanged()
    }

    fun clearone(){

        items.clear()
        notifyDataSetChanged()
    }


}

private fun View.setOnClickListener(onItemClicked: Unit) {

}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
{


    val titleview: TextView = itemView.findViewById(R.id.Title)
    val imageview: ImageView = itemView.findViewById(R.id.imageview)
    val author:TextView = itemView.findViewById(R.id.author)
}

interface NewsItemClicked{
    fun onItemClicked(item: model)

}