package com.cardemory.card_set_editor.di

import com.cardemory.card_set_editor.mvp.CardSetEditorFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface CardSetEditorFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CardSetEditorFragmentModule::class])
    fun contributeCardSetEditorFragment(): CardSetEditorFragment
}