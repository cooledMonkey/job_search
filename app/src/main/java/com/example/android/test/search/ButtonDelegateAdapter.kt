package com.example.android.test.search

import android.view.View
import com.example.android.test.databinding.MoreVacanciesButtonItemBinding
import com.example.android.test.network.Vacancies
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class ButtonDelegateAdapter(private val clickListener: ButtonActionListener):
    ViewBindingDelegateAdapter<MoreButton, MoreVacanciesButtonItemBinding>(MoreVacanciesButtonItemBinding::inflate) {

    override fun MoreVacanciesButtonItemBinding.onBind(item: MoreButton) {
        var buttonText = ""
        when(item.itemsCount%10) {
                1 -> buttonText = "Еще " + item.itemsCount.toString() + " вакансия"
                2, 3, 4 -> buttonText = "Еще " + item.itemsCount.toString() + " вакансии"
                else -> buttonText = "Еще " + item.itemsCount.toString() + " вакансий"
            }
        bigGreenButton.text = buttonText
        bigGreenButton.setOnClickListener{
            clickListener.onButtonClick()
        }
    }

    override fun isForViewType(item: Any): Boolean = item is MoreButton

    override fun MoreButton.getItemId(): Any = itemsCount
}

data class MoreButton(val itemsCount: Int)