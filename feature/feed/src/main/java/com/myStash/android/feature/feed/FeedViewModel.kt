package com.myStash.android.feature.feed

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.Quadruple
import com.myStash.android.core.data.usecase.feed.DeleteFeedUseCase
import com.myStash.android.core.data.usecase.feed.GetFeedListUseCase
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.style.GetStyleListUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.model.filterDate
import com.myStash.android.core.model.getFeedByDate
import com.myStash.android.core.model.getUsedTagList
import com.myStash.android.core.model.setCalender
import com.myStash.android.feature.feed.ui.FeedScreenAction
import com.myStash.android.feature.feed.ui.FeedScreenState
import com.myStash.android.feature.feed.ui.FeedSideEffect
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class FeedViewModel @Inject constructor(
    private val getFeedListUseCase: GetFeedListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val getHasListUseCase: GetHasListUseCase,
    private val getStyleListUseCase: GetStyleListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,
    private val deleteFeedUseCase: DeleteFeedUseCase,
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

    fun onAction(action: FeedScreenAction) {
        viewModelScope.launch {
            when(action) {
                is FeedScreenAction.PrevMonth -> prevMonth()
                is FeedScreenAction.NextMonth -> nextMonth()
                is FeedScreenAction.SelectDay -> selectDay(action.date)
                is FeedScreenAction.Delete -> deleteFeed()
                else -> Unit
            }
        }
    }

    private fun prevMonth() {
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
    private fun nextMonth() {
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

    private fun selectDay(date: LocalDate) {
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

    private fun deleteFeed() {
        intent {
            viewModelScope.launch {
                state.selectedFeed?.let { deleteFeedUseCase.invoke(it) }
            }
        }
    }
}