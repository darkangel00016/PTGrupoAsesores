package com.lerny.ptgrupoasesores.helpers

import androidx.room.Database
import androidx.room.RoomDatabase
import com.lerny.ptgrupoasesores.screens.main.data.dao.TermDao
import com.lerny.ptgrupoasesores.screens.main.data.entities.Term

@Database(entities = [Term::class], version = 1)
abstract class AppDatabase : RoomDatabase() {
    abstract fun termDao(): TermDao
}
