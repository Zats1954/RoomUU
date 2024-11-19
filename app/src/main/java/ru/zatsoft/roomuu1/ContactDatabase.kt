package ru.zatsoft.roomuu1

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

@Database(entities = [Contact::class], version = 1, exportSchema = false)
abstract class ContactDatabase: RoomDatabase() {
    abstract fun getContactDao(): ContactDao
    companion object{
        private var INSTANCE: ContactDatabase? = null
        fun getDatabase(context: Context) : ContactDatabase{
            return INSTANCE ?: synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    ContactDatabase::class.java,
                    "myContacts"
                ).build()
                INSTANCE = instance
                instance
            }
        }
    }
}