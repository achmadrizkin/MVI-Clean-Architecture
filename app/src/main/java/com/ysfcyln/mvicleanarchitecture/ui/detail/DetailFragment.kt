package com.ysfcyln.mvicleanarchitecture.ui.detail

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.navArgs
import com.ysfcyln.base.BaseFragment
import com.ysfcyln.mvicleanarchitecture.databinding.FragmentDetailBinding
import com.ysfcyln.presentation.contract.DetailContract
import com.ysfcyln.presentation.vm.DetailViewModel
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collect

/**
 * Detail Fragment
 */
@AndroidEntryPoint
class DetailFragment : BaseFragment<FragmentDetailBinding>() {

    private val viewModel : DetailViewModel by viewModels()
    private val args : DetailFragmentArgs by navArgs()

    override val bindLayout: (LayoutInflater, ViewGroup?, Boolean) -> FragmentDetailBinding
        get() = FragmentDetailBinding::inflate

    override fun prepareView(savedInstanceState: Bundle?) {
        // do something
        initObservers()
        val id = args.post?.id
        Log.d("Ysf", id.toString())
    }

    /**
     * Initialize Observers
     */
    private fun initObservers() {
        lifecycleScope.launchWhenStarted {
            viewModel.uiState.collect {
                when (val state = it.commentsState) {
                    is DetailContract.CommentsState.Idle -> {
                        Log.d("Ysf", "Idle")
                    }
                    is DetailContract.CommentsState.Loading -> {
                        Log.d("Ysf", "Loading")
                    }
                    is DetailContract.CommentsState.Success -> {
                        val data = state.comments
                        Log.d("Ysf", "Success")
                    }
                }
            }
        }

        lifecycleScope.launchWhenStarted {
            viewModel.effect.collect {
                when (it) {
                    is DetailContract.Effect.ShowError -> {
                        val msg = it.message
                    }
                }
            }
        }
    }

}