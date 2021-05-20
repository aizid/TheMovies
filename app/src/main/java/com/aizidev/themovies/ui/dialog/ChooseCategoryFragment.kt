package com.aizidev.themovies.ui.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.DialogFragment
import com.aizidev.themovies.R

class ChooseCategoryFragment : DialogFragment() {

    companion object {
        fun newInstance() = ChooseCategoryFragment()
    }

    private lateinit var viewModel: ChooseCategoryViewModel

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.choose_category_fragment, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
//        viewModel = ViewModelProviders.of(this).get(ChooseCategoryViewModel::class.java)
        // TODO: Use the ViewModel
    }

}
