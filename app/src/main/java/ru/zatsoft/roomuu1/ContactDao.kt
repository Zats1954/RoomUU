package ru.zatsoft.roomuu1

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface ContactDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(contact: Contact)

    @Delete
    suspend fun delete(contact: Contact)

    @Query("SELECT * FROM myContacts ORDER BY id ASC")
    fun getAllContacts(): LiveData<List<Contact>>

    @Query("DELETE FROM myContacts")
    fun deleteAll()
}