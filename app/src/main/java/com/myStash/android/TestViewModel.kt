package com.myStash.android

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.android.core.data.usecase.tag.GetTagListUseCase
import com.myStash.android.core.data.usecase.tag.SaveTagUseCase
import com.myStash.android.core.model.Tag
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.Container
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class TestViewModel @Inject constructor (
    private val saveTagUseCase: SaveTagUseCase,
    private val getTagListUseCase: GetTagListUseCase,
) : ContainerHost<TestScreenState, TestSideEffect>, ViewModel() {

    override val container: Container<TestScreenState, TestSideEffect> = container(TestScreenState())
    init {
        initTest()
    }

    private val tagList = getTagListUseCase.tagList
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5_000L),
            initialValue = emptyList()
        )

    private fun initTest() {
        intent {
            viewModelScope.launch {
                tagList.collectLatest {
                    reduce { state.copy(data = it) }
                }
            }
        }
    }

    fun saveTest() {
        viewModelScope.launch {
            val testTag = Tag(name = "test1")
            val result = saveTagUseCase.invoke(testTag)
            Log.d("qwe123", "result - $result")
        }
    }
}

data class TestScreenState(
    val data: List<Tag> = emptyList()
)

sealed interface TestSideEffect