package com.aizidev.themovies.ui.detail

import android.os.Bundle
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
import com.aizidev.themovies.databinding.DetailFragmentBinding
import com.aizidev.themovies.di.component.Injectable
import com.aizidev.themovies.util.KEY_PREFF_TOKEN
import com.aizidev.themovies.util.NavigationControllerMain
import com.aizidev.themovies.util.autoCleared
import javax.inject.Inject

class DetailFragment : Fragment(), Injectable, MainActivity.OnBackPressedListner {
    @Inject
    lateinit var appExecutors: AppExecutors

    @Inject
    lateinit var navigationControllerMain: NavigationControllerMain

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding by autoCleared<DetailFragmentBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)
    private val detailViewModel: DetailViewModel by viewModels {
        viewModelFactory
    }
    private var adapter by autoCleared<AdapterDetailReview>()

    private var idMovie: Int = 0
    private var popular: Boolean = false
    private var upcoming: Boolean = false
    private var topRated: Boolean = false
    private var nowPlaying: Boolean = false

    fun newInstance(id: Int, popular: Boolean, upcoming: Boolean,
                    topRated: Boolean, nowPlaying: Boolean): DetailFragment {
        val detailFragment = DetailFragment()
        detailFragment.idMovie = id
        detailFragment.popular = popular
        detailFragment.upcoming = upcoming
        detailFragment.topRated = topRated
        detailFragment.nowPlaying = nowPlaying
        return detailFragment
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val dataBinding = DataBindingUtil.inflate<DetailFragmentBinding>(
            inflater,
            R.layout.detail_fragment,
            container,
            false,
            dataBindingComponent
        )
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        initAdapter()
        initComponent()
        initData()
        initContributorList(detailViewModel)
    }

    private fun initData() {
        detailViewModel.setToken(KEY_PREFF_TOKEN)
        detailViewModel.setMovieId(idMovie)
        detailViewModel.setPopular(popular)
        detailViewModel.setUpcoming(upcoming)
        detailViewModel.setNowPlaying(nowPlaying)
        detailViewModel.setTopRated(topRated)
        binding.lifecycleOwner = viewLifecycleOwner
    }

    private fun initAdapter() {
        val adapter = AdapterDetailReview(dataBindingComponent, appExecutors) {}
        this.adapter = adapter

        binding.rcvDetailFragmentReview.adapter = adapter
        this.adapter = adapter
    }

    private fun initContributorList(viewModel: DetailViewModel) {
        detailViewModel.movieDetail.observe(viewLifecycleOwner, Observer { result ->
            if (result?.data != null) {
                binding.movieRes = result.data
            }
        })

        viewModel.reviewMovie.observe(viewLifecycleOwner, Observer { listResource ->
            if (listResource?.data != null && listResource.data.isNotEmpty()) {
                binding.tvDetailMovieNoData.visibility = View.GONE
                adapter.submitList(listResource.data)
            } else {
                binding.tvDetailMovieNoData.visibility = View.VISIBLE
                adapter.submitList(emptyList())
            }
        })
    }

    private fun initComponent() {
        binding.ivDetailMovieFavorite.setOnClickListener {

        }
    }

    override fun onBackPressed(): Boolean {
        return false
    }

}
