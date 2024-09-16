package com.myStash.android.feature.item.feed

import android.content.Intent
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.Quadruple
import com.myStash.android.common.util.isNotNull
import com.myStash.android.common.util.offer
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.data.usecase.feed.SaveFeedUseCase
import com.myStash.android.core.data.usecase.feed.UpdateFeedUseCase
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.style.GetStyleListUseCase
import com.myStash.android.core.data.usecase.tag.CheckAvailableTagUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.type.GetTypeListUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Feed
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.filterSelectTag
import com.myStash.android.core.model.getStyleScreenModel
import com.myStash.android.core.model.tagFoundFromSearchText
import com.myStash.android.feature.item.ItemConstants
import com.myStash.android.feature.item.item.ItemTab
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import java.time.LocalDate
import javax.inject.Inject

@HiltViewModel
class AddFeedViewModel @Inject constructor(
    private val checkAvailableTagUseCase: CheckAvailableTagUseCase,

    private val getTagListUseCase: GetTagListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,

    private val getStyleListUseCase: GetStyleListUseCase,
    private val getHasListUseCase: GetHasListUseCase,

    private val saveFeedUseCase: SaveFeedUseCase,
    private val updateFeedUseCase: UpdateFeedUseCase,
    private val stateHandle: SavedStateHandle,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<AddFeedScreenState, AddFeedSideEffect>, ViewModel() {
    override val container: Container<AddFeedScreenState, AddFeedSideEffect> =
        container(AddFeedScreenState())

    init {
        fetch()
        collectSearchText()
    }

    private val selectedTagList = mutableListOf<Tag>()

    val searchTextState = TextFieldState()

    private val searchTagList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
        .onEach { text ->
            tagTotalList.value.tagFoundFromSearchText(
                text = text,
                found = { tag -> selectedTagList.offer(tag) { tag.id == it.id } },
                complete = { searchTextState.setTextAndPlaceCursorAtEnd(it) }
            )
        }
        .map { search -> tagTotalList.value.filter { it.name.contains(search) } }
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

    private val hasTotalList = getHasListUseCase.hasList
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

    private val typeTotalList = getTypeListUseCase.typeList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val agoFeed = stateHandle.get<Feed?>(ItemConstants.CMD_FEED)
    private val agoStyleId = stateHandle.get<Long?>(ItemConstants.CMD_STYLE_ID)
    private val isEdit = stateHandle.get<String>(ItemConstants.CMD_EDIT_TAB_NAME) == ItemTab.FEED.tabName


    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(styleTotalList, hasTotalList, tagTotalList, typeTotalList) { styleList, hasList, tagList, typeList ->
                    Quadruple(styleList, hasList, tagList, typeList)
                }.collectLatest { (styleList, hasList, tagList, typeList) ->
                    reduce {
                        state.copy(
                            isEdit = isEdit,
                            styleList = styleList,
                            tagList = tagList,
                            selectedImageList = state.selectedImageList.ifEmpty { agoFeed?.images ?: emptyList() },
                            calenderDate = if(state.calenderDate.dayOfMonth != LocalDate.now().dayOfMonth) state.calenderDate else agoFeed?.date ?: LocalDate.now(),
                            selectedStyle = state.selectedStyle ?: agoStyleId.getStyleScreenModel(styleList),
                            typeTotalList = typeList,
                            tagTotalList = tagList
                        )
                    }
                }
            }
        }
    }

    private fun collectSearchText() {
        intent {
            viewModelScope.launch {
                searchTagList.collectLatest {
                    reduce {
                        state.copy(
                            tagList = it.toList(),
                            selectedTagList = selectedTagList.toList()
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: AddFeedScreenAction) {
        when(action) {
            is AddFeedScreenAction.UnselectImage -> unselectImage(action.uri)
            is AddFeedScreenAction.SelectStyle -> selectStyle(action.style)
            is AddFeedScreenAction.DeleteSearchText -> deleteSearchText()
            is AddFeedScreenAction.SelectTag -> selectTag(action.tag)
            is AddFeedScreenAction.SelectDate -> selectDate(action.date)
            is AddFeedScreenAction.SaveFeed -> if(isEdit) editFeed() else saveFeed()
            is AddFeedScreenAction.PrevMonth -> prevMonth()
            is AddFeedScreenAction.NextMonth -> nextMonth()
            else -> Unit
        }
    }

    private fun unselectImage(uri: String) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedImageList = state.selectedImageList.toMutableList().apply { offerOrRemove(uri) { it == uri } }.toList()
                    )
                }
            }
        }
    }

    private fun selectStyle(style: StyleScreenModel?) {
        intent {
            viewModelScope.launch {
                reduce {
                    deleteSearchText()
                    state.copy(
                        selectedStyle = style
                    )
                }
            }
        }
    }

    private fun deleteSearchText() {
        searchTextState.clearText()
    }

    private fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }
                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        styleList = styleTotalList.value.filterSelectTag(selectedTagList)
                    )
                }
            }
        }
    }

    private fun selectDate(date: LocalDate) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        calenderDate = date
                    )
                }
            }
        }
    }

    private fun saveFeed() {
        intent {
            viewModelScope.launch {
                val saveFeed = Feed(
                    images = state.selectedImageList,
                    date = state.calenderDate,
                    styleId = state.selectedStyle?.id!!,
                )
                val savedId = saveFeedUseCase.invoke(saveFeed)
                if(savedId.isNotNull()) {
                    Intent().apply {
                        putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_FEED)
                        postSideEffect(AddFeedSideEffect.Finish(this))
                    }
                }
            }
        }
    }

    private fun editFeed() {
        intent {
            viewModelScope.launch {
                val updateFeed = Feed(
                    id = agoFeed?.id,
                    images = state.selectedImageList,
                    date = state.calenderDate,
                    styleId = state.selectedStyle?.id!!,
                )
                val updateFeedId = updateFeedUseCase.invoke(updateFeed)
                if(updateFeedId.isNotNull()) {
                    Intent().apply {
                        putExtra(ItemConstants.CMD_COMPLETE, ItemConstants.CMD_FEED)
                        postSideEffect(AddFeedSideEffect.Finish(this))
                    }
                }
            }
        }
    }

    fun addImageList(imageList: List<String>) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedImageList = imageList
                    )
                }
            }
        }
    }

    private fun prevMonth() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.minusMonths(1)
                reduce {
                    state.copy(
                        calenderDate = date
                    )
                }
            }
        }
    }

    private fun nextMonth() {
        intent {
            viewModelScope.launch {
                val date = state.calenderDate.plusMonths(1)
                reduce {
                    state.copy(
                        calenderDate = date
                    )
                }
            }
        }
    }
}