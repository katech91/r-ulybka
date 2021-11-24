package ru.ulybka.data

import com.google.gson.annotations.SerializedName

data class Good(
    @field:SerializedName("id_inst")
    val id: Int,
    @field:SerializedName("pos_name")
    val name: String,
    @field:SerializedName("pos_group_name")
    val group: String,
    @field:SerializedName("pos_category_name")
    val category: String,
    @field:SerializedName("good_qty")
    val quantity: Float,
    @field:SerializedName("id_hd_nakl")
    val docId: Int
    )
