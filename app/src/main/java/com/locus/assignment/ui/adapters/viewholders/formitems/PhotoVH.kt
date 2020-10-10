package com.locus.assignment.ui.adapters.viewholders.formitems

import android.graphics.Point
import com.locus.assignment.databinding.ItemFormPhotoBinding
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.ui.interfaces.ListItemInterface

class PhotoVH(
    point: Point,
    val binding: ItemFormPhotoBinding,
    itemClickListener: ListItemInterface
) :
    BaseFormItemVH(binding) {
    companion object {
        const val FLAG_OPEN_CAMERA = 1
        const val FLAG_OPEN_IMAGE = 2
    }

    init {
        binding.ivClose.setOnClickListener {
            formItem?.value = null
            binding.formItem = formItem
        }
        val params = binding.ivForm.layoutParams
        params.width = point.x
        params.height = point.x
        binding.ivForm.layoutParams = params
        binding.ivForm.setOnClickListener {
            if (formItem?.value == null)
                itemClickListener.listItemClicked(adapterPosition, this, formItem, FLAG_OPEN_CAMERA)
            else
                itemClickListener.listItemClicked(adapterPosition, this, formItem, FLAG_OPEN_IMAGE)
        }
    }

    override fun setBindingData(formItem: FormListItem) {
        this.formItem = formItem
        binding.formItem = formItem
    }
}