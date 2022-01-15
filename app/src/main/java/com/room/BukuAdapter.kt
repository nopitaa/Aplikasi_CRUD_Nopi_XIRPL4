package com.room

import android.provider.ContactsContract
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.room.Room.Buku
import com.room.Room.Buku.*


import kotlinx.android.synthetic.main.list_buku.view.*

class BukuAdapter (var buku: ArrayList<Buku>, var listener: OnAdapterListener) :
    RecyclerView.Adapter<BukuAdapter.BukuViewHolder>(){



    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BukuViewHolder {
        return BukuViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.list_buku, parent, false)
        )
    }
    override fun onBindViewHolder(holder: BukuViewHolder, position: Int) {
        val buku = buku[position]
        holder.view.text_title.text = buku.title
        holder.view.text_title.setOnClickListener {
            listener.onClick(buku)
        }
        holder.view.icon_edit.setOnClickListener {
            listener.onUpdate(buku)
        }
        holder.view.icon_delete.setOnClickListener {
            listener.onDelete(buku)
        }
    }
    override fun getItemCount() = buku.size

    class BukuViewHolder(val view: View) : RecyclerView.ViewHolder(view)

    fun setData(list: List<Buku> ){
        buku.clear()
        buku.addAll(list)
    }
    interface OnAdapterListener {
        fun onClick(buku: Buku)
        fun onUpdate(buku: Buku)
        fun onDelete(buku: Buku)
    }
}