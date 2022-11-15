package com.example.android.criminalintent

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.util.*

private const val TAG = "CrimeListViewModel"

class CrimeListViewModel: ViewModel() {

    private val crimeRepository = CrimeRepository.get()

    val crimes = mutableListOf<Crime>()

    suspend fun loadCrimes(): List<Crime> = crimeRepository.getCrimes()
}