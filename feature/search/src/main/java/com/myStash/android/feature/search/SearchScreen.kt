package com.myStash.android.feature.search

import android.text.InputType
import androidx.activity.compose.BackHandler
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.core.model.Tag
import com.myStash.android.core.model.testTagList
import com.myStash.android.design_system.ui.DevicePreviews
import com.myStash.android.design_system.ui.SearchTextField
import com.myStash.android.design_system.ui.TagDeleteChipItem
import com.myStash.android.design_system.ui.TagSelectChipItem

@Composable
fun SearchScreen(
    searchTextState: TextFieldState,
    selectTagList: List<Tag>,
    tagList: List<Tag>,
    select: (Tag) -> Unit,
    onBack: () -> Unit,
) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White)
            .clickable {  },
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .height(60.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(modifier = Modifier.size(50.dp)) {
                Text("Back")
            }
            Spacer(modifier = Modifier.width(10.dp))
            SearchTextField(
                modifier = Modifier.weight(1f),
                textState = searchTextState,
            )
        }
        Spacer(modifier = Modifier.width(10.dp))
        Text("적용된 태그")
        Spacer(modifier = Modifier.width(10.dp))
        LazyRow {
            items(selectTagList) { tag ->
                TagDeleteChipItem(
                    name = tag.name,
                    onClick = { select.invoke(tag) }
                )
            }
        }
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .height(10.dp)
                .background(Color.Gray)
        )
        Spacer(modifier = Modifier.width(10.dp))
        Text("사용한 태그")
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f)
        ) {
            tagList.forEach { tag ->
                val isSelected by remember(selectTagList) {
                    derivedStateOf {
                        selectTagList.contains(tag)
                    }
                }

                TagSelectChipItem(
                    name = tag.name,
                    isSelected = isSelected,
                    onClick = { select.invoke(tag) }
                )
            }
        }
    }
    BackHandler(onBack = onBack)
}

@DevicePreviews
@Composable
fun SearchScreenPreview() {
    SearchScreen(
        searchTextState = TextFieldState(),
        selectTagList = emptyList(),
        tagList = testTagList,
        select = {},
        onBack = {}
    )
}