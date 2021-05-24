package com.aizidev.themovies.ui.home

import android.content.Intent
import android.os.Bundle
import android.os.Handler
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.BuildConfig

import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.binding.FragmentDataBindingComponent
import com.aizidev.themovies.databinding.HomeFragmentBinding
import com.aizidev.themovies.di.component.Injectable
import com.aizidev.themovies.ui.common.RetryCallback
import com.aizidev.themovies.util.KEY_PREFF_TOKEN
import com.aizidev.themovies.util.NavigationControllerMain
import com.aizidev.themovies.util.autoCleared
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
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

    private val manager: SplitInstallManager by lazy { SplitInstallManagerFactory.create(requireContext()) }

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

        initAdapter()
        initComponent()
        initData()
        initContributorList(homeViewModel)
    }

    private fun initComponent() {
        updateDynamicFeatureButtonState()
        binding.swplHomeFragmentRefresh.setOnRefreshListener {
            initContributorList(homeViewModel)
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
        binding.tvCatSearchHome.setOnClickListener {
            navigationControllerMain.navigateSearch()
        }

        binding.ivCatSearchDynamicHome.setOnClickListener {
            val request = SplitInstallRequest.newBuilder()
                .addModule(DYNAMIC_MODULE_NAME)
                .build()

            manager.registerListener {
                when (it.status()) {
                    SplitInstallSessionStatus.DOWNLOADING -> Toast.makeText(activity, "Downloading feature", Toast.LENGTH_SHORT).show()
                    SplitInstallSessionStatus.INSTALLED -> {
                        Toast.makeText(activity, "Feature ready to be used", Toast.LENGTH_SHORT).show()
                        updateDynamicFeatureButtonState()
                    }
                    else -> {/* Do nothing in this example */
                    }
                }
            }
            manager.startInstall(request)
        }

        binding.tvCatSearchDynamicHome.setOnClickListener {
            val intent = Intent()
            intent.setClassName(BuildConfig.APPLICATION_ID, "com.aizid.themovie.searchmovie.SearchMovieActivity")
            startActivity(intent)
        }
    }

    private fun updateDynamicFeatureButtonState() {
        binding.tvCatSearchDynamicHome.isEnabled = manager.installedModules.contains(DYNAMIC_MODULE_NAME)
    }

    private fun initAdapter() {
        val adapter = AdapterHomeMovie(dataBindingComponent, appExecutors) {
                movieRes -> navigationControllerMain.navigateDetail(movieRes.id, movieRes.popular,
        movieRes.upcoming, movieRes.topRated, movieRes.nowPlaying)
        }
        this.adapter = adapter

        binding.rcvHomeFragmentMovie.adapter = adapter
        this.adapter = adapter
    }

    private fun initContributorList(viewModel: HomeViewModel) {
        when (option) {
            0 -> {
                viewModel.popularMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null && listResource.data.isNotEmpty()) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            1 -> {
                viewModel.upcomingMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null && listResource.data.isNotEmpty()) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            2 -> {
                viewModel.topratedMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null && listResource.data.isNotEmpty()) {
                        adapter.submitList(listResource.data)
                    } else {
                        adapter.submitList(emptyList())
                    }
                })
            }
            3 -> {
                viewModel.nowplayingMovie.observe(viewLifecycleOwner, Observer { listResource ->
                    if (listResource?.data != null && listResource.data.isNotEmpty()) {
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

    companion object {
        private const val DYNAMIC_MODULE_NAME = "ondemand"
    }

}
