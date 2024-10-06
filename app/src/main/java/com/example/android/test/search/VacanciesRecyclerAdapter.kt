package com.example.android.test.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.example.android.test.R
import com.example.android.test.databinding.VacancyLayoutBinding
import com.example.android.test.network.Vacancies

class VacanciesRecyclerAdapter :
    ListAdapter<Vacancies, VacanciesRecyclerAdapter.ViewHolder>(VacanciesAdapterDiffCallback()) {


    class ViewHolder private constructor(val binding: VacancyLayoutBinding) :
        RecyclerView.ViewHolder(binding.root) {
        fun bind(item: Vacancies) {
            if (item.looking_number != null){
                binding.lookingNow.text = "Сейчас смотрит " + item.looking_number.toString() + " человек"
            }
            binding.vacancyName.text = item.title
            //TODO("доделай bind")
            binding.city.text = item.address.town
            binding.company.text = item.company
            binding.experience.text = item.experience.previewText
            binding.publicationDate.text = item.publishedDate
            if(item.isFavorite){
                binding.favoriteMark.setImageResource(R.drawable.ic_heart_filled)
            }
            else{
                binding.favoriteMark.setImageResource(R.drawable.ic_heart)
            }
            binding.executePendingBindings()
        }
        companion object{
            fun from(parent: ViewGroup): ViewHolder{
                val layoutInflater = LayoutInflater.from(parent.context)
                val binding = VacancyLayoutBinding.inflate(layoutInflater, parent, false)
                return ViewHolder(binding)
            }
        }
    }

    class VacanciesAdapterDiffCallback() : DiffUtil.ItemCallback<Vacancies>() {
        override fun areContentsTheSame(oldItem: Vacancies, newItem: Vacancies): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areItemsTheSame(oldItem: Vacancies, newItem: Vacancies): Boolean {
            return oldItem == newItem
        }
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bind(getItem(position))
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder.from(parent)
    }

    override fun getItemCount(): Int {
        return super.getItemCount()
    }
}