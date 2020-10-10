package com.locus.assignment.ui.interfaces

import androidx.recyclerview.widget.RecyclerView

interface ListItemInterface {
    fun listItemClicked(pos:Int, viewHolder:RecyclerView.ViewHolder, data:Any?, type:Int)
}