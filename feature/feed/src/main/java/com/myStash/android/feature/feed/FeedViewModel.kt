package com.myStash.android.feature.feed

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

    init {
        fetch()
    }

    private fun fetch() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate
                reduce {
                    state.copy(
                        calenderDataList = setCalender(date.year, date.monthValue)
                    )
                }
            }
        }
    }

    fun onClickAgoCalender() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.minusMonths(1)

                reduce {
                    state.copy(
                        calenderDate = date,
                        calenderDataList = setCalender(date.year, date.monthValue)
                    )
                }
            }
        }
    }
    fun onClickNextCalender() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.plusMonths(1)

                reduce {
                    state.copy(
                        calenderDate = date,
                        calenderDataList = setCalender(date.year, date.monthValue)
                    )
                }
            }
        }
    }

    fun onClickDay(day: String) {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate

                reduce {
                    state.copy(
                        selectDate = LocalDate.of(date.year, date.monthValue, day.toInt())
                    )
                }
            }
        }
    }
}

fun MutableList<CalenderData>.addAllDayOfWeek() {
    val dayOfWeekList = mutableListOf(
        CalenderData.DayOfWeek("일"),
        CalenderData.DayOfWeek("월"),
        CalenderData.DayOfWeek("화"),
        CalenderData.DayOfWeek("수"),
        CalenderData.DayOfWeek("목"),
        CalenderData.DayOfWeek("금"),
        CalenderData.DayOfWeek("토"),
    )
    addAll(dayOfWeekList)
}

fun MutableList<CalenderData>.addAllSpacerDay(yearMonth: YearMonth) {
    val firstDayOfMonth = yearMonth.atDay(1).dayOfWeek.value % 7 // Sunday as 0
    repeat(firstDayOfMonth) {
        add(CalenderData.Spacer)
    }
}

fun setCalender(currentYear: Int, currentMonth: Int): MutableList<CalenderData> {
    val calenderDataList: MutableList<CalenderData> = mutableListOf()
    val yearMonth = YearMonth.of(currentYear, currentMonth)

    // 요일 데이터
    calenderDataList.addAllDayOfWeek()

    // 공백 데이터
    calenderDataList.addAllSpacerDay(yearMonth)

    // 날짜 데이터
    val daysInMonth = yearMonth.lengthOfMonth()
    (1..daysInMonth).forEach { day ->
        calenderDataList.add(
            CalenderData.Day(
                day = day.toString()
            )
        )
    }

    return calenderDataList
}