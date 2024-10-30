package com.genzen.zenspire.ui.journal

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.genzen.zenspire.data.api.ApiClient
import com.genzen.zenspire.data.models.journal.JournalData
import kotlinx.coroutines.launch

class JournalViewModel : ViewModel() {

    private val _journals = MutableLiveData<List<JournalData>>()
    val journals: LiveData<List<JournalData>> get() = _journals

    private val api = ApiClient.getApiInstance()
    private var token = "----"

    fun setToken(token: String) {
        this.token = token
    }

    suspend fun addJournal(journal: JournalData): JournalData {
        val response = api.addJournal("Bearer $token", journal)
        return response.data
    }

    suspend fun updateJournal(id: Int, journal: JournalData): JournalData {
        val response = api.updateJournal(id, "Bearer $token", journal)
        return response.data
    }

    fun fetchJournals() {
        viewModelScope.launch {
            val response = api.getJournals("Bearer $token")
            _journals.postValue(response.data)
        }
    }

    fun logData() {
        if (journals.value != null) {
            Log.d("debug", journals.value!!.last().title)
        } else {
            Log.d("debug", "gak ada")
        }
    }
}