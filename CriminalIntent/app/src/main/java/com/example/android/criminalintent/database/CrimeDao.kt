package com.example.android.criminalintent.database

import androidx.room.Dao
import androidx.room.Query
import com.example.android.criminalintent.Crime
import java.util.*


@Dao
interface CrimeDao {

    @Query("SELECT * FROM crime")
    suspend fun getCrimes(): List<Crime>

    @Query("SELECT * FROM crime WHERE id=(:id)")
    suspend fun getCrime(id: UUID): Crime
}