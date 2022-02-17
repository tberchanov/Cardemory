package com.cardemory.train.di

import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.train.mvp.TrainFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface TrainFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [TrainFragmentModule::class])
    fun contributesTrainFragment(): TrainFragment
}