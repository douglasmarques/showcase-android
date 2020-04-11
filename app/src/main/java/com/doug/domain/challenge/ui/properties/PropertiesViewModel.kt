package com.doug.domain.challenge.ui.properties

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.doug.domain.challenge.R
import com.doug.domain.challenge.repository.PropertyRepository
import com.doug.domain.challenge.repository.domain.Property
import com.doug.domain.challenge.repository.domain.SearchType
import kotlinx.coroutines.launch

class PropertiesViewModel constructor(
    private val propertiesRepository: PropertyRepository = PropertyRepository()
) : ViewModel() {

    val propertyListObserver = MutableLiveData<List<Property>>()
    val loadingObserver = MutableLiveData<Boolean>()
    val errorObserver = MutableLiveData(0)

    fun searchProperty(type: SearchType) = viewModelScope.launch {
        try {
            // set loading state to true, it means the screen will display the loading widget
            loadingObserver.value = true
            // fetch the properties from the repository
            val properties = propertiesRepository.search(type)
            // set the list of properties, UI will display the list
            propertyListObserver.value = properties
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
        } catch (exception: Exception) {
            // set loading state to false it means the UI will hide the loading widget
            loadingObserver.value = false
            // set a generic error message if there is any error to retrieve properties
            errorObserver.value = R.string.dialog_error_generic
        }
    }

    override fun onCleared() {
        super.onCleared()
    }
}
