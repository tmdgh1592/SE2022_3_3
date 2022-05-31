package com.se.hanger.view.calendar

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.fragment.app.DialogFragment
import com.se.hanger.R
import com.se.hanger.databinding.FragmentDialogPhotoBinding

class DailyPhotoDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentDialogPhotoBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentDialogPhotoBinding.inflate(inflater)
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

        dialog?.window?.setLayout(
            ViewGroup.LayoutParams.WRAP_CONTENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }

    private fun setClickListener() {

    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.close_btn -> {
                dismiss()
            }
        }
    }
}