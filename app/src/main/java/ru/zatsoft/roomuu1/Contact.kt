package ru.zatsoft.roomuu1

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "myContacts")
data class Contact (
    @ColumnInfo(name = "name")
    var nameDB: String,
    @ColumnInfo(name = "phone")
    var phoneDB: String
){
    @PrimaryKey(autoGenerate = true)
    var id = 0}