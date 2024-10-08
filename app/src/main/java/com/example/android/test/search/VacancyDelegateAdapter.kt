package com.example.android.test.search

import android.view.View
import com.example.android.test.R
import com.example.android.test.network.Vacancies
import com.example.android.test.databinding.VacanciesItemBinding
import com.example.android.test.network.Button
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter


class VacancyDelegateAdapter(private val clickListener: VacanciesActionListener) :
    ViewBindingDelegateAdapter<Vacancies, VacanciesItemBinding>(VacanciesItemBinding::inflate) {

    override fun VacanciesItemBinding.onBind(item: Vacancies) {
        var peopleString = if(item.looking_number in (2..4))
        {root.context.getString(R.string.looking_for_2_4)}
        else{root.context.getString(R.string.looking_for_def)}
        if (item.looking_number != null) {
            lookingNow.text = root.context.getString(R.string.now_looking) +
                    item.looking_number.toString() + peopleString
        } else {
            lookingNow.visibility = View.GONE
        }
        vacancyName.text = item.title
        city.text = item.address.town
        company.text = item.company
        experience.text = item.experience.previewText
        publicationDate.text =
            PublicationDate(item.publishedDate).buildString()
        if (item.isFavorite) {
            favoriteMark.setImageResource(R.drawable.ic_heart_filled)
        } else {
            favoriteMark.setImageResource(R.drawable.ic_heart)
        }
        favoriteMark.setOnClickListener {
            clickListener.onFavouriteMarkClick(item.id.toString())
            if (item.isFavorite) {
                favoriteMark.setImageResource(R.drawable.ic_heart_filled)
            } else {
                favoriteMark.setImageResource(R.drawable.ic_heart)
            }
        }
        bigGreenButton.setOnClickListener { clickListener.onButtonClick() }
        fragmentBackground.setOnClickListener { clickListener.onVacancyCardClick() }
    }

    override fun isForViewType(item: Any): Boolean = item is Vacancies

    override fun Vacancies.getItemId(): Any = title
}

class PublicationDate(val rawDate: String) {
    fun recognizeMonth(rawDate: String): String {
        return when (rawDate.substring(5, 7)) {
            "01" -> "января"
            "02" -> "февраля"
            "03" -> "марта"
            "04" -> "апреля"
            "05" -> "мая"
            "06" -> "июня"
            "07" -> "июля"
            "08" -> "августа"
            "09" -> "сентября"
            "10" -> "октября"
            "11" -> "ноября"
            "12" -> "декабря"
            else -> "числа"
        }
    }

    fun getDate(rawDate: String): String {
        var date = rawDate.substring(8, 10)
        if (date.substring(0, 1) == "0") {
            date = date.substring(1, 2)
        }
        return date
    }

    fun buildString(): String {
        return "Опубликовано " + getDate(rawDate) + " " + recognizeMonth(rawDate)
    }
}