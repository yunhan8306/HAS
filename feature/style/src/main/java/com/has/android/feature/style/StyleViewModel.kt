package com.has.android.feature.style

import android.app.Application
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.has.android.common.util.offerOrRemove
import com.has.android.core.model.Style.Companion.toStyle
import com.has.android.core.model.StyleScreenModel
import com.has.android.core.model.Tag
import com.has.android.core.model.filterSelectTag
import com.has.android.core.model.getTagList
import com.has.android.core.model.sortSelectedTagList
import com.has.android.domain.database.database.usecase.style.DeleteStyleUseCase
import com.has.android.domain.database.database.usecase.style.GetStyleListUseCase
import com.has.android.domain.database.database.usecase.tag.GetTagListUseCase
import com.has.android.domain.database.database.usecase.type.GetTypeListUseCase
import com.has.android.feature.style.ui.StyleScreenAction
import com.has.android.feature.style.ui.StyleScreenState
import com.has.android.feature.style.ui.StyleSideEffect
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
import javax.inject.Inject

@HiltViewModel
class StyleViewModel @Inject constructor(
    private val application: Application,
    private val getStyleListUseCase: GetStyleListUseCase,
    private val getTypeListUseCase: GetTypeListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val deleteStyleUseCase: DeleteStyleUseCase,
) : ContainerHost<StyleScreenState, StyleSideEffect>, ViewModel() {
    override val container: Container<StyleScreenState, StyleSideEffect> =
        container(StyleScreenState())

    init {
        fetch()
    }

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

    private val tagTotalList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private val selectedTagList = mutableListOf<Tag>()

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(styleTotalList, typeTotalList, tagTotalList) { styleList, typeList, tagTotalList ->
                    Triple(styleList, typeList, tagTotalList)
                }.collectLatest { (styleList, typeList, tagTotalList) ->
                    reduce {
                        state.copy(
                            styleList = styleList,
                            totalTypeList = typeList,
                            totalTagList = tagTotalList,
                        )
                    }
                }
            }
        }
    }

    fun onAction(action: StyleScreenAction) {
        when(action) {
            is StyleScreenAction.SelectStyle -> selectStyle(action.style)
            is StyleScreenAction.SelectTag -> selectTag(action.tag)
            is StyleScreenAction.SetTagList -> setTagList(action.tagIdList)
            is StyleScreenAction.ResetSelectStyle -> resetSelectStyle()
            is StyleScreenAction.TagToggle -> toggleTag()
            is StyleScreenAction.DeleteStyle -> deleteStyle(action.style)
            else -> Unit
        }
    }

    private fun selectTag(tag: Tag) {
        intent {
            viewModelScope.launch {
                selectedTagList.offerOrRemove(tag) { it.name == tag.name }
                reduce {
                    state.copy(
                        isFoldTag = true,
                        totalTagList = state.totalTagList.sortSelectedTagList(selectedTagList, true),
                        selectedTagList = selectedTagList.toList(),
                        styleList = styleTotalList.value.filterSelectTag(selectedTagList)
                    )
                }
            }
        }
    }

    private fun setTagList(tagIdList: List<Long>) {
        intent {
            viewModelScope.launch {
                selectedTagList.apply {
                    clear()
                    addAll(tagIdList.getTagList(state.totalTagList))
                }

                reduce {
                    state.copy(
                        selectedTagList = selectedTagList.toList(),
                        styleList = styleTotalList.value.filterSelectTag(selectedTagList)
                    )
                }
            }
        }
    }

    private fun selectStyle(style: StyleScreenModel?) {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedStyle = if(state.selectedStyle == style) null else style
                    )
                }
            }
        }
    }

    private fun resetSelectStyle() {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        selectedStyle = null
                    )
                }
            }
        }
    }

    private fun toggleTag() {
        intent {
            viewModelScope.launch {
                reduce {
                    state.copy(
                        isFoldTag = !state.isFoldTag,
                        totalTagList = state.totalTagList.sortSelectedTagList(selectedTagList, state.isFoldTag),
                    )
                }
            }
        }
    }

    private fun deleteStyle(style: StyleScreenModel) {
        viewModelScope.launch {
            deleteStyleUseCase.invoke(style.toStyle())
        }
    }
}