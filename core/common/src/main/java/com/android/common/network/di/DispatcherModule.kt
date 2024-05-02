package com.android.common.network.di

import com.android.common.network.Dispatcher
import com.android.common.network.GhmDispatchers
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

@Module
@InstallIn(SingletonComponent::class)
object DispatcherModule {
    @Provides
    @Dispatcher(GhmDispatchers.IO)
    fun provideDispatcherIO(): CoroutineDispatcher = Dispatchers.IO

    @Provides
    @Dispatcher(GhmDispatchers.Default)
    fun provideDispatcherDefault(): CoroutineDispatcher = Dispatchers.Default
}