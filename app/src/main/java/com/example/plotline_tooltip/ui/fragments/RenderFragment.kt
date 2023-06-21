package com.example.plotline_tooltip.ui.fragments

import android.graphics.Color
import android.os.Bundle
import android.os.Handler
import android.os.Looper
import android.view.*
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.example.plotline_tooltip.data.db.TooltipDataEntity
import com.example.plotline_tooltip.databinding.FragmentRendererBinding
import com.example.plotline_tooltip.ui.tooltip.TooltipHelper
import com.example.plotline_tooltip.ui.viewmodels.TooltipViewModel
import com.example.plotline_tooltip.ui.viewmodels.TooltipViewModelFactory

class RenderFragment : Fragment() {
    private lateinit var sharedViewModel: TooltipViewModel
    private lateinit var tooltipHandler: Handler
    private lateinit var tooltipHelper: TooltipHelper
    private lateinit var tooltipRunnable: Runnable
    private lateinit var binding: FragmentRendererBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        sharedViewModel = ViewModelProvider(requireActivity(), TooltipViewModelFactory(requireContext()))[TooltipViewModel::class.java]
        binding = FragmentRendererBinding.inflate(inflater, container, false)
        tooltipHelper = TooltipHelper(requireContext())
        tooltipHandler = Handler(Looper.getMainLooper())

        return binding.root
    }
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        activity?.window?.statusBarColor = Color.parseColor("#40000000")
        val defaultTooltipData = TooltipDataEntity(
            buttonId = "id",
            isVisible = true,
            image = "https://picsum.photos/id/237/200/300",
            text = "Hi Abhay's there",
            textSize = 16,
            padding = 10,
            backgroundColor = "#000000",
            textColor = "#FFFFFF",
            cornerRadius = 20,
            toolTipWidth = 400,
            arrowWidth = 30,
            arrowHeight = 60
        )
        tooltipRunnable = Runnable {
            tooltipHelper.hideTooltip()
        }



        binding.buttonLeftTop.setOnClickListener {
            val tooltipData = getTooltipDataById("Button 1") ?: defaultTooltipData
            tooltipHelper.showTooltip(binding.buttonLeftTop, tooltipData)
        }

        binding.buttonRightTop.setOnClickListener {
            val tooltipData = getTooltipDataById("Button 2") ?: defaultTooltipData
            tooltipHelper.showTooltip(binding.buttonRightTop, tooltipData)
        }

        binding.buttonCenter.setOnClickListener {
            val tooltipData = getTooltipDataById("Button 3") ?: defaultTooltipData
            tooltipHelper.showTooltip(binding.buttonCenter, tooltipData)
        }

        binding.buttonLeftBottom.setOnClickListener {
            val tooltipData = getTooltipDataById("Button 4") ?: defaultTooltipData
            tooltipHelper.showTooltip(binding.buttonLeftBottom, tooltipData)
        }

        binding.buttonRightBottom.setOnClickListener {
            val tooltipData = getTooltipDataById("Button 5") ?: defaultTooltipData
            tooltipHelper.showTooltip(binding.buttonRightBottom, tooltipData)
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        tooltipHandler.removeCallbacks(tooltipRunnable)
    }

    private fun getTooltipDataById(id: String) : TooltipDataEntity? {
        var tooltipData: TooltipDataEntity? = null

        sharedViewModel.allTooltipData.observe(viewLifecycleOwner) { data ->
            tooltipData = data.find { item -> item.buttonId == id }
        }

        return tooltipData
    }
}