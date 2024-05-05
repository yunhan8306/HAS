package com.myStash

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.myStash.core.data.usecase.tag.GetTagListUseCase
import com.myStash.core.data.usecase.tag.SaveTagUseCase
import com.myStash.core.model.Tag
import kotlinx.coroutines.launch
import javax.inject.Inject

class TestViewModel @Inject constructor (
    private val saveTagUseCase: SaveTagUseCase,
    private val getTagListUseCase: GetTagListUseCase
) : ViewModel() {

    fun test() {
        viewModelScope.launch {
            val testTag = Tag(name = "test1")
            val result = saveTagUseCase.invoke(testTag)
            Log.d("qwe123", "result - $result")

            val result2 = getTagListUseCase.invoke()
            Log.d("qwe123", "result2 - $result2")
        }
    }
}