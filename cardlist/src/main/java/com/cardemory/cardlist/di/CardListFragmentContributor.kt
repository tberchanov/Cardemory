package com.cardemory.cardlist.di

import com.cardemory.cardlist.mvp.CardListFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface CardListFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [CardListFragmentModule::class])
    fun contributesCardListFragment(): CardListFragment
}