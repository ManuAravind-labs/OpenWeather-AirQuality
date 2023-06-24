package com.openweather.airquality.presentation.details

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.openweather.airquality.R
import com.openweather.airquality.databinding.FragmentDetailsBinding
import com.openweather.airquality.domain.model.Component
import com.openweather.airquality.domain.model.ForcastEntity

class DetailsFragment : Fragment() {

    private var _binding: FragmentDetailsBinding? = null
    private val binding get() = _binding!!
    private var entity: ForcastEntity? = null
    private val args: DetailsFragmentArgs by navArgs()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View {
        _binding = FragmentDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        entity = args.forecast
        binding.toolbarHome.setNavigationOnClickListener {
            findNavController().popBackStack()
        }

        val arrayList = ArrayList<Component>()
        entity?.apply {
            co?.let {
                arrayList.add(Component(getString(R.string.co), "$it μg/m3"))
            }
            no?.let {
                arrayList.add(Component(getString(R.string.no), "$it μg/m3"))
            }
            no2?.let {
                arrayList.add(Component(getString(R.string.no2), "$it μg/m3"))
            }
            o3?.let {
                arrayList.add(Component(getString(R.string.o3), "$it μg/m3"))
            }
            so2?.let {
                arrayList.add(Component(getString(R.string.so2), "$it μg/m3"))
            }
            pm10?.let {
                arrayList.add(Component(getString(R.string.pm10), "$it μg/m3"))
            }
            pm25?.let {
                arrayList.add(Component(getString(R.string.pm25), "$it μg/m3"))
            }
            nh3?.let {
                arrayList.add(Component(getString(R.string.nh3), "$it μg/m3"))
            }
        }
        val adapter = ComponentsAdapter()
        _binding?.componentList?.adapter = adapter
        adapter.submitList(arrayList)
    }
}
