package com.locus.assignment.models.entities

import com.locus.assignment.models.constants.ItemType

class FormListItem {
    lateinit var type:ItemType
    lateinit var id:String
    var title:String?=null
    var dataMap:HashMap<String, ArrayList<String>>?=null

    var value:String?=null
    var enabled=false
}