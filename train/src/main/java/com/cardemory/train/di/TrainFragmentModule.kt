package com.cardemory.train.di

import com.cardemory.carddata.interactor.SaveCardsInteractor
import com.cardemory.common.di.scope.FragmentScope
import com.cardemory.common.interactor.ReadBooleanInteractor
import com.cardemory.common.interactor.WriteBooleanInteractor
import com.cardemory.memorykit.MemoryManager
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
        trainNavigation: TrainNavigation,
        memoryManager: MemoryManager,
        saveCardsInteractor: SaveCardsInteractor,
        readBooleanInteractor: ReadBooleanInteractor,
        writeBooleanInteractor: WriteBooleanInteractor
    ): TrainContract.Presenter {
        return TrainPresenter(
            trainNavigation,
            memoryManager,
            saveCardsInteractor,
            readBooleanInteractor,
            writeBooleanInteractor
        )
    }

    @FragmentScope
    @Provides
    fun provideMemoryManager() = MemoryManager()
}