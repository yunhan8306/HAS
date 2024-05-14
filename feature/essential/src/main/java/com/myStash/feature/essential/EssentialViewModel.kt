package com.myStash.feature.essential

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.core.data.usecase.item.GetItemListUseCase
import com.myStash.core.data.usecase.tag.GetTagListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class EssentialViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
) : ContainerHost<EssentialScreenState, EssentialSideEffect>, ViewModel() {
    override val container: Container<EssentialScreenState, EssentialSideEffect> =
        container(EssentialScreenState())

    init {
        fetch()
    }

    private val itemList = getItemListUseCase.itemList
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

    private fun fetch() {
        intent {
            viewModelScope.launch {
                combine(itemList, tagTotalList) { itemList, tagTotalList ->
                    reduce {
                        state.copy(
                            itemList = itemList,
                            tagTotalList = tagTotalList
                        )
                    }
                }
            }
        }
    }

    fun onClick() {
        Log.d("qwe123", "click")
    }

}