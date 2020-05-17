package com.doug.showcase.photo.ui.photos

import android.os.Bundle
import android.view.*
import androidx.appcompat.widget.SearchView
import androidx.core.content.ContextCompat
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.doug.showcase.R
import com.doug.showcase.photo.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_photos.*
import org.koin.androidx.viewmodel.ext.android.viewModel

class PhotosFragment : BaseFragment() {

    private val viewModel by viewModel<PhotosViewModel>()
    private val adapter by lazy {
        PhotosAdapter()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setHasOptionsMenu(true)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_photos, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        setUpSwipeRefreshLayout()
        setUpRecyclerView()
        observeViewModel()

        // call view model to search the photos
        viewModel.loadPhotos()

    }

    override fun onCreateOptionsMenu(menu: Menu, inflater: MenuInflater) {
        inflater.inflate(R.menu.menu_photos, menu)
        setUpSearchView(menu)
        super.onCreateOptionsMenu(menu, inflater)
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
            viewModel.loadPhotos()
        }
    }

    private fun setUpRecyclerView() {
        propertiesRecyclerView.layoutManager = GridLayoutManager(context, 3)
        propertiesRecyclerView.adapter = adapter
        setUpScrollListener()
    }

    /**
     * Set up a scroll listener to load more photos when the amount of visible photos reaches
     * the threshold.
     */
    private fun setUpScrollListener() {
        propertiesRecyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                if (dy > 0) { // only when scrolling up
                    val visibleThreshold = 40
                    val layoutManager =
                        propertiesRecyclerView.layoutManager as GridLayoutManager
                    val lastItem = layoutManager.findLastCompletelyVisibleItemPosition()
                    val currentTotalCount = layoutManager.itemCount
                    if (currentTotalCount <= lastItem + visibleThreshold) {
                        viewModel.loadMorePhotos()
                    }
                }
            }
        })
    }

    /**
     * Setup the search view on the action bar and setup a listener search photos
     * with the search term typed by the user.
     */
    private fun setUpSearchView(menu: Menu) {
        val searchMenuItem: MenuItem = menu.findItem(R.id.search)
        val searchView: SearchView = searchMenuItem.actionView as SearchView
        searchView.queryHint = getString(R.string.search_photos)
        searchView.setOnQueryTextListener(object : SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                viewModel.searchPhotos(query)
                searchMenuItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String): Boolean {
                return false
            }
        })
    }

    private fun observeViewModel() {
        observePropertiesList()
        observeLoadingState()
        observeErrorState()
        observeSearchTerm()
    }

    /**
     *  Observe if the view model emits changes to the photo list.
     */
    private fun observePropertiesList() {
        viewModel.photoListObserver.observe(viewLifecycleOwner, Observer { properties ->
            if (properties.isNotEmpty()) {
                // show the list and hide the empty state text
                emptyStateTextView.visibility = View.GONE
                propertiesRecyclerView.visibility = View.VISIBLE
                adapter.setPhotos(properties)
            } else {
                // hide the list and show the empty state
                emptyStateTextView.visibility = View.VISIBLE
                propertiesRecyclerView.visibility = View.GONE
                adapter.setPhotos(listOf())
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
    private fun observeErrorState() {
        viewModel.errorObserver.observe(viewLifecycleOwner, Observer { errorResource ->
            // show the error message
            if (errorResource > 0) {
                showErrorDialog(message = getString(errorResource))
                viewModel.errorObserver.value = 0
            }
        })
    }

    /**
     * observe if the view model send the current search term
     */
    private fun observeSearchTerm() {
        viewModel.searchTermObserver.observe(viewLifecycleOwner, Observer { searchTerm ->
            setActionBarTitle(searchTerm)
        })
    }
}
