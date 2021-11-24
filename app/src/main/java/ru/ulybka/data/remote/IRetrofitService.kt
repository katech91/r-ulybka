package ru.ulybka.data.remote

import retrofit2.http.GET
import retrofit2.http.Query
import ru.ulybka.data.DocumentDetailsResponse
import ru.ulybka.data.DocumentListResponse

interface IRetrofitService {
    @GET("/api/getdocumentlist/")
    fun getDocumentList(): retrofit2.Call<DocumentListResponse>

    @GET("/api/getdocument/")
    fun getDocumentDetails(
        @Query("id") id: Int
    ): retrofit2.Call<DocumentDetailsResponse>
}