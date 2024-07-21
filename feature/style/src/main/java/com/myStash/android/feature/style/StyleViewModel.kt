package com.myStash.android.feature.style

import android.app.Application
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.foundation.text2.input.textAsFlow
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.common.util.offerOrRemove
import com.myStash.android.core.data.usecase.has.GetHasListUseCase
import com.myStash.android.core.data.usecase.has.SaveHasUseCase
import com.myStash.android.core.data.usecase.style.GetStyleListUseCase
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.di.DefaultDispatcher
import com.myStash.android.core.di.IoDispatcher
import com.myStash.android.core.model.Style.Companion.toStyleScreenModel
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.filterSelectTag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class StyleViewModel @Inject constructor(
    private val application: Application,
    private val getStyleListUseCase: GetStyleListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val getHasListUseCase: GetHasListUseCase,
    private val saveHasUseCase: SaveHasUseCase,
    // dispatcher
    @DefaultDispatcher private val defaultDispatcher: CoroutineDispatcher,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
) : ContainerHost<StyleScreenState, StyleSideEffect>, ViewModel() {
    override val container: Container<StyleScreenState, StyleSideEffect> =
        container(StyleScreenState())

    init {
        fetch()
    }

    val searchTextState = TextFieldState()
    private val searchTagList = searchTextState
        .textAsFlow()
        .debounce(500)
        .flowOn(defaultDispatcher)
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

    private val selectedTagList = mutableListOf<Tag>()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(styleTotalList, tagTotalList, hasTotalList) { styleList, tagTotalList, hasTotalList ->
                    Triple(styleList, tagTotalList, hasTotalList)
                }.collectLatest { (styleList, tagTotalList, hasTotalList) ->
                    reduce {
                        state.copy(
                            styleList = styleList.map { it.toStyleScreenModel(hasTotalList) },
                            totalTagList = tagTotalList,
                        )
                    }
                }
                collectSearchText()
            }
        }
    }

    private fun collectSearchText() {
        intent {
            viewModelScope.launch {
                searchTagList.collectLatest {
                    reduce {
                        state.copy(totalTagList = it.toList())
                    }
                }
            }
        }
    }

    fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }

                val styleList = styleTotalList.value
                    .filterSelectTag(selectedTagList, hasTotalList.value)
                    .map { it.toStyleScreenModel(hasTotalList.value) }

                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        styleList = styleList
                    )
                }
            }
        }
    }
}