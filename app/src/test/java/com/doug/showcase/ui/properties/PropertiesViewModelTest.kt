package com.doug.showcase.ui.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doug.showcase.R
import com.doug.showcase.repository.PropertyRepository
import com.doug.showcase.repository.domain.Property
import com.doug.showcase.repository.domain.SearchType
import com.doug.showcase.test.CoroutinesTestRule
import com.doug.showcase.test.data.PropertyTestDataFactory.createTestProperty
import com.doug.showcase.test.testObserver
import com.nhaarman.mockitokotlin2.doReturn
import com.nhaarman.mockitokotlin2.doThrow
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verifyBlocking
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Assert.assertEquals
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
    fun `listObserver should be empty when the repository has no property`() {
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
    fun `listObserver should contain one property when the repository returns one property`() {
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

    @Test
    fun `error state should have the generic error when the repository throws an exception`() {
        repository = mock {
            onBlocking { search(SearchType.BUY) } doThrow (RuntimeException())
        }
        viewModel = PropertiesViewModel(repository)

        viewModel.propertyListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()

        viewModel.searchProperty(SearchType.BUY)
        verifyBlocking(repository, { repository.search(SearchType.BUY) })

        assertEquals(viewModel.errorObserver.value, R.string.dialog_error_generic)
        assertEquals(viewModel.loadingObserver.value, false)
    }

}
