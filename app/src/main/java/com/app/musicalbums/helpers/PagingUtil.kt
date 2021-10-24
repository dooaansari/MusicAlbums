package com.app.musicalbums.helpers

fun getNextPage(totalResult: Int?, pageSize: Int?, currentPage: Int): Int? {
    totalResult?.let {
        pageSize?.let {
            return when (totalResult > pageSize * currentPage) {
                true -> currentPage.plus(1)
                else -> null
            }
        }
    }
    return null
}