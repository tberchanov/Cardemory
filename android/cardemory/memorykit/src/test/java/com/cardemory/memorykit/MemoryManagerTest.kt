package com.cardemory.memorykit

import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.Mockito.inOrder
import org.mockito.MockitoAnnotations.initMocks
import org.powermock.api.mockito.PowerMockito
import org.powermock.core.classloader.annotations.PrepareForTest
import org.powermock.modules.junit4.PowerMockRunner

@RunWith(PowerMockRunner::class)
@PrepareForTest(MemoryManager::class)
class MemoryManagerTest {

    @Mock
    private lateinit var mockMemoryHolder: MemoryHolder

    @Before
    fun setUp() {
        initMocks(this)
        PowerMockito.mockStatic(System::class.java)
        PowerMockito.`when`(System.currentTimeMillis()).thenReturn(MOCKED_CURRENT_TIME_MILLIS)
    }

    @Test
    fun zero_Memory_Rank_Forget_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(0.0)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.forget(mockMemoryHolder)
        assertEquals(0.0, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun one_Memory_Rank_Forget_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(1.0)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.forget(mockMemoryHolder)
        assertEquals(1.5979925503950554E-6, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun half_Memory_Rank_Forget_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(0.5)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.forget(mockMemoryHolder)
        assertEquals(7.989965943311361E-7, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun outdated_Last_Train_Forget_Test() {
        val outdatedLastTrainMillis = 100_000_000_000L
        PowerMockito.`when`(System.currentTimeMillis()).thenReturn(outdatedLastTrainMillis)
        `when`(mockMemoryHolder.memoryRank).thenReturn(0.5)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.forget(mockMemoryHolder)
        assertEquals(0.0, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = outdatedLastTrainMillis
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun zero_Memory_Rank_Remember_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(0.0)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.remember(mockMemoryHolder)
        assertEquals(0.0024726822793391836, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun one_Memory_Rank_Remember_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(1.0)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.remember(mockMemoryHolder)
        assertEquals(1.0, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    @Test
    fun half_Memory_Rank_Remember_Test() {
        `when`(mockMemoryHolder.memoryRank).thenReturn(0.5)
        `when`(mockMemoryHolder.lastTrainMillis).thenReturn(100L)

        val memoryManager = MemoryManager()
        val calculatedMemoryRank = memoryManager.remember(mockMemoryHolder)
        assertEquals(0.5024726822793392, calculatedMemoryRank, 0.0)

        val inOrder = inOrder(mockMemoryHolder)
        inOrder.verify(mockMemoryHolder).memoryRank = calculatedMemoryRank
        inOrder.verify(mockMemoryHolder).lastTrainMillis = MOCKED_CURRENT_TIME_MILLIS
        inOrder.verifyNoMoreInteractions()
    }

    companion object {
        private const val MOCKED_CURRENT_TIME_MILLIS = 4242L
    }
}