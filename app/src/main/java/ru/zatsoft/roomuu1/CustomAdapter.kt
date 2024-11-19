package ru.zatsoft.roomuu1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class CustomAdapter(private val contacts:MutableList<Contacts>):
    RecyclerView.Adapter<CustomAdapter.ContactsViewHolder>() {

        private var onContactClickListener: OnContactClickListener? =null

        interface OnContactClickListener{
            fun onContactClick(contacts: Contacts, position: Int)
        }


    class ContactsViewHolder(itemView: View): RecyclerView.ViewHolder(itemView){
        val phone: TextView = itemView.findViewById(R.id.item_phone)
        val title: TextView = itemView.findViewById(R.id.item_name)

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactsViewHolder {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.list_item, parent, false)

        return ContactsViewHolder(itemView)
    }

    override fun getItemCount() = contacts.size

    override fun onBindViewHolder(holder: ContactsViewHolder, position: Int) {
        val contact = contacts[position]
        holder.phone.text =contact.phone
        holder.title.text = contact.name
        holder.itemView.setOnClickListener {
            if(onContactClickListener != null){
                onContactClickListener!!.onContactClick(contact,position)
            }
        }
    }

    fun setOnContactsClickListener(onContactClickListener: OnContactClickListener){
        this.onContactClickListener = onContactClickListener
    }
}