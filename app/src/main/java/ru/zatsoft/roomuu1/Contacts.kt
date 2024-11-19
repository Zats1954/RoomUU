package ru.zatsoft.roomuu1

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
data class Contacts (val name: String, val phone: String): Parcelable

