package com.adolfo.core.platform

data class ApiResponse<T>(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val data: T,
    val etag: String?
)
