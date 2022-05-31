package com.se.hanger.view.calendar

import android.app.Activity.RESULT_OK
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.net.Uri
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.gson.Gson
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.data.db.DailyPhotoDao
import com.se.hanger.data.model.DailyPhoto
import com.se.hanger.data.model.Photo
import com.se.hanger.databinding.FragmentDialogPhotoBinding
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.threeten.bp.LocalDate

class DailyPhotoDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentDialogPhotoBinding
    private lateinit var date: LocalDate
    private lateinit var dailyPhotoDao: DailyPhotoDao
    private var dailyPhoto: DailyPhoto? = null
    private var galleryLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        date = Gson().fromJson(arguments?.getString("date"), LocalDate::class.java)
        binding = FragmentDialogPhotoBinding.inflate(inflater)
        dailyPhotoDao = ClothDatabase.getInstance(requireContext())!!.dailyPhotoDao()
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        with(binding) {
            setClickListener()
            setLauncher()
        }
    }

    private fun setLauncher() {
        galleryLauncher = registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == RESULT_OK) {
                    // Use the uri to load the image
                    val uri = it.data?.data!!
                    // 갤러리에서 불러온 이미지 적용
                    Glide.with(requireContext()).load(uri).into(binding.photoIv)

                    dailyPhoto = DailyPhoto(
                        Photo(
                            System.currentTimeMillis().toString(), uri.toString()
                        ), date
                    )

                }
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
        binding.galleryBtn.setOnClickListener(this)
        binding.cancelBtn.setOnClickListener(this)
        binding.applyBtn.setOnClickListener(this)
    }

    private fun insertPhoto(dailyPhoto: DailyPhoto) {
        CoroutineScope(Dispatchers.IO).launch {
            dailyPhotoDao.insert(dailyPhoto)
            dismissAllowingStateLoss()
        }
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.gallery_btn -> {
                // 갤러리 실행
                galleryLauncher?.launch(
                    ImagePicker.with(requireActivity())
                        .galleryOnly()
                        .createIntent()
                )
            }
            R.id.cancel_btn -> dismissAllowingStateLoss()
            R.id.apply_btn -> {
                dailyPhoto?.let { insertPhoto(it) }
            }
        }
    }
}