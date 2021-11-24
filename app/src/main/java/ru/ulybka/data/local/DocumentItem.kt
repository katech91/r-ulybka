package ru.ulybka.data.local

import ru.ulybka.data.DocumentDetails

data class DocumentItem(
    val id: Int,
    val date: String,
    val post: String,
    val numPos: Int,
    val numChecked: Int = 0
) {
    companion object {
        fun build(doc: DocumentDetails): DocumentItem = DocumentItem(
            id = doc.data1.first().id,
            date = doc.data1.first().date,
            post = doc.data1.first().post,
            numPos = doc.data2.size
        )
    }
}
