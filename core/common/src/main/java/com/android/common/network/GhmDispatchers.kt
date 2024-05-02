package com.android.common.network

import javax.inject.Qualifier

@Qualifier
@Retention
annotation class Dispatcher(val ghmDispatcher: GhmDispatchers)
enum class GhmDispatchers {
    Default,
    IO
}