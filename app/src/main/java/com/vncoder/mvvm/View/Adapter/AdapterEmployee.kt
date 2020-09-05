package com.vncoder.retrofit2_employee.Adapter

import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.mvvm.R
import com.vncoder.retrofit2_employee.Model.Contact
import kotlin.collections.ArrayList

class AdapterEmployee() : RecyclerView.Adapter<AdapterEmployee.ViewHolder>() {

    private lateinit var onItemClickListener: OnItemClickListener
    lateinit var exampleList: ArrayList<Contact>

    constructor(
        onItemClickListener: OnItemClickListener,
        exampleList: ArrayList<Contact>
    ) : this() {
        this.onItemClickListener = onItemClickListener
        this.exampleList = exampleList
        setHasStableIds(true)
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Email = itemView.findViewById<TextView>(R.id.Email)
        val Name = itemView.findViewById<TextView>(R.id.Name)
        val FirstName = itemView.findViewById<TextView>(R.id.FirstName)
        val LastName = itemView.findViewById<TextView>(R.id.LastName)
        val img_avatar = itemView.findViewById<ImageView>(R.id.image_avatar)

        fun onBindView(contact: Contact, listenner: OnItemClickListener) {
            itemView.setOnClickListener { listenner.onClickEmployee(contact) }
            itemView.setOnLongClickListener {
                listenner.onLongClickEmployee(contact)
                return@setOnLongClickListener true
            }
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exampleList!!.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val contact = exampleList!![position]

        holder.Email.text = contact.Email.toString()
        holder.Name.text = contact.Name.toString()
        holder.FirstName.text = contact.FirstName.toString()
        holder.LastName.text = contact.LastName.toString()

        holder.img_avatar.setImageURI(Uri.parse(exampleList[position].custom_fields?.get(0)?.value))

        holder.onBindView(exampleList!![position], onItemClickListener)
    }

    interface OnItemClickListener {
        fun onClickEmployee(contact: Contact)
        fun onLongClickEmployee(contact: Contact)
    }

    fun setList(ListStaff: ArrayList<Contact>) {
        this.exampleList = ListStaff
        notifyDataSetChanged()
    }

}