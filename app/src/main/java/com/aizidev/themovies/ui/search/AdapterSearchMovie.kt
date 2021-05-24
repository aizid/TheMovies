package com.aizidev.themovies.ui.search

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.R
import com.aizidev.themovies.databinding.LayoutItemRcvMovieHomeBinding
import com.aizidev.themovies.databinding.LayoutItemRcvMovieSearchBinding
import com.aizidev.themovies.ui.common.DataBoundListAdapter
import com.aizidev.themovies.vo.MovieRes

class AdapterSearchMovie(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val clickCallback: ((MovieRes) -> Unit)?
) : DataBoundListAdapter<MovieRes, LayoutItemRcvMovieSearchBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<MovieRes>() {
        override fun areItemsTheSame(oldItem: MovieRes, newItem: MovieRes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: MovieRes, newItem: MovieRes): Boolean {
            return oldItem.posterPath == newItem.posterPath
                    && oldItem.title == newItem.title
                    && oldItem.releaseDate == newItem.releaseDate
                    && oldItem.overview == newItem.overview
        }
    }
) {

    override fun createBinding(parent: ViewGroup): LayoutItemRcvMovieSearchBinding {
        val binding = DataBindingUtil
            .inflate<LayoutItemRcvMovieSearchBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_rcv_movie_search,
                parent,
                false,
                dataBindingComponent
            )

        binding.root.setOnClickListener {
            binding.posthome?.let {
                clickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: LayoutItemRcvMovieSearchBinding, item: MovieRes) {
        binding.posthome = item
    }
}