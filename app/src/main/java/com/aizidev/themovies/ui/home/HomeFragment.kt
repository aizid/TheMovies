package com.aizidev.themovies.ui.home

import androidx.lifecycle.ViewModelProviders
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider

import com.aizidev.themovies.R
import com.aizidev.themovies.activity.MainActivity
import com.aizidev.themovies.binding.FragmentDataBindingComponent
import com.aizidev.themovies.databinding.HomeFragmentBinding
import com.aizidev.themovies.di.component.Injectable
import com.aizidev.themovies.util.NavigationControllerMain
import com.aizidev.themovies.util.autoCleared
import javax.inject.Inject

class HomeFragment : Fragment(), Injectable, MainActivity.OnBackPressedListner {
    @Inject
    lateinit var navigationControllerMain: NavigationControllerMain

    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory
    private var binding by autoCleared<HomeFragmentBinding>()
    private var dataBindingComponent: DataBindingComponent = FragmentDataBindingComponent(this)

    private lateinit var viewModel: HomeViewModel

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
        binding = dataBinding

        return dataBinding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

    }

    override fun onBackPressed(): Boolean {
        return true
    }

}
