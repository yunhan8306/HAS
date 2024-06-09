package com.myStash.android.feature.feed

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import java.time.YearMonth
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(

): ContainerHost<FeedScreenState, FeedSideEffect>, ViewModel() {
    override val container: Container<FeedScreenState, FeedSideEffect> =
        container(FeedScreenState())

    private val calenderDataList: MutableList<CalenderData> = mutableListOf()

    init {
        test()
    }
    fun test() {
        intent {
            viewModelScope.launch {
                val currentDate = LocalDate.now()
                val currentYear = currentDate.year
                val currentMonth = currentDate.monthValue

                val yearMonth = YearMonth.of(currentYear, currentMonth)

                val dayOfWeekList = mutableListOf(
                    CalenderData.DayOfWeek("일"),
                    CalenderData.DayOfWeek("월"),
                    CalenderData.DayOfWeek("화"),
                    CalenderData.DayOfWeek("수"),
                    CalenderData.DayOfWeek("목"),
                    CalenderData.DayOfWeek("금"),
                    CalenderData.DayOfWeek("토"),
                )
                // 요일 데이터
                calenderDataList.addAll(dayOfWeekList)

                // 공백 데이터
                val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value % 7 // Sunday as 0
                repeat(firstDayOfMonth) {
                    calenderDataList.add(CalenderData.Spacer)
                }

                // 요일 데이터
                val daysInMonth = yearMonth.lengthOfMonth()
                (1..daysInMonth).forEach { day ->
                    calenderDataList.add(
                        CalenderData.Day(
                            day = day.toString()
                        )
                    )
                }

                Log.d("qwe123", "calenderDataList - $calenderDataList")
                Log.d("qwe123", "calenderDataList - ${calenderDataList.size}")

                reduce {
                    state.copy(
                        calenderDataList = calenderDataList
                    )
                }
            }
        }
    }
}