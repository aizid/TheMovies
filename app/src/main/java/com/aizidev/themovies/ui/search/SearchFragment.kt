package com.aizidev.themovies.ui.search

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.EditorInfo
import androidx.appcompat.widget.SearchView
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.binding.FragmentDataBindingComponent
import com.aizidev.themovies.databinding.SearchFragmentBinding
import com.aizidev.themovies.di.component.Injectable
import com.aizidev.themovies.ui.common.RetryCallback
import com.aizidev.themovies.util.KEY_PREFF_TOKEN
import com.aizidev.themovies.util.NavigationControllerMain
import com.aizidev.themovies.util.autoCleared
import javax.inject.Inject

class SearchFragment : Fragment(), Injectable, MainActivity.OnBackPressedListner {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var navigationControllerMain: NavigationControllerMain

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding by autoCleared<SearchFragmentBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private val searchMovie: SearchViewModel by viewModels {
        viewModelFactory
    }
    private var adapter by autoCleared<AdapterSearchMovie>()

    fun newInstance(): SearchFragment {
        return SearchFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<SearchFragmentBinding>(
            inflater,
            R.layout.search_fragment,
            container,
            false,
            dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                searchMovie.retry()
            }
        }
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initComponent()
        initData()
        initContributorList(searchMovie)
    }

    private fun initAdapter() {
        val adapter = AdapterSearchMovie(dataBindingComponent, appExecutors) { movieRes ->
            navigationControllerMain.navigateDetail(movieRes.id, movieRes.popular,
                movieRes.upcoming, movieRes.topRated, movieRes.nowPlaying)
        }
        this.adapter = adapter

        binding.rcvSearchFragmentMovie.adapter = adapter
        this.adapter = adapter
    }

    private fun initComponent() {
        binding.srvSearchList.imeOptions = EditorInfo.IME_ACTION_DONE
        binding.srvSearchList.setOnQueryTextListener(object : SearchView.OnQueryTextListener {

            override fun onQueryTextSubmit(query: String?): Boolean {
                if (query != null && query.length > 2) {
                    searchMovie.setQuery(query)
                } else {
                    searchMovie.setQuery("")
                }
                return false;
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null && newText.length > 2) {
                    searchMovie.setQuery(newText)
                } else {
                    searchMovie.setQuery("")
                }
                return false;
            }
        });
    }

    private fun initData() {
        searchMovie.setToken(KEY_PREFF_TOKEN)
        searchMovie.setQuery("")
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initContributorList(viewModel: SearchViewModel) {
        viewModel.searchMovie.observe(viewLifecycleOwner, Observer { listResource ->
            if (listResource?.data != null && listResource.data.isNotEmpty()) {
                adapter.submitList(listResource.data)
            } else {
                adapter.submitList(emptyList())
            }
        })
    }

    override fun onBackPressed(): Boolean {
        return false
    }

}