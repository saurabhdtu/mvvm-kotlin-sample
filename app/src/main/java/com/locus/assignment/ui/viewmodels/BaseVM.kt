package com.locus.assignment.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

abstract class BaseVM:ViewModel() {
    val progress=MutableLiveData<Boolean>().apply { value = false }
    val error=MutableLiveData<String>()
}