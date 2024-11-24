package ru.zatsoft.roomuu1

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class ContactViewModel(application:Application): AndroidViewModel(application) {
    private val repository: ContactRepository
    val contactes: LiveData<List<Contact>>

    init{
        val dao = ContactDatabase.getDatabase(application).getContactDao()
        repository = ContactRepository(dao)
        contactes = repository.contactes
    }

    fun deleteContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.delete(contact)
    }

    fun insertContact(contact: Contact) = viewModelScope.launch(Dispatchers.IO) {
        repository.insert(contact)
    }
}