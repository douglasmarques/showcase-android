package com.doug.domain.challenge.ui.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doug.domain.challenge.repository.PropertyRepository
import com.doug.domain.challenge.repository.domain.Property
import com.doug.domain.challenge.repository.domain.SearchType
import com.doug.domain.challenge.test.CoroutinesTestRule
import com.doug.domain.challenge.test.data.PropertyTestDataFactory.createTestProperty
import com.doug.domain.challenge.test.testObserver
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import junit.framework.Assert.assertEquals
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class PropertiesViewModelTest {

    @JvmField
    @Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @JvmField
    @Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var repository: PropertyRepository

    lateinit var viewModel: PropertiesViewModel

    @Test
    fun searchProperty() {

    }

    @Test
    fun `factListObserver should be empty when the repository has no facts`() {
        repository = mock {
            onBlocking { search(SearchType.RENT) } doReturn emptyList()
        }
        viewModel = PropertiesViewModel(repository)

        viewModel.propertyListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()

        viewModel.searchProperty(SearchType.RENT)
        verifyBlocking(repository, { repository.search(SearchType.RENT) })

        assertEquals(viewModel.errorObserver.value, 0)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(emptyList<Property>(), viewModel.propertyListObserver.value)
    }


    @Test
    fun `factListObserver should contain one fact when the repository returns one fact`() {
        val properties = listOf(createTestProperty())
        repository = mock {
            onBlocking { search(SearchType.RENT) } doReturn properties
        }
        viewModel = PropertiesViewModel(repository)

        viewModel.propertyListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()

        viewModel.searchProperty(SearchType.RENT)
        verifyBlocking(repository, { repository.search(SearchType.RENT) })

        assertEquals(viewModel.errorObserver.value, 0)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(properties, viewModel.propertyListObserver.value)
        assertEquals(1, viewModel.propertyListObserver.value?.size)
    }
}
