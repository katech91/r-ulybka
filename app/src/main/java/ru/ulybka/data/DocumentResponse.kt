package ru.ulybka.data
/**
 * Data class that represents results of getDocumentList request.
 */
data class DocumentListResponse(
    val exception: Exeption,
    val data: List<DocumentResponse>
)

data class Exeption(
    val error: Int,
    val error_msg: String
)

data class DocumentResponse (
    val id_pos: Int,
    val id_record: Int,
    val nom_route: String,
    val nom_zak: String
)
