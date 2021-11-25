package ru.ulybka.data.local

import ru.ulybka.data.DocumentDetails
import ru.ulybka.data.Good

data class DetailsItem(
    val id: Int,
    val name: String,
    val group: String,
    val category: String,
    val quantity: Float,
    var isChecked: Boolean = false
){
    companion object {
        fun build(good: Good): DetailsItem = DetailsItem(
            id = good.id,
            name = good.name,
            group = good.group,
            category = good.category,
            quantity = good.quantity
        )
    }
}
