package ru.ulybka.viewmodels

import android.app.Application
import android.util.Log
import androidx.lifecycle.asLiveData
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.InternalCoroutinesApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import okhttp3.internal.notify
import okhttp3.internal.notifyAll
import okhttp3.internal.wait
import ru.ulybka.App
import ru.ulybka.data.DocumentListResponse
import ru.ulybka.data.local.DetailsItem
import ru.ulybka.data.local.DocumentItem
import ru.ulybka.data.remote.RemoteRepository

class DocumentViewModel(application: Application): AndroidViewModel(application) {

    val documentsList: MutableLiveData<MutableList<DocumentItem>> by lazy {
        MutableLiveData<MutableList<DocumentItem>>(mutableListOf())
    }

    val document: MutableLiveData<DocumentItem> by lazy {
        MutableLiveData<DocumentItem>()
    }
    private val repository = RemoteRepository((application as App).service)

    @ExperimentalCoroutinesApi
    suspend fun loadDocumentsDetails() {
        repository.loadDocuments().collect {
            synchronized(documentsList) {
                documentsList.value?.add(it)
                documentsList.postValue(documentsList.value)
            }
        }
    }

    suspend fun handleLoadDocumentById(id: Int) {
        repository.loadDocumentById(id).collect {
            synchronized(document) {
                document.value = it
                document.postValue(document.value)
                Log.d("DocumentViewModel: handleLoadDocumentById", "${document.value}")
            }
        }
    }
}