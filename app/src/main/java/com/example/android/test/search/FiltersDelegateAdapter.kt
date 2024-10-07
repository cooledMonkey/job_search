package com.example.android.test.search

import android.content.Intent
import android.net.Uri
import androidx.core.content.ContextCompat.startActivity
import com.example.android.test.R
import com.example.android.test.databinding.FiltersItemBinding
import com.example.android.test.network.Offers
import com.livermor.delegateadapter.delegate.ViewBindingDelegateAdapter


class FiltersDelegateAdapter :
    ViewBindingDelegateAdapter<Offers, FiltersItemBinding>(FiltersItemBinding::inflate) {

    override fun FiltersItemBinding.onBind(item: Offers) {
        if (item.id != null) {

            when (item.id) {
                "near_vacancies" -> {
                    filterIcon.setImageResource(R.drawable.ic_profile)
                    filterEllipse.setColorFilter(R.color.dark_blue)
                }

                "level_up_resume" -> filterIcon.setImageResource(R.drawable.ic_star)
                "temporary_job" -> filterIcon.setImageResource(R.drawable.ic_temporary_job)
            }
        }
        filterBackground.setOnClickListener{
            val uriUrl = Uri.parse(item.link)
            val launchBrowser = Intent(Intent.ACTION_VIEW, uriUrl)
            startActivity(root.context, launchBrowser, null)
        }
        filterTitle.text = item.title
        filterButtonText.text = item.button?.text ?: ""
    }

    override fun isForViewType(item: Any): Boolean = item is Offers

    override fun Offers.getItemId(): Any = title
}