package com.locus.assignment.ui.viewmodels

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.network.FormRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class FormVM : BaseVM() {
    var formList = MutableLiveData<ArrayList<FormListItem>>()

    fun getFormData(s: String) {
        progress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            val response = FormRepository.getFormItems(s)
            withContext(Dispatchers.Main) {
                progress.value = false
                if (response.body != null) {
                    formList.value = response.body
                } else if (response.error != null) {
                    error.value = response.error.errMessage
                }
            }
        }

    }

    fun submit() {
        progress.value = true
        viewModelScope.launch(Dispatchers.IO) {
            if (formList.value != null)
                FormRepository.submitForm(formList.value!!)
            withContext(Dispatchers.Main) {
                progress.value = false
                error.value = "Submitted. Check logs"
            }
        }
    }

}