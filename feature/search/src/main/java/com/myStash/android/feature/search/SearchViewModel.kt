package com.myStash.android.feature.search

import android.content.Intent
import android.util.Log
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.clearText
import androidx.compose.foundation.text2.input.setTextAndPlaceCursorAtEnd
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.common.util.removeBlank
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.style.GetStyleListUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.model.Has
import com.myStash.android.core.model.StyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.filterSelectTag
import com.myStash.android.core.model.getTagList
import com.myStash.android.core.model.selectTag
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
import javax.inject.Inject

@HiltViewModel
class SearchViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val getTagListUseCase: GetTagListUseCase,
    private val getHasListUseCase: GetHasListUseCase,
    private val getStyleListUseCase: GetStyleListUseCase,
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
): ContainerHost<SearchScreenState, SearchSideEffect>, ViewModel() {
    override val container: Container<SearchScreenState, SearchSideEffect> =
        container(SearchScreenState())

    init {
        fetch()
        collectSearchText()
    }

    val searchTextState = TextFieldState()
    private val selectedTagList = mutableListOf<Tag>()

    private val searchTagList = searchTextState
        .textAsFlow()
        .flowOn(defaultDispatcher)
        .onEach { text -> searchTextState.setTextAndPlaceCursorAtEnd(text.removeBlank()) }
        .map { search -> tagTotalList.value.filter { it.name.contains(search) } }
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

    private val type = savedStateHandle.get<String>(SearchConstants.TYPE) ?: SearchConstants.HAS
    private val selectedTagIdList = savedStateHandle.get<Array<Long>>(SearchConstants.SELECTED_TAG_LIST)?.toList() ?: emptyList()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(tagTotalList, hasTotalList, styleTotalList) { tagTotalList, hasTotalList, styleTotalList ->
                    Triple(tagTotalList, hasTotalList, styleTotalList)
                }.collectLatest { (tagTotalList, hasTotalList, styleTotalList) ->
                    if(selectedTagList.isEmpty()) {
                        selectedTagList.addAll(selectedTagIdList.getTagList(tagTotalList))
                    }
                    val cntText = getCnt(hasTotalList, styleTotalList)

                    reduce {
                        state.copy(
                            totalTagList = tagTotalList,
                            searchTagList = tagTotalList,
                            selectedTagList = selectedTagList.toList(),
                            selectedCntText = cntText
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
                        state.copy(searchTagList = it.toList())
                    }
                }
            }
        }
    }

    fun onAction(action: SearchAction) {
        when(action) {
            is SearchAction.SelectTag -> {
                selectTag(action.tag)
            }
            is SearchAction.Confirm -> {
                complete()
            }
            is SearchAction.DeleteText -> {
                deleteSearchText()
            }
            else -> Unit
        }
    }

    private fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }

                val cntText = getCnt(hasTotalList.value, styleTotalList.value)
                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        selectedCntText = cntText
                    )
                }
            }
        }
    }

    private fun complete() {
        intent {
            viewModelScope.launch {
                Intent().apply {
                    putExtra(SearchConstants.SELECTED_TAG_LIST, selectedTagList.map { it.id!! }.toLongArray())
                    postSideEffect(SearchSideEffect.Complete(this))
                }
            }
        }
    }

    private fun deleteSearchText() {
        searchTextState.clearText()
    }

    private fun getCnt(hasTotalList: List<Has>, styleTotalList: List<StyleScreenModel>): String? {
        return if(selectedTagList.isEmpty()) {
            null
        } else {
            when(type) {
                SearchConstants.HAS -> {
                    val cnt = hasTotalList.selectTag(selectedTagList).size
                    Log.d("qwe123", "cnt - $cnt")
                    if(cnt > 0) "${cnt}개 Has 보기" else null
                }
                SearchConstants.STYLE -> {
                    val cnt = styleTotalList.filterSelectTag(selectedTagList).size
                    if(cnt > 0) "${cnt}개 Style 보기" else null
                }
                else -> null
            }
        }
    }
}

data class SearchScreenState(
    val totalTagList: List<Tag> = emptyList(),
    val selectedTagList: List<Tag> = emptyList(),
    val searchTagList: List<Tag> = emptyList(),
    val selectedCntText: String? = null,
)

sealed interface SearchSideEffect {
    data class Complete(val intent: Intent): SearchSideEffect
}

sealed interface SearchAction {
    data class SelectTag(val tag: Tag): SearchAction
    object Confirm: SearchAction
    object DeleteText: SearchAction
    object Finish: SearchAction
}