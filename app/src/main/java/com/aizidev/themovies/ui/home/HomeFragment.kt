package com.aizidev.themovies.ui.home

import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aizidev.themovies.AppExecutors

import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.binding.FragmentDataBindingComponent
import com.aizidev.themovies.databinding.HomeFragmentBinding
import com.aizidev.themovies.di.component.Injectable
import com.aizidev.themovies.ui.common.RetryCallback
import com.aizidev.themovies.util.KEY_PREFF_TOKEN
import com.aizidev.themovies.util.NavigationControllerMain
import com.aizidev.themovies.util.autoCleared
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable, MainActivity.OnBackPressedListner {
    @Inject
    lateinit var appExecutors: AppExecutors
    @Inject
    lateinit var navigationControllerMain: NavigationControllerMain

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding by autoCleared<HomeFragmentBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private val homeViewModel: HomeViewModel by viewModels {
        viewModelFactory
    }
    private var adapter by autoCleared<AdapterHomeMovie>()

    private var option: Int = 0

    fun newInstance(): HomeFragment {
        return HomeFragment()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<HomeFragmentBinding>(
            inflater,
            R.layout.home_fragment,
            container,
            false,
            dataBindingComponent
        )
        dataBinding.retryCallback = object : RetryCallback {
            override fun retry() {
                homeViewModel.retry()
            }
        }
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initComponent()
        initData()
        initAdapter()
        initContributorList(homeViewModel)
    }

    private fun initComponent() {
        binding.swplHomeFragmentRefresh.setOnRefreshListener {
            Handler().postDelayed({
                binding.swplHomeFragmentRefresh.isRefreshing = false
            }, 1500)
        }
        binding.btnCatHomeCategory.setOnClickListener {
            binding.llCatHomeContainer.visibility = View.VISIBLE
        }
        binding.tvCatPopularHome.setOnClickListener {
            option = 0
            initContributorList(homeViewModel)
            binding.llCatHomeContainer.visibility = View.GONE
        }
        binding.tvCatUpComingHome.setOnClickListener {
            option = 1
            initContributorList(homeViewModel)
            binding.llCatHomeContainer.visibility = View.GONE
        }
        binding.tvCatTopRatedHome.setOnClickListener {
            option = 2
            initContributorList(homeViewModel)
            binding.llCatHomeContainer.visibility = View.GONE
        }
        binding.tvCatNowPlayingHome.setOnClickListener {
            option = 3
            initContributorList(homeViewModel)
            binding.llCatHomeContainer.visibility = View.GONE
        }
    }

    private fun initAdapter() {
        val adapter = AdapterHomeMovie(dataBindingComponent, appExecutors) {
                movieRes -> navigationControllerMain.navigateDetail(movieRes.id)
        }
        this.adapter = adapter

        binding.rcvHomeFragmentMovie.adapter = adapter
        this.adapter = adapter
    }

    private fun initContributorList(viewModel: HomeViewModel) {
        when (option) {
            0 -> {
                viewModel.popularMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            1 -> {
                viewModel.upcomingMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            2 -> {
                viewModel.topratedMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            3 -> {
                viewModel.nowplayingMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
        }
    }

    private fun initData() {
        homeViewModel.setToken(KEY_PREFF_TOKEN)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    override fun onBackPressed(): Boolean {
        activity?.finish()
        return true
    }

}
