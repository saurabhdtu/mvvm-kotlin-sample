package com.locus.assignment.ui.adapters.viewholders.formitems

import android.text.Editable
import android.text.TextWatcher
import com.locus.assignment.databinding.ItemFormCommentBinding
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.ui.interfaces.ListItemInterface

class CommentVH(val binding: ItemFormCommentBinding, itemClickListener: ListItemInterface) :
    BaseFormItemVH(binding) {

    init {
        binding.switchForm.setOnCheckedChangeListener { switch, isChecked ->
            formItem?.enabled = isChecked
            binding.formItem = formItem
        }
        binding.etComment.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                formItem?.value = s.toString()
            }
        })

    }

    override fun setBindingData(formItem: FormListItem) {
        this.formItem = formItem
        binding.formItem = formItem
    }

}