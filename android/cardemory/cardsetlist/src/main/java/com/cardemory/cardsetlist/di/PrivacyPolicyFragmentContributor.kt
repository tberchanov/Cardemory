package com.cardemory.cardsetlist.di

import com.cardemory.cardsetlist.mvp.privacy.PrivacyPolicyFragment
import com.cardemory.common.di.scope.FragmentScope
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface PrivacyPolicyFragmentContributor {

    @FragmentScope
    @ContributesAndroidInjector(modules = [PrivacyPolicyFragmentModule::class])
    fun contributesPrivacyPolicyFragment(): PrivacyPolicyFragment
}
