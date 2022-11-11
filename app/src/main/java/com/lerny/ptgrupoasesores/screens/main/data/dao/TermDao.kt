package com.lerny.ptgrupoasesores.screens.main.data.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import com.lerny.ptgrupoasesores.screens.main.data.entities.Term

@Dao
interface TermDao {

    @Query("SELECT * FROM term WHERE term = :term LIMIT 1")
    suspend fun findByName(term: String): Term?

    @Insert
    suspend fun insertAll(vararg terms: Term)

}