package com.cardemory.cardsetlist.di

import com.cardemory.cardsetlist.mvp.privacy.PrivacyPolicyContract
import com.cardemory.cardsetlist.mvp.privacy.PrivacyPolicyPresenter
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.Provides

@Module()
class PrivacyPolicyFragmentModule {

    @FragmentScope
    @Provides
    fun providePresenter(
    ): PrivacyPolicyContract.Presenter {
        return PrivacyPolicyPresenter()
    }
}
