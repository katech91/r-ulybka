package ru.ulybka.data

import com.google.gson.annotations.SerializedName
/**
 * Data class that represents results from internet.
 *
 * Not all of the fields returned from the API are represented here; only the ones used in this
 * project are listed below.
 */
data class Document(
    @field:SerializedName("id_hd_nakl")
    val id: Int,
    @field:SerializedName("num_zak")
    val numZak: String,
    @field:SerializedName("dat_doc")
    val date: String,
    @field:SerializedName("name_post")
    val post: String
)