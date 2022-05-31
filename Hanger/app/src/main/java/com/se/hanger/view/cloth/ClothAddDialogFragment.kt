package com.se.hanger.view.cloth

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.se.hanger.R
import com.se.hanger.databinding.FragmentDialogClothAddBinding
import com.se.hanger.utils.ScreenSizeProvider

class ClothAddDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentDialogClothAddBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogClothAddBinding.inflate(inflater)
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setClickListener()
        }
    }

    override fun onResume() {
        super.onResume()
        // full Screen code
        val width = (ScreenSizeProvider.getWidth(
            requireContext(),
            requireActivity().windowManager
        ))
        val height = (ScreenSizeProvider.getHeight(
            requireContext(),
            requireActivity().windowManager
        ))
        dialog?.window?.setLayout((width * 0.9).toInt(), (height * 0.9).toInt())
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun setClickListener() {
        binding.closeBtn.setOnClickListener(this)
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.close_btn -> {
                dismiss()
            }
        }
    }
}