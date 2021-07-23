package com.kgstrivers.kgnews

import android.view.LayoutInflater
import android.view.OrientationEventListener
import android.view.View
import android.view.ViewGroup
import android.widget.Adapter
import android.widget.TextView
import androidx.appcompat.view.menu.ActionMenuItemView
import androidx.recyclerview.widget.RecyclerView

class NewsListadapter(): RecyclerView.Adapter<NewsViewHolder>() {

    var  items : ArrayList<model> = ArrayList()
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NewsViewHolder {

        val view = LayoutInflater.from(parent.context).inflate(R.layout.itemcompo,parent,false)
        val viewH = NewsViewHolder(view)
       /*view.setOnClickListener{
           listener.onItemClicked(items[viewH.adapterPosition])
       }*/
        return viewH
    }

    override fun onBindViewHolder(holder: NewsViewHolder, position: Int) {

        val currentItem = items[position]
        holder.titleview.text = currentItem.toString()


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


}

private fun View.setOnClickListener(onItemClicked: Unit) {

}

class NewsViewHolder(itemView: View):RecyclerView.ViewHolder(itemView)
{


    val titleview: TextView = itemView.findViewById(R.id.Title)
}

interface NewsItemClicked{
    fun onItemClicked(item: model)
}