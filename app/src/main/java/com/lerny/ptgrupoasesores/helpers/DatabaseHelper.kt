package com.lerny.ptgrupoasesores.helpers

import android.content.Context
import androidx.room.Room

class DatabaseHelper {

    companion object {

        fun getDatabase(context: Context): AppDatabase {
            return Room.databaseBuilder(
                context,
                AppDatabase::class.java, "lerny_gapsi"
            ).build()
        }

    }

}