package com.myStash.feature.essential

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.core.data.usecase.item.GetItemListUseCase
import com.myStash.core.data.usecase.item.SaveItemUseCase
import com.myStash.core.data.usecase.tag.GetTagListUseCase
import com.myStash.core.model.Image
import com.myStash.core.model.Item
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
class EssentialViewModel @Inject constructor(
    private val getItemListUseCase: GetItemListUseCase,
    private val getTagListUseCase: GetTagListUseCase,
    private val saveItemUseCase: SaveItemUseCase,
) : ContainerHost<EssentialScreenState, EssentialSideEffect>, ViewModel() {
    override val container: Container<EssentialScreenState, EssentialSideEffect> =
        container(EssentialScreenState())

    init {
        fetch()
    }

    var testImageList = listOf<Image>()
    var testImageCnt = 0

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
                    Pair(itemList, tagTotalList)
                }.collectLatest { (itemList, tagTotalList) ->
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

    fun testItemAdd() {
        viewModelScope.launch {
            val newItem = Item(
//                tags = tags,
//                brand = brand,
//                type = type,
                imagePath = testImageList[testImageCnt].uri.toString(),
            )
            saveItemUseCase.invoke(newItem)
            testImageCnt++
        }
    }

    fun onClick() {
        Log.d("qwe123", "click")
    }

}