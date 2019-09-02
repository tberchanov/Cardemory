package com.cardemory.memory_label.di

import com.cardemory.carddata.entity.Card
import com.cardemory.carddata.entity.CardSet
import com.cardemory.memory_label.CardMemoryLabelTransformer
import com.cardemory.memory_label.CardSetMemoryLabelTransformer
import com.cardemory.memory_label.MemoryLabelTransformer
import com.cardemory.memory_label.card.HighCardMemoryLabelTransformer
import com.cardemory.memory_label.card.LowCardMemoryLabelTransformer
import com.cardemory.memory_label.card.MiddleCardMemoryLabelTransformer
import com.cardemory.memory_label.cardset.HighCardSetMemoryLabelTransformer
import com.cardemory.memory_label.cardset.LowCardSetMemoryLabelTransformer
import com.cardemory.memory_label.cardset.MiddleCardSetMemoryLabelTransformer
import dagger.Module
import dagger.Provides
import dagger.multibindings.IntoSet

@Module
class MemoryLabelModule {

    @IntoSet
    @Provides
    fun provideHighCardMemoryLabelTransformer(): MemoryLabelTransformer<Card> =
        HighCardMemoryLabelTransformer()

    @IntoSet
    @Provides
    fun provideMiddleCardMemoryLabelTransformer(): MemoryLabelTransformer<Card> =
        MiddleCardMemoryLabelTransformer()

    @IntoSet
    @Provides
    fun provideLowCardMemoryLabelTransformer(): MemoryLabelTransformer<Card> =
        LowCardMemoryLabelTransformer()

    @Provides
    fun provideCardMemoryLabelTransformer(
        memoryLabelTransformers: Set<@JvmSuppressWildcards MemoryLabelTransformer<Card>>
    ) = CardMemoryLabelTransformer(memoryLabelTransformers.toList())

    @IntoSet
    @Provides
    fun provideHighCardSetMemoryLabelTransformer(): MemoryLabelTransformer<CardSet> =
        HighCardSetMemoryLabelTransformer()

    @IntoSet
    @Provides
    fun provideMiddleCardSetMemoryLabelTransformer(): MemoryLabelTransformer<CardSet> =
        MiddleCardSetMemoryLabelTransformer()

    @IntoSet
    @Provides
    fun provideLowCardSetMemoryLabelTransformer(): MemoryLabelTransformer<CardSet> =
        LowCardSetMemoryLabelTransformer()

    @Provides
    fun provideCardSetMemoryLabelTransformer(
        memoryLabelTransformers: Set<@JvmSuppressWildcards MemoryLabelTransformer<CardSet>>
    ) = CardSetMemoryLabelTransformer(memoryLabelTransformers.toList())
}