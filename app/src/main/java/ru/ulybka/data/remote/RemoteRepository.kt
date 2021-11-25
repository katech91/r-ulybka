package ru.ulybka.data.remote

import android.util.Log
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.awaitAll
import kotlinx.coroutines.cancel
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.channels.trySendBlocking
import kotlinx.coroutines.flow.*
import okhttp3.internal.wait
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.awaitResponse
import ru.ulybka.data.Document
import ru.ulybka.data.DocumentDetailsResponse
import ru.ulybka.data.DocumentListResponse
import ru.ulybka.data.local.DetailsItem
import ru.ulybka.data.local.DocumentItem

class RemoteRepository(private val service: IRetrofitService) {

    @ExperimentalCoroutinesApi
    fun loadDocuments(): Flow<DocumentItem> = callbackFlow {
        //Get a list of document's ids (id_record)
        val docsList = service.getDocumentList().awaitResponse().body()

        docsList?.data?.let { list ->
            //Load documents
            for (document in list) {
                val doc =  service.getDocumentDetails(document.id_record)

                doc.enqueue( object : Callback<DocumentDetailsResponse> {
                    override fun onResponse(
                        call: Call<DocumentDetailsResponse>?,
                        response: Response<DocumentDetailsResponse>
                    ){
                        response.body()?.data?.let { trySend(DocumentItem.build(it)) }
                    }

                    override fun onFailure(call: Call<DocumentDetailsResponse>?, t: Throwable?){
                        cancel(t?.message ?: "Unknown exception")
                    }
                })
            }
        }

        awaitClose { cancel() }
    }

    @ExperimentalCoroutinesApi
    fun loadDocumentById(id: Int):Flow<DocumentItem> = callbackFlow {
        val doc =  service.getDocumentDetails(id)
        doc.enqueue( object : Callback<DocumentDetailsResponse> {
            override fun onResponse(
                call: Call<DocumentDetailsResponse>?,
                response: Response<DocumentDetailsResponse>
            ){
                Log.d("RemoteRepository: onResponse", "${response.body()?.data}")
                response.body()?.data?.let { trySend(DocumentItem.build(it)) }
            }

            override fun onFailure(call: Call<DocumentDetailsResponse>?, t: Throwable?){
                cancel(t?.message ?: "Unknown exception")
            }
        })
        awaitClose { cancel() }
    }

}