package com.cardemory.cardsetlist.di

import com.cardemory.cardsetlist.mvp.CardSetListFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface CardSetListFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CardSetListFragmentModule::class])
    fun contributesCardListFragment(): CardSetListFragment
}