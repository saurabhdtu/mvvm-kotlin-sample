package com.locus.assignment.ui.adapters

import android.graphics.Point
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.locus.assignment.R
import com.locus.assignment.models.constants.ItemType
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.ui.adapters.viewholders.formitems.BaseFormItemVH
import com.locus.assignment.ui.adapters.viewholders.formitems.CommentVH
import com.locus.assignment.ui.adapters.viewholders.formitems.OptionsVH
import com.locus.assignment.ui.adapters.viewholders.formitems.PhotoVH
import com.locus.assignment.ui.interfaces.ListItemInterface

class AdapterFormList(val list: ArrayList<FormListItem>, val itemClickLis: ListItemInterface, val point:Point) :
    RecyclerView.Adapter<BaseFormItemVH>() {
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseFormItemVH {
        return ItemType.values()[viewType].let {
            when (it) {
                ItemType.COMMENT -> {
                    CommentVH(
                        DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_form_comment,
                            parent,
                            false
                        ), itemClickLis
                    )
                }
                ItemType.PHOTO -> {
                    PhotoVH(
                        point, 
                        DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_form_photo,
                            parent,
                            false
                        ), itemClickLis
                    )
                }
                ItemType.SINGLE_CHOICE -> {
                    OptionsVH(
                        DataBindingUtil.inflate(
                            LayoutInflater.from(parent.context),
                            R.layout.item_form_options,
                            parent,
                            false
                        ), itemClickLis
                    )
                }
            }
        }
    }

    override fun onBindViewHolder(holder: BaseFormItemVH, position: Int) {
        val formItem = list[position]
        holder.setBindingData(formItem)
    }

    fun updateItem(pos: Int) {
        notifyItemChanged(pos)
    }

    override fun getItemCount(): Int {
        return list.size
    }

    override fun getItemViewType(position: Int): Int {
        return list[position].type.ordinal
    }
}