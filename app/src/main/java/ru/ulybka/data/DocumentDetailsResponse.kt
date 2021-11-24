package ru.ulybka.data

/**
 * Data class that represents results of getDocument request.
 */
data class DocumentDetailsResponse(
    val exception: Exeption,
    val data: DocumentDetails
)

data class DocumentDetails(
    val data1: List<Document>,
    val data2: List<Good>
)
