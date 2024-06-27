package com.genzen.zenspire.ui.journal

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.genzen.zenspire.data.models.journal.Journal

object JournalViewModel : ViewModel() {

    private val _journals = MutableLiveData<List<Journal>>()
    val journals: LiveData<List<Journal>> get() = _journals

    fun addJournal(journal: Journal) {
        _journals.value = listOf(journal) + _journals.value.orEmpty()
    }
}