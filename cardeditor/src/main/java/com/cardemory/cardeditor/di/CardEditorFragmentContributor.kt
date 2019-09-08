package com.cardemory.cardeditor.di

import com.cardemory.cardeditor.mvp.CardEditorFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface CardEditorFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CardEditorFragmentModule::class])
    fun contributeCardEditorFragment(): CardEditorFragment
}