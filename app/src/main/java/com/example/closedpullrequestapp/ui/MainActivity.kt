package com.example.closedpullrequestapp.ui

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.activity.viewModels
import androidx.core.view.isVisible
import androidx.lifecycle.lifecycleScope
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.closedpullrequestapp.R
import com.example.closedpullrequestapp.databinding.ActivityMainBinding
import com.example.closedpullrequestapp.utils.UiEvent
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

@AndroidEntryPoint
class MainActivity : AppCompatActivity() {
    private val viewModel: PullRequestViewModel by viewModels()
    lateinit var activityMainBinding: ActivityMainBinding
    lateinit var pullRequestAdapter: PullRequestAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        activityMainBinding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(activityMainBinding.root)

        setUpRecyclerView(this)

        viewModel.getList()

        lifecycleScope.launch(Dispatchers.Main) {
            viewModel.uiState.collect { state ->

                when (state) {
                    is UiEvent.Empty -> {
                        stopLoading()
                    }

                    is UiEvent.Loading -> {
                        showLoading()
                    }

                    is UiEvent.Success -> {
                        state.result?.let { result ->
                            if(!result.isNullOrEmpty()){
                                pullRequestAdapter.submitList(result)
                            }
                        }
                        stopLoading()
                    }
                    is UiEvent.Failure -> {
                        stopLoading()
                        Snackbar.make(
                            findViewById(android.R.id.content),
                            state.errorText,
                            Snackbar.LENGTH_LONG
                        ).show()
                    }
                }
            }
        }
    }

    private fun showLoading() {
        activityMainBinding.progressCircular.isVisible = true
    }

    private fun stopLoading() {
        activityMainBinding.progressCircular.isVisible = false
    }


    private fun setUpRecyclerView(context: Context) {
        pullRequestAdapter = PullRequestAdapter()
        activityMainBinding.pullRequestRv.apply {
            adapter = pullRequestAdapter
            layoutManager = LinearLayoutManager(context)
        }
    }

}