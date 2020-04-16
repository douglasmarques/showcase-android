package com.doug.domain.challenge.ui.properties

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import com.doug.domain.challenge.R
import com.doug.domain.challenge.repository.domain.SearchType
import com.doug.domain.challenge.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_properties.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PropertiesFragment : BaseFragment() {

    companion object {
        private const val TYPE = "TYPE"

        fun newInstance(searchType: SearchType): PropertiesFragment {
            return PropertiesFragment().apply {
                arguments = bundleOf(
                    TYPE to searchType
                )
            }
        }
    }

    private val viewModel by viewModel<PropertiesViewModel>()
    private val adapter by lazy {
        PropertiesAdapter()
    }
    private var selectedType = SearchType.RENT

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        selectedType = arguments?.get(TYPE) as? SearchType ?: SearchType.RENT
        return inflater.inflate(R.layout.fragment_properties, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpSwipeRefreshLayout()
        setUpRecyclerView()
        observeViewModel()

        // call view model to search the properties
        viewModel.searchProperty(selectedType)

    }

    /**
     * Setup swipe to refresh widget and bind it to the view model to fetch/refresh properties
     */
    private fun setUpSwipeRefreshLayout() {
        context?.let {
            swipeRefreshLayout.setColorSchemeColors(
                ContextCompat.getColor(
                    it,
                    R.color.colorPrimaryDark
                )
            )
        }
        swipeRefreshLayout.setOnRefreshListener {
            viewModel.searchProperty(selectedType)
        }
    }

    private fun setUpRecyclerView() {
        propertiesRecyclerView.layoutManager = LinearLayoutManager(context)
        propertiesRecyclerView.adapter = adapter
    }

    private fun observeViewModel() {
        observePropertiesList()
        observeLoadingState()
        observerErrorState()
    }

    /**
     *  Observe if the view model emits changes to the property list.
     */
    private fun observePropertiesList() {
        viewModel.propertyListObserver.observe(viewLifecycleOwner, Observer { properties ->
            if (properties.isNotEmpty()) {
                // show the list and hide the empty state text
                emptyStateTextView.visibility = View.GONE
                propertiesRecyclerView.visibility = View.VISIBLE
                adapter.setProperties(properties)
            } else {
                // hide the list and show the empty state
                emptyStateTextView.visibility = View.VISIBLE
                propertiesRecyclerView.visibility = View.GONE
                adapter.setProperties(listOf())
            }
        })
    }

    /**
     * observe if the view model emits changes to the loading state
     */
    private fun observeLoadingState() {
        viewModel.loadingObserver.observe(viewLifecycleOwner, Observer { isLoading ->
            // show the refreshing widget
            if (isLoading) {
                if (!swipeRefreshLayout.isRefreshing) {
                    swipeRefreshLayout.isRefreshing = true
                }
            } else {
                // hide the refreshing widget
                swipeRefreshLayout.isRefreshing = false
            }
        })
    }

    /**
     * observe if the view model pushes any error messages
     */
    private fun observerErrorState() {
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer { errorResource ->
            // show the error message
            if (errorResource > 0) {
                showErrorDialog(message = getString(errorResource))
                viewModel.errorObserver.value = 0
            }
        })
    }
}
