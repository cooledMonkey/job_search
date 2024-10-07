package com.example.android.test.search

import android.view.View
import com.example.android.test.R
import com.example.android.test.network.Vacancies
import com.example.android.test.databinding.VacanciesItemBinding
import com.example.android.test.network.Button
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter


class VacancyDelegateAdapter(private val clickListener: VacanciesActionListener):
    ViewBindingDelegateAdapter<Vacancies, VacanciesItemBinding>(VacanciesItemBinding::inflate) {

    override fun VacanciesItemBinding.onBind(item: Vacancies) {
        if (item.looking_number != null){
            lookingNow.text = "Сейчас смотрит " + item.looking_number.toString() + " человек"
        }
        else{
            lookingNow.visibility = View.GONE
        }
        vacancyName.text = item.title
        city.text = item.address.town
        company.text = item.company
        experience.text = item.experience.previewText
        publicationDate.text = item.publishedDate
        if(item.isFavorite){
            favoriteMark.setImageResource(R.drawable.ic_heart_filled)
        }
        else{
            favoriteMark.setImageResource(R.drawable.ic_heart)
        }
        favoriteMark.setOnClickListener {
            clickListener.onFavouriteMarkClick(item.id.toString())
            if(item.isFavorite){favoriteMark.setImageResource(R.drawable.ic_heart_filled)}
            else{favoriteMark.setImageResource(R.drawable.ic_heart)}
        }
        bigGreenButton.setOnClickListener{clickListener.onButtonClick()}
        fragmentBackground.setOnClickListener{clickListener.onVacancyCardClick()}
    }

    override fun isForViewType(item: Any): Boolean = item is Vacancies

    override fun Vacancies.getItemId(): Any = title
}