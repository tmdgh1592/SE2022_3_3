package com.se.hanger.view.calendar

import android.content.Context
import android.graphics.drawable.Drawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.gson.Gson
import com.prolificinteractive.materialcalendarview.*
import com.prolificinteractive.materialcalendarview.format.ArrayWeekDayFormatter
import com.prolificinteractive.materialcalendarview.format.MonthArrayTitleFormatter
import com.prolificinteractive.materialcalendarview.format.TitleFormatter
import com.se.hanger.R
import com.se.hanger.data.db.ClothDatabase
import com.se.hanger.databinding.FragmentCalendarBinding
import com.se.hanger.view.calendar.decorator.DailyDecorator
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import org.threeten.bp.LocalDate


class CalendarFragment : Fragment(), OnDateSelectedListener, OnPhotoChangedListener {

    private lateinit var binding: FragmentCalendarBinding
    private lateinit var clothDB: ClothDatabase

    companion object {
        const val DAILY_PHOTO_FRAGMENT_TAG = "DAILY_PHOTO_FRAGMENT_TAG"
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentCalendarBinding.inflate(inflater)
        clothDB = ClothDatabase.getInstance(requireContext())!!
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        setCalendar() // 캘린더 기본 설정
    }

    override fun onResume() {
        super.onResume()
        updateDots()
    }


    // DB에서 사진 데이터를 가져와서
    // 사진을 저장한 캘린더 날짜에 점을 찍는다.
    private fun updateDots() {
        // 데코레이터 초기화
        CoroutineScope(Dispatchers.Main).launch {
            binding.calendarView.removeDecorators()
        }

        CoroutineScope(Dispatchers.IO).launch {
            val dailyPhotoList = clothDB.dailyPhotoDao().getDailyPhotos()
            val photoDates = mutableListOf<CalendarDay>()

            // 캘린더에 점을 찍을 날짜를 가져온다 (사진을 저장한 날짜)
            dailyPhotoList?.forEach { dailyPhoto ->
                val day = CalendarDay.from(dailyPhoto.photoDate)
                Log.d("TAG", "updateDots: " + day)
                photoDates.add(day)
            }

            // 가져온 날짜에 점을 찍는다.
            withContext(Dispatchers.Main) {
                binding.calendarView.addDecorator(
                    DailyDecorator(
                        requireContext(),
                        photoDates,
                        DateColor.PRIMARY
                    )
                )
            }
        }
    }

    private fun setCalendar() {
        with(binding.calendarView) {
            // 월, 요일을 한글로 보이게 설정 (MonthArrayTitleFormatter의 작동을 확인하려면 밑의 setTitleFormatter()를 지운다)
            setTitleFormatter(MonthArrayTitleFormatter(resources.getTextArray(R.array.custom_months)))
            setWeekDayFormatter(ArrayWeekDayFormatter(resources.getTextArray(R.array.custom_weekdays)))

            // 좌우 화살표 사이 연, 월의 폰트 스타일 설정
            setHeaderTextAppearance(R.style.CalendarWidgetHeader)

            // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
            addDecorators(DayDecorator(requireContext()))
            setOnDateChangedListener(this@CalendarFragment)

            // 좌우 화살표 가운데의 연/월이 보이는 방식 커스텀
            setTitleFormatter(TitleFormatter { day ->
                // CalendarDay라는 클래스는 LocalDate 클래스를 기반으로 만들어진 클래스다
                // 때문에 MaterialCalendarView에서 연/월 보여주기를 커스텀하려면 CalendarDay 객체의 getDate()로 연/월을 구한 다음 LocalDate 객체에 넣어서
                // LocalDate로 변환하는 처리가 필요하다
                val inputText: LocalDate = day.date
                val calendarHeaderElements = inputText.toString().split("-")
                val calendarHeaderBuilder = StringBuilder()
                calendarHeaderBuilder.append(calendarHeaderElements[0])
                    .append(" ")
                    .append(calendarHeaderElements[1])
                calendarHeaderBuilder.toString()
            })
        }
    }

    /* 선택된 요일의 background를 설정하는 Decorator 클래스 */
    private class DayDecorator(context: Context) : DayViewDecorator {
        private val drawable: Drawable?

        // true를 리턴 시 모든 요일에 내가 설정한 드로어블이 적용된다
        override fun shouldDecorate(day: CalendarDay): Boolean {
            return true
        }

        // 일자 선택 시 내가 정의한 드로어블이 적용되도록 한다
        override fun decorate(view: DayViewFacade) {
            view.setSelectionDrawable(drawable!!)
            //            view.addSpan(new StyleSpan(Typeface.BOLD));   // 달력 안의 모든 숫자들이 볼드 처리됨
        }

        init {
            drawable = ContextCompat.getDrawable(context, R.drawable.calendar_selector)
        }
    }

    override fun onDateSelected(
        widget: MaterialCalendarView,
        date: CalendarDay,
        selected: Boolean
    ) {
        val localDate = date.date // 전달할 캘린더 선택 날짜
        // 의류 CRUD를 할 Fragment
        val dailyPhotoDialog = DailyPhotoDialogFragment().apply {
            isCancelable = false
            arguments = Bundle().apply { putString("date", Gson().toJson(localDate)) }
            setOnPhotoChangedListener(this@CalendarFragment)
        }
        dailyPhotoDialog.show(parentFragmentManager, DAILY_PHOTO_FRAGMENT_TAG)

    }

    override fun onChanged(isChanged: Boolean) {
        // Dialog가 닫힐 때, 캘린더에 있는 점들 갱신
        if (isChanged) {
            updateDots()
        }
    }

}

interface OnPhotoChangedListener {
    fun onChanged(isChanged: Boolean)
}