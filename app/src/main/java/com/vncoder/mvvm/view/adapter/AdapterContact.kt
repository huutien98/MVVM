package com.vncoder.retrofit2_employee.Adapter

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.mvvm.databinding.ItemEmployeeBinding
import com.vncoder.mvvm.model.Contact


class AdapterContact() : RecyclerView.Adapter<AdapterContact.ViewHolder>() {
    private lateinit var onItemClickItem: onItemClick

    constructor(
        onItemClick: onItemClick
    ) : this() {
        this.onItemClickItem = onItemClick
    }

    class ViewHolder(private val contacBinding: ItemEmployeeBinding) :
        RecyclerView.ViewHolder(contacBinding.root) {
        fun onBind(contact: Contact, listener: onItemClick) {
            contacBinding.contact = contact
            itemView.setOnClickListener { listener.onItemClick(contact) }
            itemView.setOnLongClickListener() {
                listener.onLongItemClick(contact)
                return@setOnLongClickListener true
            }
        }
    }

    var contact: List<Contact> = listOf()
        set(value) {
            field = value
            notifyDataSetChanged()
        }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        val contacBinding = ItemEmployeeBinding.inflate(inflater, parent, false)
        return ViewHolder(contacBinding)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.onBind(contact[position], onItemClickItem)
    }

    override fun getItemCount(): Int = contact.size

    interface onItemClick {
        fun onItemClick(contact: Contact)
        fun onLongItemClick(contact: Contact)
    }


}