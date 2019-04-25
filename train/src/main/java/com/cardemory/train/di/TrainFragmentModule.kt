package com.cardemory.train.di

import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.train.mvp.TrainContract
import com.cardemory.train.mvp.TrainPresenter
import com.cardemory.train.navigation.TrainNavigation
import dagger.Module
import dagger.Provides

@Module
class TrainFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
        trainNavigation: TrainNavigation
    ): TrainContract.Presenter {
        return TrainPresenter(
            trainNavigation
        )
    }
}