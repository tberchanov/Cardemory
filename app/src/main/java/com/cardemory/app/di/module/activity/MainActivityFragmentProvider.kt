package com.cardemory.app.di.module.activity

import com.cardemory.cardeditor.di.CardEditorFragmentContributor
import com.cardemory.cardlist.di.CardListFragmentContributor
import com.cardemory.cardsetlist.di.CardSetListFragmentContributor
import dagger.Module

@Module(
    includes = [
        CardListFragmentContributor::class,
        CardEditorFragmentContributor::class,
        CardSetListFragmentContributor::class
    ]
)
interface MainActivityFragmentProvider