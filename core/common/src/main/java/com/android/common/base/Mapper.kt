package com.android.common.base

interface Mapper<F,T> {
    fun mapFrom(from:F):T
}