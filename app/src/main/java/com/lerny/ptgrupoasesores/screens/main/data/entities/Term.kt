package com.lerny.ptgrupoasesores.screens.main.data.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Term(
    @PrimaryKey(autoGenerate = true)
    val uid: Int?,

    @ColumnInfo(name = "term")
    val text: String,
)
