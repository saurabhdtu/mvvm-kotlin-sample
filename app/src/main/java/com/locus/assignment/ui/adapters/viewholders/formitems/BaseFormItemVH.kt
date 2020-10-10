package com.locus.assignment.ui.adapters.viewholders.formitems

import androidx.databinding.ViewDataBinding
import androidx.recyclerview.widget.RecyclerView
import com.locus.assignment.models.entities.FormListItem

abstract class BaseFormItemVH( binding:ViewDataBinding):RecyclerView.ViewHolder(binding.root){
    var formItem: FormListItem? = null
    abstract fun setBindingData(formItem:FormListItem)
}