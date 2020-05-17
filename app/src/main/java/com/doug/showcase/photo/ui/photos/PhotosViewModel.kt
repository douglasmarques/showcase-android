package com.doug.showcase.photo.ui.photos

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doug.showcase.R
import com.doug.showcase.photo.repository.PhotoRepository
import com.doug.showcase.photo.repository.domain.Photo

import kotlinx.coroutines.launch

class PhotosViewModel constructor(
    private val photosRepository: PhotoRepository
) : ViewModel() {

    private var searchText = "Explore"
    private var page = 1
    private var totalPages = 1
    var photos = mutableListOf<Photo>()

    val photoListObserver = MutableLiveData<List<Photo>>()
    val loadingObserver = MutableLiveData<Boolean>()
    val errorObserver = MutableLiveData(0)
    val searchTermObserver = MutableLiveData(searchText)

    fun searchPhotos(text: String) = viewModelScope.launch {
        searchText = text
        page = 1
        // set loading state to true, it means the screen will display the loading widget
        loadingObserver.value = true
        // set the search term, it will update the action bar title
        searchTermObserver.value = searchText
        // fetch the photos from the repository
        try {
            val photosResult = photosRepository.search(searchText, page)
            totalPages = photosResult.totalPages
            photos = photosResult.photos.toMutableList()
            // set the list of photos, UI will display the list
            photoListObserver.value = photos
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
        } catch (exception: Exception) {
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
            // set a generic error message if there is any error to retrieve photos
            errorObserver.value = R.string.dialog_error_generic
        }
    }

    /**
     * Load the next page if the page is less than or equal to the total of pages
     */
    fun loadMorePhotos() = viewModelScope.launch {
        try {
            page++
            if (page <= totalPages) {
                loadingObserver.value = true
                photos.addAll(photosRepository.search(searchText, page).photos)
                photoListObserver.value = photos
                loadingObserver.value = false
            }
        } catch (exception: Exception) {
            loadingObserver.value = false
            errorObserver.value = R.string.dialog_error_generic
        }
    }

    /**
     * Load the first page of photos with the current search term (pull to refresh)
     */
    fun loadPhotos() = viewModelScope.launch {
        searchPhotos(searchText)
    }
}
