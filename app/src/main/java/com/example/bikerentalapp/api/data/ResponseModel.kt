package com.example.bikerentalapp.api.data

import com.google.gson.annotations.SerializedName

data class QueryResponse<P, T>(
    @SerializedName("query_by")
    val queryBy: String,
    @SerializedName("target_count")
    val targetCount: Int,
    val params: Map<String, P>,
    val data: List<T>
)

data class CRUDResponse<T>(
    val action: String,
    val status: String,
    @SerializedName("target_count")
    val targetCount: Int = 1,
    val target: T?
)