package com.example.android.test.delegate_adapter

import android.view.View
import kotlinx.android.extensions.LayoutContainer

abstract class KDelegateAdapter<T>
    : BaseDelegateAdapter<KDelegateAdapter.KViewHolder, T>() {

    abstract fun onBind(item: T, viewHolder: KViewHolder)

    final override fun onBindViewHolder(view: View, item: T, viewHolder: KViewHolder) {
        onBind(item, viewHolder)
    }

    override fun createViewHolder(parent: View?): KViewHolder {
        return KViewHolder(parent)
    }

    class KViewHolder(override val containerView: View?)
        : BaseViewHolder(containerView), LayoutContainer
}