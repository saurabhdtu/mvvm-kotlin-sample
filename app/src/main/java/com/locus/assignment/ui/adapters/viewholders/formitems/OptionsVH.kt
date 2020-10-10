package com.locus.assignment.ui.adapters.viewholders.formitems

import android.view.View
import android.widget.RadioButton
import com.locus.assignment.databinding.ItemFormOptionsBinding
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.ui.interfaces.ListItemInterface

class OptionsVH(val binding: ItemFormOptionsBinding, itemClickListener: ListItemInterface) :
    BaseFormItemVH(binding) {
    init {
        binding.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            formItem?.value = group.findViewById<RadioButton>(checkedId).text as String?
        }
    }

    override fun setBindingData(formItem: FormListItem) {
        this.formItem = formItem
        binding.formItem = formItem
        if (formItem.dataMap != null && formItem.dataMap!!.containsKey("options")) {
            binding.radioGroup.visibility = View.VISIBLE
            val list = formItem.dataMap!!["options"]
            for (i in 0 until list!!.size) {
                if (i + 1 > binding.radioGroup.childCount) {
                    val rb = RadioButton(binding.radioGroup.context)
                    rb.id = i
                    rb.text = list[i]
                    binding.radioGroup.addView(rb)
                } else {
                    binding.radioGroup.getChildAt(i).visibility = View.VISIBLE
                }
            }
            if (list.size < binding.radioGroup.childCount) {
                for (j in list.size - 1 until binding.radioGroup.childCount)
                    binding.radioGroup.getChildAt(j).visibility = View.GONE
            }

        } else {
            binding.radioGroup.visibility = View.GONE
        }
    }
}