package com.example.android.test.search

import com.example.android.test.R
import com.example.android.test.databinding.MoreVacanciesButtonItemBinding
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter

class ButtonDelegateAdapter(private val clickListener: ButtonActionListener) :
    ViewBindingDelegateAdapter<MoreButton, MoreVacanciesButtonItemBinding>(
        MoreVacanciesButtonItemBinding::inflate
    ) {

    override fun MoreVacanciesButtonItemBinding.onBind(item: MoreButton) {
        val moreString = root.context.getString(R.string.more)
        val buttonText = when (item.itemsCount % 10) {
            1 -> moreString + " " + item.itemsCount.toString() + " " + root.context.getString(R.string.vacancy_1)
            2, 3, 4 -> moreString + " " + item.itemsCount.toString() + " " + root.context.getString(
                R.string.vacancy_2_4
            )
            else -> moreString + " " + item.itemsCount.toString() + " " + root.context.getString(R.string.vacancy_5_0)
        }
        bigGreenButton.text = buttonText
        bigGreenButton.setOnClickListener {
            clickListener.onButtonClick()
        }
    }

    override fun isForViewType(item: Any): Boolean = item is MoreButton

    override fun MoreButton.getItemId(): Any = itemsCount
}

data class MoreButton(val itemsCount: Int)