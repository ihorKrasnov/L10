package com.example.l10.models

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Task(
    val title: String,
    val description: String,
    var isCompleted: Boolean = false
) : Parcelable
