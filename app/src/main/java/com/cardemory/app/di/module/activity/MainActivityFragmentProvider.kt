package com.cardemory.app.di.module.activity

import com.cardemory.cardlist.di.CardListFragmentContributor
import dagger.Module

@Module(includes = [CardListFragmentContributor::class])
interface MainActivityFragmentProvider