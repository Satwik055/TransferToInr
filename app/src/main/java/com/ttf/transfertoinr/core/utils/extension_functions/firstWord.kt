package com.ttf.transfertoinr.core.utils.extension_functions

fun String.firstWord(): String {
    return this.split(" ").firstOrNull() ?: ""
}