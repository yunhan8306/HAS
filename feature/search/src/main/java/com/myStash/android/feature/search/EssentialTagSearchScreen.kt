package com.myStash.android.feature.search

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
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text2.input.TextFieldState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.myStash.android.core.model.Tag
import com.myStash.android.design_system.ui.SearchTextField2
import com.myStash.android.design_system.ui.tag.TagDeleteChipItem
import com.myStash.android.design_system.ui.TagSelectChipItem

@Composable
fun EssentialTagSearchScreen(
    searchTextState: TextFieldState,
    selectTagList: List<Tag>,
    tagList: List<Tag>,
    select: (Tag) -> Unit,
    onBack: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color.White)
            .clickable { },
    ) {
        Row(
            modifier = Modifier
                .padding(start = 12.dp)
                .fillMaxWidth()
                .height(40.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Box(
                modifier = Modifier
                    .size(24.dp)
                    .background(Color.Black)
                    .clickable { onBack.invoke() }
            )
        }

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .shadow(
                    shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp),
                    elevation = 10.dp,
                    spotColor = Color.Black,
                    ambientColor = Color(0x14000000)
                )
                .fillMaxWidth()
                .weight(1f)
                .background(
                    color = Color(0xFFFFFFFF),
                    shape = RoundedCornerShape(topEnd = 24.dp, topStart = 24.dp)
                )
                .padding(top = 24.dp)
        ) {
            // 상단
            Column(
                modifier = Modifier.padding(bottom = 12.dp)
            ) {
                SearchTextField2(
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 6.dp),
                    textState = searchTextState,
                )
                LazyRow(
                    modifier = Modifier.height(40.dp),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    item { Spacer(modifier = Modifier.width(12.dp)) }
                    items(selectTagList) { tag ->
                        TagDeleteChipItem(
                            name = tag.name,
                            onClick = { select.invoke(tag) }
                        )
                    }
                    item { Spacer(modifier = Modifier.width(12.dp)) }
                }
            }
            // 경계
            Box(modifier = Modifier
                .fillMaxWidth()
                .height(8.dp)
                .background(color = Color(0xFFF1F1F1)))

            // 하단
            Column {
                if(searchTextState.text.isEmpty()) {
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
                } else {
                    Spacer(modifier = Modifier.height(10.dp))
                    LazyColumn {
                        items(tagList) { tag ->
                            val isSelected by remember(selectTagList) {
                                derivedStateOf {
                                    selectTagList.contains(tag)
                                }
                            }
                            SearchResultItem(
                                name = tag.name,
                                isSelected = isSelected,
                                onClick = { select.invoke(tag) }
                            )
                        }
                    }
                }
            }
        }
    }
    BackHandler(onBack = onBack)
}