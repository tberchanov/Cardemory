package com.cardemory.app.di.module.activity

import com.cardemory.cardeditor.di.CardEditorFragmentContributor
import com.cardemory.cardlist.di.CardListFragmentContributor
import dagger.Module

@Module(
    includes = [
        CardListFragmentContributor::class,
        CardEditorFragmentContributor::class
    ]
)
interface MainActivityFragmentProvider