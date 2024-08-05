package com.myStash.android.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.Quadruple
import com.myStash.android.core.data.usecase.feed.GetFeedListUseCase
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.style.GetStyleListUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.model.CalenderData
import com.myStash.android.core.model.Feed
import com.myStash.android.core.model.filterDate
import com.myStash.android.core.model.getFeedByDate
import com.myStash.android.core.model.getUsedTagList
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.firstOrNull
import kotlinx.coroutines.flow.stateIn
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
    private val getFeedListUseCase: GetFeedListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val getHasListUseCase: GetHasListUseCase,
    private val getStyleListUseCase: GetStyleListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase
): ContainerHost<FeedScreenState, FeedSideEffect>, ViewModel() {
    override val container: Container<FeedScreenState, FeedSideEffect> =
        container(FeedScreenState())

    init {
        fetch()
    }

    private val feedTotalList = getFeedListUseCase.feedList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val tagTotalList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val hasTotalList = getHasListUseCase.hasList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val styleTotalList = getStyleListUseCase.styleList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val typeTotalList = getTypeListUseCase.typeList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(feedTotalList, styleTotalList, tagTotalList, typeTotalList) { feedTotalList, styleTotalList, tagTotalList, typeTotalList ->
                    Quadruple(feedTotalList, styleTotalList, tagTotalList, typeTotalList)
                }.collectLatest { (feedTotalList, styleTotalList, tagTotalList, typeTotalList) ->
                    val date = state.calenderDate
                    val feedList = feedTotalList.filterDate(date.year, date.monthValue)
                    val calenderList = setCalender(date.year, date.monthValue, feedList)
                    val selectedFeed = feedList.getFeedByDate(date)
                    val selectedStyle = styleTotalList.firstOrNull { it.id == selectedFeed?.styleId }
                    val tagList = selectedStyle?.getUsedTagList(tagTotalList) ?: emptyList()

                    reduce {
                        state.copy(
                            calenderDataList = calenderList,
                            selectedFeed = feedList.getFeedByDate(date),
                            selectedFeedTagList = tagList,
                            selectedFeedStyle = selectedStyle,
                            typeTotalList = typeTotalList
                        )
                    }
                }
            }
        }
    }

    fun onClickAgoCalender() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.minusMonths(1)
                val feedList = feedTotalList.value.filterDate(date.year, date.monthValue)
                val calenderList = setCalender(date.year, date.monthValue, feedList)
                reduce {
                    state.copy(
                        calenderDate = date,
                        calenderDataList = calenderList
                    )
                }
            }
        }
    }
    fun onClickNextCalender() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.plusMonths(1)
                val feedList = feedTotalList.value.filterDate(date.year, date.monthValue)
                val calenderList = setCalender(date.year, date.monthValue, feedList)
                reduce {
                    state.copy(
                        calenderDate = date,
                        calenderDataList = calenderList
                    )
                }
            }
        }
    }

    fun onClickDay(date: LocalDate) {
        intent {
            viewModelScope.launch {
                val selectedFeed = feedTotalList.value.filterDate(date.year, date.monthValue).getFeedByDate(date)
                val selectedStyle = styleTotalList.value.firstOrNull { it.id == selectedFeed?.styleId }
                val tagList = selectedStyle?.getUsedTagList(tagTotalList.value) ?: emptyList()

                reduce {
                    state.copy(
                        selectedDate = date,
                        selectedFeed = feedTotalList.value.getFeedByDate(date),
                        selectedFeedTagList = tagList,
                        selectedFeedStyle = selectedStyle,
                        typeTotalList = typeTotalList.value
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

fun setCalender(currentYear: Int, currentMonth: Int, feedList: List<Feed> = emptyList()): MutableList<CalenderData> {
    val calenderDataList: MutableList<CalenderData> = mutableListOf()
    val yearMonth = YearMonth.of(currentYear, currentMonth)

    // 요일 데이터
    calenderDataList.addAllDayOfWeek()

    // 공백 데이터
    calenderDataList.addAllSpacerDay(yearMonth)

    // 날짜 데이터
    val daysInMonth = yearMonth.lengthOfMonth()
    (1..daysInMonth).forEach { day ->
        feedList.firstOrNull { day == it.date.dayOfMonth }?.let { feed ->
            calenderDataList.add(
                CalenderData.RecodedDay(
                    day = day.toString(),
                    imageUri = feed.images[0]
                )
            )
        } ?: run {
            calenderDataList.add(
                CalenderData.Day(
                    day = day.toString()
                )
            )
        }
    }

    return calenderDataList
}