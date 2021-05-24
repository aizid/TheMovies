package com.aizidev.themovies.ui.detail

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.databinding.DataBindingComponent
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.DiffUtil
import com.aizidev.themovies.AppExecutors
import com.aizidev.themovies.R
import com.aizidev.themovies.databinding.LayoutItemRcvReviewDetailBinding
import com.aizidev.themovies.ui.common.DataBoundListAdapter
import com.aizidev.themovies.vo.ReviewRes

class AdapterDetailReview(
    private val dataBindingComponent: DataBindingComponent,
    appExecutors: AppExecutors,
    private val clickCallback: ((ReviewRes) -> Unit)?
) : DataBoundListAdapter<ReviewRes, LayoutItemRcvReviewDetailBinding>(
    appExecutors = appExecutors,
    diffCallback = object : DiffUtil.ItemCallback<ReviewRes>() {
        override fun areItemsTheSame(oldItem: ReviewRes, newItem: ReviewRes): Boolean {
            return oldItem.id == newItem.id
        }

        override fun areContentsTheSame(oldItem: ReviewRes, newItem: ReviewRes): Boolean {
            return oldItem.author == newItem.author
                    && oldItem.content == newItem.content
        }
    }
) {

    override fun createBinding(parent: ViewGroup): LayoutItemRcvReviewDetailBinding {
        val binding = DataBindingUtil
            .inflate<LayoutItemRcvReviewDetailBinding>(
                LayoutInflater.from(parent.context),
                R.layout.layout_item_rcv_review_detail,
                parent,
                false,
                dataBindingComponent
            )

        binding.root.setOnClickListener {
            binding.resultsItem?.let {
                clickCallback?.invoke(it)
            }
        }
        return binding
    }

    override fun bind(binding: LayoutItemRcvReviewDetailBinding, item: ReviewRes) {
        binding.resultsItem = item
    }
}