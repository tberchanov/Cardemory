package com.cardemory.app.di.module

import com.cardemory.common.data.KeyValueRepository
import com.cardemory.common.data.PreferencesKeyValueRepository
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
interface DataModule {

    @Singleton
    @Binds
    fun provideKeyValueRepository(
        preferencesKeyValueRepository: PreferencesKeyValueRepository
    ): KeyValueRepository
}