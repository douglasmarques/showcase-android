package com.doug.showcase.ui.properties

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.doug.showcase.R
import com.doug.showcase.photo.repository.PhotoRepository
import com.doug.showcase.photo.repository.domain.Photo
import com.doug.showcase.photo.ui.photos.PhotosViewModel
import com.doug.showcase.showcase.test.data.PhotoTestDataFactory.createPhotosResults
import com.doug.showcase.test.CoroutinesTestRule
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
class PhotosViewModelTest {

    @JvmField
    @Rule
    val taskExecutorRule = InstantTaskExecutorRule()

    @ExperimentalCoroutinesApi
    @JvmField
    @Rule
    val coroutinesTestRule = CoroutinesTestRule()

    lateinit var repository: PhotoRepository

    lateinit var viewModel: PhotosViewModel

    @Test
    fun `list should be empty when the repository has no photo`() {
        val searchTerm = "no results"
        repository = mock {
            onBlocking { search(searchTerm) } doReturn createPhotosResults(
                photos = emptyList(),
                totalPages = 0
            )
        }
        viewModel = PhotosViewModel(repository)

        viewModel.photoListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()
        viewModel.searchTermObserver.testObserver()

        viewModel.searchPhotos(searchTerm)
        verifyBlocking(repository, { repository.search(searchTerm) })

        assertEquals(0, viewModel.errorObserver.value)
        assertEquals(false, viewModel.loadingObserver.value)
        assertEquals(emptyList<Photo>(), viewModel.photoListObserver.value)
        assertEquals(searchTerm, viewModel.searchTermObserver.value)
    }

    @Test
    fun `list should contain one photo when the repository returns one photo`() {
        val searchTerm = "no results"
        val photoResults = createPhotosResults()
        repository = mock {
            onBlocking { search(searchTerm) } doReturn createPhotosResults()
        }
        viewModel = PhotosViewModel(repository)

        viewModel.photoListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()
        viewModel.searchTermObserver.testObserver()

        viewModel.searchPhotos(searchTerm)
        verifyBlocking(repository, { repository.search(searchTerm) })

        assertEquals(0, viewModel.errorObserver.value)
        assertEquals(false, viewModel.loadingObserver.value)
        assertEquals(photoResults.photos, viewModel.photoListObserver.value)
        assertEquals(1, viewModel.photoListObserver.value?.size)
        assertEquals(searchTerm, viewModel.searchTermObserver.value)
    }

    @Test
    fun `error state should have the generic error when the repository throws an exception`() {
        val searchTerm = "explore"
        repository = mock {
            onBlocking { search("explore") } doThrow (RuntimeException())
        }
        viewModel = PhotosViewModel(repository)

        viewModel.photoListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()
        viewModel.searchTermObserver.testObserver()

        viewModel.searchPhotos(searchTerm)
        verifyBlocking(repository, { repository.search(searchTerm) })

        assertEquals(viewModel.errorObserver.value, R.string.dialog_error_generic)
        assertEquals(viewModel.loadingObserver.value, false)
        assertEquals(null, viewModel.photoListObserver.value)
    }

    @Test
    fun `loadMore - load 2 pages when the total page equals 2`() {
        val searchTerm = "no results"
        val photoResults = createPhotosResults(
            totalPages = 2
        )
        repository = mock {
            onBlocking { search(searchTerm, 1) } doReturn photoResults
            onBlocking { search(searchTerm, 2) } doReturn photoResults
        }
        viewModel = PhotosViewModel(repository)

        viewModel.photoListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()
        viewModel.searchTermObserver.testObserver()

        viewModel.searchPhotos(searchTerm)
        viewModel.loadMorePhotos()
        verifyBlocking(repository, {
            search(searchTerm, 1)
            search(searchTerm, 2)
        })

        assertEquals(0, viewModel.errorObserver.value)
        assertEquals(false, viewModel.loadingObserver.value)
        assertEquals(2, viewModel.photoListObserver.value?.size)
        assertEquals(searchTerm, viewModel.searchTermObserver.value)
    }

    @Test
    fun `loadMore - do not load next page when total page equals 1`() {
        val searchTerm = "no results"
        val photoResults = createPhotosResults(
            totalPages = 1
        )
        repository = mock {
            onBlocking { search(searchTerm, 1) } doReturn photoResults
        }
        viewModel = PhotosViewModel(repository)

        viewModel.photoListObserver.testObserver()
        viewModel.errorObserver.testObserver()
        viewModel.loadingObserver.testObserver()
        viewModel.searchTermObserver.testObserver()

        viewModel.searchPhotos(searchTerm)
        viewModel.loadMorePhotos()
        verifyBlocking(repository, {
            search(searchTerm, 1)
        })

        assertEquals(0, viewModel.errorObserver.value)
        assertEquals(false, viewModel.loadingObserver.value)
        assertEquals(1, viewModel.photoListObserver.value?.size)
        assertEquals(searchTerm, viewModel.searchTermObserver.value)
    }

}
