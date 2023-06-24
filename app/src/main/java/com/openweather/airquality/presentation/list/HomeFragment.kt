package com.openweather.airquality.presentation.list

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.repeatOnLifecycle
import androidx.navigation.fragment.findNavController
import com.google.android.material.snackbar.Snackbar
import com.openweather.airquality.R
import com.openweather.airquality.databinding.FragmentHomeBinding
import com.openweather.airquality.domain.model.BaseEntity
import com.openweather.airquality.domain.model.ForcastEntity
import com.openweather.airquality.domain.utils.AppError
import com.openweather.airquality.presentation.list.viewstate.CurrentAirPollutionUIState
import com.openweather.airquality.presentation.list.viewstate.ForecastAirPollutionUIState
import com.openweather.airquality.util.CommonUtils
import com.openweather.airquality.util.convertDateAndTime
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch

@AndroidEntryPoint
class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!
    private val viewModel: HomeViewModel by viewModels()
    private var currentInfo: ForcastEntity? = null
    private val homeListAdapter = HomeListAdapter()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        _binding?.apply {
            forecastRecyclerview.apply {
                adapter = homeListAdapter
                isNestedScrollingEnabled = false
                setHasFixedSize(false)
            }

            topHeaderLayout.setOnClickListener {
                if (currentInfo != null) {
                    findNavController().navigate(
                        HomeFragmentDirections.actionHomeFragmentToDetailsFragment(
                            currentInfo,
                        ),
                    )
                }
            }
        }

        subscribeObserver()
    }

    private fun subscribeObserver() {
        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiForeCastAirPollutionState.collectLatest { uiState ->
                    when (uiState) {
                        is ForecastAirPollutionUIState.IsLoading -> handleLoading(uiState.isLoading)
                        is ForecastAirPollutionUIState.Success -> handleSuccessForecastAirPollutionInfo(
                            uiState.forecast,
                        )

                        is ForecastAirPollutionUIState.Error -> handleError(uiState.error)
                        is ForecastAirPollutionUIState.Init -> Unit
                    }
                }
            }
        }

        viewLifecycleOwner.lifecycleScope.launch {
            viewLifecycleOwner.repeatOnLifecycle(Lifecycle.State.STARTED) {
                viewModel.uiCurrentAirPollutionState.collectLatest { uiState ->
                    when (uiState) {
                        is CurrentAirPollutionUIState.IsLoading -> handleLoading(uiState.isLoading)
                        is CurrentAirPollutionUIState.Success -> handleSuccessCurrentAirPollutionInfo(
                            uiState.current,
                        )
                        is CurrentAirPollutionUIState.Error -> handleError(uiState.error)
                        is CurrentAirPollutionUIState.Init -> Unit
                    }
                }
            }
        }
    }

    private fun handleSuccessCurrentAirPollutionInfo(current: List<ForcastEntity>?) {
        if (current?.isNotEmpty() == true) {
            currentInfo = current[0]
            _binding?.apply {
                airQualityStatusTextview.text =
                    CommonUtils.getAirQualityText(currentInfo?.aqi)
                currentDateTextview.text =
                    currentInfo?.dt?.toLong()?.convertDateAndTime()
                airQualityStatusTextview.setTextColor(
                    CommonUtils.getAirQualityColor(
                        currentInfo?.aqi,
                    ),
                )
            }
        }
    }

    private fun handleSuccessForecastAirPollutionInfo(forecast: List<BaseEntity>?) {
        if (forecast?.isNotEmpty() == true) {
            homeListAdapter.submitList(forecast)
        }
    }

    private fun handleError(error: AppError) {
        when (error) {
            is AppError.GeneralFailure -> {
                showSnackBar(getString(R.string.something_wrong))
            }

            is AppError.NoInternetFailure -> {
                showSnackBar(getString(R.string.no_internet))
            }

            is AppError.ServerFailure -> {
                showSnackBar(error.message)
            }
        }
    }

    private fun showSnackBar(message: String?) {
        val snack = Snackbar.make(binding.root, message!!, Snackbar.LENGTH_LONG)
        snack.show()
    }

    private fun handleLoading(isLoading: Boolean) {
        binding.progressBar.visibility = if (isLoading) View.VISIBLE else View.GONE
    }
}
