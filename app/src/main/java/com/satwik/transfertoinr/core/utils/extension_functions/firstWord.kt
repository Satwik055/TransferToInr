package com.satwik.transfertoinr.core.utils.extension_functions

fun String.firstWord(): String {
    return this.split(" ").firstOrNull() ?: ""
}