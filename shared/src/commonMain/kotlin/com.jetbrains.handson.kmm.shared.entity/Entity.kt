package com.jetbrains.handson.kmm.shared.entity

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class NYCSchool(
    @SerialName("school_name")
    val school_name: String? ="",
    @SerialName("dbn")
    val dbn: String? =""
)