package com.locus.assignment.network

import android.util.Log
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import com.locus.assignment.models.entities.FormListItem
import com.locus.assignment.models.entities.FormResponse
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

object FormRepository {


    suspend fun getFormItems(s: String): Response<ArrayList<FormListItem>> {
        return withContext(Dispatchers.IO) {
            try {
                Response(
                    Gson().fromJson(
                        s,
                        object : TypeToken<ArrayList<FormListItem>>() {}.type
                    ), null
                )
            } catch (e: Exception) {
                Response(null, NetworkError(e.message, 500))
            }
        }
    }

    suspend fun submitForm(list: ArrayList<FormListItem>) {
        withContext(Dispatchers.IO) {
            val responseList = ArrayList<FormResponse>()
            for (formItem in list) {
                if (formItem.value != null)
                    responseList.add(FormResponse(formItem.id, formItem.value))
            }
            Log.i("FORM-responses", Gson().toJson(list))
        }
    }
}