package com.example.android.criminalintent

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.criminalintent.DateHelper.Companion.formatDate
import com.example.android.criminalintent.databinding.ListItemCrimeBinding
import com.example.android.criminalintent.databinding.ListItemCrimeRequiresPoliceBinding

class CrimeHolder(
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = formatDate(crime.date)

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }

        binding.crimeSolved.visibility = if (crime.isSolved) {
            View.VISIBLE
        } else {
            View.INVISIBLE
        }
    }
}

class CrimePoliceRequiredHolder(
    private val binding: ListItemCrimeRequiresPoliceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = formatDate(crime.date)

        binding.contactPolice.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "Police Contacted",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class CrimeListAdapter(
    private val crimes: List<Crime>
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int
    ) : RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)

        return when (viewType) {
            R.layout.list_item_crime -> {
                val binding = ListItemCrimeBinding.inflate(inflater, parent, false)
                CrimeHolder(binding)
            }
            else -> {
                val binding = ListItemCrimeRequiresPoliceBinding.inflate(inflater, parent, false)
                CrimePoliceRequiredHolder(binding)
            }
        }
    }


    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        val crime = crimes[position]

        if (crime.requiresPolice) {
            (holder as CrimeHolder).bind(crime)
        } else {
            (holder as CrimePoliceRequiredHolder).bind(crime)
        }
    }

    override fun getItemViewType(position: Int): Int {

        return if (crimes[position].requiresPolice)
            R.layout.list_item_crime
        else
            R.layout.list_item_crime_requires_police
    }

    override fun getItemCount() = crimes.size

}