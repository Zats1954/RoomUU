package ru.zatsoft.roomuu1

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM myContacts ORDER BY id ASC")
    fun getAllContacts(): List<Contact>

    @Query("DELETE FROM myContacts")
    fun deleteAll()
}