package com.cardemory.app.di.module.activity

import com.cardemory.card_set_editor.di.CardSetEditorFragmentContributor
import com.cardemory.cardeditor.di.CardEditorFragmentContributor
import com.cardemory.cardlist.di.CardListFragmentContributor
import com.cardemory.cardsetlist.di.CardSetListFragmentContributor
import com.cardemory.cardsetlist.di.PrivacyPolicyFragmentContributor
import com.cardemory.train.di.TrainFragmentContributor
import dagger.Module

@Module(
    includes = [
        CardListFragmentContributor::class,
        CardEditorFragmentContributor::class,
        CardSetListFragmentContributor::class,
        CardSetEditorFragmentContributor::class,
        TrainFragmentContributor::class,
        PrivacyPolicyFragmentContributor::class
    ]
)
interface MainActivityFragmentProvider