package com.vncoder.retrofit2_employee.Adapter

import android.content.Context
import android.net.Uri
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.RecyclerView
import com.vncoder.mvvm.R
import com.vncoder.mvvm.model.Contact

class AdapterEmployee(
    private val context: Context,
    private val onClick:(Contact) ->Unit,
    private val onLongClick:(Contact) ->Unit
) : RecyclerView.Adapter<AdapterEmployee.ViewHolder>() {

    var exampleList: List<Contact> = listOf()

    inner class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val Email = itemView.findViewById<TextView>(R.id.Email)
        val Name = itemView.findViewById<TextView>(R.id.Name)
        val FirstName = itemView.findViewById<TextView>(R.id.FirstName)
        val LastName = itemView.findViewById<TextView>(R.id.LastName)
        val img_avatar = itemView.findViewById<ImageView>(R.id.image_avatar)
        val layout_item :ConstraintLayout = itemView.findViewById(R.id.layout_item)

        fun onBind(contact: Contact) {
            Email.text = contact.Email.toString()
            Name.text = contact.Name.toString()
            FirstName.text = contact.FirstName.toString()
            LastName.text = contact.LastName.toString()
            img_avatar.setImageURI(Uri.parse(exampleList[adapterPosition].custom_fields?.get(0)?.value))
            layout_item.setOnClickListener {onClick(contact) }
            layout_item.setOnLongClickListener(View.OnLongClickListener {
                onLongClick(contact)
                true
            })
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val itemView =
            LayoutInflater.from(parent.context).inflate(R.layout.item_employee, parent, false)
        return ViewHolder(itemView)
    }

    override fun getItemCount(): Int {
        return exampleList.size
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
       holder.onBind(exampleList[position])
    }

    fun setList(notes:ArrayList<Contact>){
        this.exampleList = notes
        notifyDataSetChanged()
    }

}