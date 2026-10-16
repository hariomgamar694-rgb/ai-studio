package com.example.data.model

data class ExternalTool(
    val id: String,
    val name: String,
    val category: String,
    val categoryHi: String,
    val officialUrl: String,
    val shortSummaryEn: String,
    val shortSummaryHi: String,
    val supportedWorkflowsEn: List<String>,
    val supportedWorkflowsHi: List<String>,
    val pricingStatus: String, // e.g. "Free tier available / Subscription options"
    val pricingStatusHi: String,
    val regionalNoticeEn: String,
    val regionalNoticeHi: String,
    val badge: String = "Verified Official Link"
)
