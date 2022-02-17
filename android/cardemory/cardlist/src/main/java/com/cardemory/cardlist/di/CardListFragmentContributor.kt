package com.cardemory.cardlist.di

import com.cardemory.cardlist.mvp.CardListFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector
import kotlinx.coroutines.ObsoleteCoroutinesApi

@Module
interface CardListFragmentContributor {

    @ObsoleteCoroutinesApi
    @FragmentScope
    @ContributesAndroidInjector(modules = [CardListFragmentModule::class])
    fun contributesCardListFragment(): CardListFragment
}