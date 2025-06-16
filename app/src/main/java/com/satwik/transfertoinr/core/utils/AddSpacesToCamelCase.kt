package com.satwik.transfertoinr.core.utils

fun addSpacesToCamelCase(input: String): String {
    if (input.contains(" ")) {
        return input
    }
    return input.replace(Regex("(?<=[a-z])(?=[A-Z])"), " ")
}