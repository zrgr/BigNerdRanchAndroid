package com.example.android.criminalintent

import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.example.android.criminalintent.databinding.ListItemCrimeBinding
import com.example.android.criminalintent.databinding.ListItemCrimeRequiresPoliceBinding

class CrimeHolder(
    private val binding: ListItemCrimeBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

        binding.root.setOnClickListener {
            Toast.makeText(
                binding.root.context,
                "${crime.title} clicked!",
                Toast.LENGTH_SHORT
            ).show()
        }
    }
}

class CrimePoliceRequiredHolder(
    private val binding: ListItemCrimeRequiresPoliceBinding
) : RecyclerView.ViewHolder(binding.root) {

    fun bind(crime: Crime) {
        binding.crimeTitle.text = crime.title
        binding.crimeDate.text = crime.date.toString()

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