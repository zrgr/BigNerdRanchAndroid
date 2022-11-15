package com.example.android.criminalintent

import android.content.Context
import com.example.android.criminalintent.database.CrimeDatabase

class CrimeRepository private constructor(context: Context) {

    companion object {
        private var INSTANCE: CrimeRepository? = null

        fun initialize(context: Context) {
            if (INSTANCE == null) {
                INSTANCE = CrimeRepository(context)
            }
        }

        fun get(): CrimeRepository {
            return INSTANCE ?:
                throw IllegalStateException("Crime Repository must be initialized")
        }
    }
}