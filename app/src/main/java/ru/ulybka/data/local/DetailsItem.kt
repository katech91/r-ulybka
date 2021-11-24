package ru.ulybka.data.local

data class DetailsItem(
    val id: Int,
    val name: String,
    val group: String,
    val category: String,
    val quantity: Float,
    val docId: Int,
    var isChecked: Int
)
