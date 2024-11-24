package ru.zatsoft.roomuu1

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView


class ContactAdapter(private val context: Context, private val listener: ContactClickListener) :
    RecyclerView.Adapter<ContactAdapter.ContactViewHolder>() {
    val contacts = ArrayList<Contact>()

    fun updateList(newList: List<Contact>) {
        contacts.clear()
        contacts.addAll(newList)
        notifyDataSetChanged()
    }

    inner class ContactViewHolder(itemView: View) :
        RecyclerView.ViewHolder(itemView) {
        private val itemName: TextView = itemView.findViewById(R.id.item_name)
        private val itemPhone: TextView = itemView.findViewById(R.id.item_phone)
        private val itemTime: TextView = itemView.findViewById(R.id.item_time)
        val itemIconDelete: ImageView = itemView.findViewById(R.id.item_icon)

        fun bind(contact: Contact) {
            itemName.text = contact.nameDB
            itemPhone.text = contact.phoneDB
            itemTime.text = contact.timeDB
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContactViewHolder {
        val viewHolder =
            ContactViewHolder(
                LayoutInflater.from(context).inflate(R.layout.list_item1, parent, false)
            )
        viewHolder.itemIconDelete.setOnClickListener {
            listener.onItemClicked(contacts[viewHolder.adapterPosition])
        }
        return viewHolder
    }

    override fun getItemCount(): Int {
        return contacts.size
    }

    override fun onBindViewHolder(holder: ContactViewHolder, position: Int) {
        val curentContact = contacts[position]
        holder.bind(curentContact)
    }

    interface ContactClickListener {
        fun onItemClicked(contact: Contact)
    }
}