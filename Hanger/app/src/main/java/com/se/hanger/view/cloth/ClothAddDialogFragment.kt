package com.se.hanger.view.cloth

import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.*
import androidx.activity.result.ActivityResultLauncher
import androidx.activity.result.contract.ActivityResultContracts
import androidx.fragment.app.DialogFragment
import com.bumptech.glide.Glide
import com.github.drjacky.imagepicker.ImagePicker
import com.google.android.material.snackbar.Snackbar
import com.google.gson.Gson
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.data.model.*
import com.se.hanger.databinding.FragmentDialogClothAddBinding
import com.se.hanger.utils.ScreenSizeProvider
import com.se.hanger.view.adapter.TagAdapter
import com.se.hanger.view.main.MainActivity
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import okhttp3.internal.notify
import org.threeten.bp.LocalDate

class ClothAddDialogFragment : DialogFragment(), View.OnClickListener {
    private lateinit var binding: FragmentDialogClothAddBinding
    private lateinit var clothDB: ClothDatabase
    private lateinit var date: LocalDate
    private lateinit var adapter: TagAdapter
    private var photo: Photo? = null
    private var hasPhoto = false // 사진 추가 여부
    private var galleryLauncher: ActivityResultLauncher<Intent>? = null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentDialogClothAddBinding.inflate(inflater)
        clothDB = ClothDatabase.getInstance(requireContext())!!
        dialog?.window?.requestFeature(Window.FEATURE_NO_TITLE)
        adapter = TagAdapter(ArrayList())
        binding.tagRecyclerView.adapter = adapter
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setLauncher()
        setClickListener()
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
        with(binding) {
            /* 태그 추가 로직 */
            tagAddBtn.setOnClickListener {
                adapter.dataSet.add(tagAddEt.text.toString())
                adapter.notifyItemChanged(adapter.dataSet.size - 1)
            }

            /* 메인 사진 추가 로직 */
            clothImgAddBtn.setOnClickListener {
                // 갤러리 실행
                galleryLauncher?.launch(
                    ImagePicker.with(requireActivity())
                        .galleryOnly()
                        .createIntent()
                )
                hasPhoto = true
            }

            /* 취소 버튼 로직*/
            cancelBtn.setOnClickListener {
                dismiss()
            }

            /* 추가 버튼 이벤트 설정*/
            clothAddBtn.setOnClickListener {
                // DB 는 IO 작업이기 때문에 scope 열어줌
                // TODO 각종 ui 에서 데이터 가져와서 설정해주자
                if (hasPhoto){
                    CoroutineScope(Dispatchers.IO).launch {
                        val cloth = Cloth(
                            buyUrl = buyerEt.text.toString(),
                            clothSize = sizeSelectBtn.text.toString(),
                            clothName = "넣어줘야함",
                            clothMemo = memoEt.text.toString(),
                            clothPhoto = photo?.photoUriString!!,
                            dailyPhoto = listOf(photo!!),
                            tags = adapter.dataSet.map { data ->
                                Tag("", data)
                            },
                            categories = listOf(Category(Season.SPRING, CategoryCloth.ACCESSORY))
                        )
                        clothDB.clothDao().insert(cloth)
                        dismiss()
                    }

                }
                else{
                    showSnackBar("의류 사진을 선택해주세요!")
                }
            }

        }
    }

    private fun showSnackBar(msg: String) {
        Snackbar.make(binding.root, msg, Snackbar.LENGTH_LONG).show()
    }

    override fun onClick(view: View?) {
        when (view?.id) {
            R.id.close_btn -> {
                dismiss()
            }
        }
    }

    private fun setLauncher() {
        galleryLauncher =
            registerForActivityResult(ActivityResultContracts.StartActivityForResult()) {
                if (it.resultCode == Activity.RESULT_OK) {
                    // Use the uri to load the image
                    val uri = it.data?.data!!
                    // 갤러리에서 불러온 이미지 적용
                    Glide.with(requireContext()).load(uri).into(binding.photoIv)
                    photo =
                        Photo(
                            System.currentTimeMillis().toString(), uri.toString(),
                        )
                }
            }
    }
}